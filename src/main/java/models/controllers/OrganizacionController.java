package models.controllers;

import models.entities.CargaDeActividades.adapters.CargaDeActividadesApachePOIAdapter;
import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.calculoHC.CalculoHC;
import models.entities.calculoHC.UnidadHC;
import models.entities.organizaciones.contacto.*;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.solicitudes.EstadoSolicitud;
import models.entities.organizaciones.solicitudes.PosibleEstadoSolicitud;
import models.entities.organizaciones.solicitudes.Solicitud;
import models.helpers.PeriodoHelper;
import models.helpers.PersistenciaHelper;
import models.helpers.SessionHelper;
import models.helpers.threads.FileHandlerThread;
import models.repositories.RepositorioDeContactos;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.RepositorioDeParametrosFE;
import models.repositories.RepositorioDeSolicitudes;
import models.repositories.factories.FactoryRepositorioDeContactos;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeParametrosFE;
import models.repositories.factories.FactoryRepositorioDeSolicitudes;
import org.apache.commons.io.IOUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizacionController {
    private final RepositorioDeOrganizaciones repoOrganizaciones;
    private final RepositorioDeParametrosFE repoFE;
    private final RepositorioDeSolicitudes repoSolicitudes;
    private final RepositorioDeContactos repoContactos;

    public OrganizacionController() {
        this.repoOrganizaciones = FactoryRepositorioDeOrganizaciones.get();
        this.repoFE = FactoryRepositorioDeParametrosFE.get();
        this.repoSolicitudes = FactoryRepositorioDeSolicitudes.get();
        this.repoContactos = FactoryRepositorioDeContactos.get();
    }


    public ModelAndView mostrar(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Organizacion org = this.obtenerOrganizacion(request,response);
        parametros.put("organizacion",org);

        return new ModelAndView(parametros, "organizacion/organizacion-menu.hbs");
    }

    public ModelAndView mostrarVinculaciones(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Organizacion org = this.obtenerOrganizacion(request,response);
        parametros.put("organizacion",org);
        parametros.put("solicitudes",org.getListaDeSolicitudes());
        parametros.put("pendiente", PosibleEstadoSolicitud.PENDIENTE);

        return new ModelAndView(parametros, "organizacion/solicitudes-organizacion-menu.hbs"); //aceptar esa solicitud en concreto
    }

    public ModelAndView mostrarMedicion(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion org = this.obtenerOrganizacion(request, response);
        parametros.put("organizacion", org); //todo cargar las mediciones
        parametros.put("mediciones",org.getListaDeActividades());
        return new ModelAndView(parametros, "organizacion/mediciones-menu.hbs");
    }

    private Organizacion obtenerOrganizacion(Request request, Response response){
        if(this.repoOrganizaciones.existe(new Integer(request.session().attribute("resource_id").toString()))){
            return this.repoOrganizaciones.buscar(new Integer(request.session().attribute("resource_id").toString()));
        } else{
            response.redirect("/error");
            Spark.halt();
        }
        return null;
    }

    public ModelAndView mostrarReportes(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"organizacion/reportes-menu.hbs");
    }

    public ModelAndView mostrarHC(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
       parametros.put("organizacion",this.obtenerOrganizacion(request,response));
        return new ModelAndView(new HashMap<String,Object>(),"organizacion/calculadora-organizacion.hbs");
    }

    public ModelAndView calcularHC(Request request, Response response){
        this.setearCalculadoraHC();
        if(!SessionHelper.atributosNoSonNull(request,"mes","anio")){
            return this.calcularCalculadoraHCTotal(request,response); //todo testear
        }
        Periodo periodo = PeriodoHelper.nuevoPeriodo(request.queryParams("mes"),request.queryParams("anio"));
        HashMap<String, Object> parametros = new HashMap<>();

        Organizacion org = this.obtenerOrganizacion(request, response);
        parametros.put("consumoActividad",org.calcularHCActividadesEnPeriodo(periodo));
        parametros.put("consumoTrabajador",org.calcularHCEmpleados(periodo));
        parametros.put("factorEmision",CalculoHC.getUnidadPorDefectoString());
        parametros.put("huellaCarbono",org.calcularHCEnPeriodo(periodo));

        return new ModelAndView(parametros,"organizacion/calculadora-organizacion.hbs");
    }

    public ModelAndView calcularCalculadoraHCTotal(Request request, Response response){
        this.setearCalculadoraHC();
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion org = this.obtenerOrganizacion(request, response);

        parametros.put("consumoActividad",org.calcularHCTotalActividades());
        parametros.put("consumoTrabajador",org.calcularHCTotalTrabajadores());
        parametros.put("factorEmision",CalculoHC.getUnidadPorDefectoString());
        parametros.put("huellaCarbono",org.calcularHCTotal());

        return new ModelAndView(parametros,"organizacion/calculadora-organizacion.hbs");
    }

    private void setearCalculadoraHC(){
        CalculoHC.setFactoresEmisionFE(this.repoFE.buscarTodos());
        CalculoHC.setUnidadPorDefecto(UnidadHC.GRAMO_EQ);
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"recomendaciones.hbs");
    }

    public ModelAndView mostrarNuevaMedicion(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"organizacion/subir-archivo-menu.hbs");
    }

    public Response registrarNuevaMedicion(Request request, Response response) throws ServletException, IOException {

        String path = this.guardarArchivo(request,response);
        Organizacion org = this.obtenerOrganizacion(request,response);
        FileHandlerThread thread = new FileHandlerThread(path,org.getId(),new CargaDeActividadesApachePOIAdapter());
        thread.start();

        response.redirect("/organizacion/mediciones");
        return response;
    }

    private String guardarArchivo(Request request, Response response) throws ServletException, IOException {
        request.attribute("org.eclipse.jetty.multipartConfig", new
                MultipartConfigElement(System.getProperty("user.dir") + "/src/main/resources/public/uploads/"));

        Part filePart = request.raw().getPart("myfile");

        try (InputStream inputStream = filePart.getInputStream()) {

            OutputStream outputStream = Files.newOutputStream(Paths.get(System.getProperty("user.dir") + "/src/main/resources/public/uploads/" + filePart.getSubmittedFileName()));
            IOUtils.copy(inputStream, outputStream);
            outputStream.close();
        }
        return Paths.get(System.getProperty("user.dir") + "/src/main/resources/public/uploads/" + filePart.getSubmittedFileName()).toString();
    }


    public Response aceptarVinculacion(Request request, Response response){
        Solicitud solicitud = this.obtenerSolicitud(request,response);
        solicitud.aceptarSolicitud();
        PersistenciaHelper.persistir(solicitud);
        response.redirect("/organizacion/vinculaciones");
        return response;
    }
    public Response rechazarVinculacion(Request request, Response response){
        Solicitud solicitud = this.obtenerSolicitud(request,response);
        solicitud.rechazarSolicitud();
        PersistenciaHelper.persistir(solicitud);
        response.redirect("/organizacion/vinculaciones");
        return response;
    }

    private Solicitud obtenerSolicitud(Request request, Response response){
        return this.repoSolicitudes.buscar(new Integer(request.queryParams("solicitudId")));
    }

    public ModelAndView mostrarContactos(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion organizacion = this.obtenerOrganizacion(request,response);
        parametros.put("contactos",organizacion.getContactos());
        return new ModelAndView(parametros,"organizacion/contactos-menu.hbs");
    }

    public ModelAndView mostrarNuevoContacto(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        List<String> mediosNotif = new ArrayList<>();
        for(EMedioNotificacion m : EMedioNotificacion.values()){
            mediosNotif.add(m.toString());
        }
        mediosNotif.add(EMedioNotificacion.TODOS.toString());
        parametros.put("medios",mediosNotif);
        return new ModelAndView(parametros,"organizacion/contacto-nuevo-menu.hbs");
    }

    public Response registrarNuevoContacto(Request request, Response response){
        if(SessionHelper.atributosNoSonNull(request,"telefono","email","mediosNotificacion","nombre","apellido")){
            Contacto nuevoContacto = new Contacto(request.queryParams("nombre"),request.queryParams("apellido"),request.queryParams("telefono"),request.queryParams("email"), this.getMedioDeNotificacionDeRequest(request));
            Organizacion org = this.obtenerOrganizacion(request,response);
            org.agregarContacto(nuevoContacto);
            PersistenciaHelper.persistir(org);
            //exito
        }else{
            response.redirect("/error");
        }
        response.redirect("/organizacion/contactos");
        return response;
    }

    public ModelAndView mostrarEditarContacto(Request request, Response response){
        return new ModelAndView(null,"organizacion/contacto-editar-menu.hbs");
    }

    public Response editarContacto(Request request, Response response){
        Contacto contactoAEditar = this.repoContactos.buscar(new Integer(request.queryParams("contactoId")));
        if(SessionHelper.atributosNoSonNull(request,"nroTelefono")){
            contactoAEditar.setNroTelefono(request.queryParams("nroTelefono"));
        }
        if(SessionHelper.atributosNoSonNull(request,"email")){
            contactoAEditar.setEmail(request.queryParams("email"));
        }
        contactoAEditar.setListaDeMedios(this.getMedioDeNotificacionDeRequest(request)); //es un desplegable
        return response;
    }

    private List<MedioNotificacion> getMedioDeNotificacionDeRequest(Request request){
        List<MedioNotificacion> medios = new ArrayList<>();
        switch (request.queryParams("mediosNotificacion")){
            case "MAIL":
                medios.add(new MandarMail());
                break;
            case "WHATSAPP":
                medios.add(new MandarWhatsapp());
                break;
            case "TODOS":
                medios.add(new MandarWhatsapp());
                medios.add(new MandarMail());
                break;
            default:
                break;
        }
        return medios;
    }

    public Response eliminarContacto(Request request, Response response) {
        Contacto contactoAEliminar = this.repoContactos.buscar(new Integer(request.queryParams("contactoId")));
        Organizacion organizacion = this.obtenerOrganizacion(request,response);
        organizacion.eliminarContacto(contactoAEliminar);
        PersistenciaHelper.persistir(organizacion);
        response.redirect("/contactos");
        return response;
    }
}
