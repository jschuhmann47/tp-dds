package models.controllers;

import db.EntityManagerHelper;
import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.calculoHC.CalculoHC;
import models.entities.calculoHC.UnidadHC;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.solicitudes.Solicitud;
import models.helpers.PeriodoHelper;
import models.helpers.threads.FileHandlerThread;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.RepositorioDeParametrosFE;
import models.repositories.RepositorioDeSolicitudes;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeParametrosFE;
import models.repositories.factories.FactoryRepositorioDeSolicitudes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import java.util.HashMap;
import java.util.stream.Collectors;

public class OrganizacionController {
    //TODO provincias, municipios, orgs, sectores, medios t que llege una lista, que no escriba a mano
    private RepositorioDeOrganizaciones repoOrganizaciones;
    private RepositorioDeParametrosFE repoFE;
    private RepositorioDeSolicitudes repoSolicitudes;

    public OrganizacionController() {
        this.repoOrganizaciones = FactoryRepositorioDeOrganizaciones.get();
        this.repoFE = FactoryRepositorioDeParametrosFE.get();
        this.repoSolicitudes = FactoryRepositorioDeSolicitudes.get();
    }


    public ModelAndView mostrar(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Organizacion org = this.obtenerOrganizacion(request,response);
        parametros.put("organizacion",org);

        return new ModelAndView(parametros, "organizacion-menu.hbs");
    }

    public ModelAndView mostrarVinculaciones(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Organizacion org = this.obtenerOrganizacion(request,response);
        parametros.put("organizacion",org);
        parametros.put("solicitudes",org.getListaDeSolicitudes());

        return new ModelAndView(parametros, "solicitudes-organizacion-menu.hbs"); //aceptar esa solicitud en concreto
    }

    public ModelAndView mostrarMedicion(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion org = this.obtenerOrganizacion(request, response);
        parametros.put("organizacion", org); //todo cargar las mediciones
        parametros.put("mediciones",org.getListaDeActividades());
        return new ModelAndView(parametros, "mediciones-menu.hbs");
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

    public ModelAndView mostrarReportes(Request request, Response response) { //esto es lo de cargar excel
        return new ModelAndView(new HashMap<String,Object>(),"reportes-menu.hbs"); //mostrar los botones y al tocar dice el valor
    }

    public ModelAndView mostrarHC(Request request, Response response){
        return new ModelAndView(new HashMap<String,Object>(),"calculadora-organizacion.hbs");
    }

    public ModelAndView calcularHC(Request request, Response response){ //NO LLEGA ACA SI ES TOTAL
        this.setearCalculadoraHC();
//        if(request.queryParams("mes") == null || request.queryParams("anio") == null){ //todo hacer un boton para calcular total
//            throw new RuntimeException("No se ingreso mes o año");
//        }
        Periodo periodo = PeriodoHelper.nuevoPeriodo(request.queryParams("mes"),request.queryParams("anio"));
        HashMap<String, Object> parametros = new HashMap<>();

        Organizacion org = this.obtenerOrganizacion(request, response);
        parametros.put("consumoActividad",org.calcularHCActividadesEnPeriodo(periodo));
        parametros.put("consumoTrabajador",org.calcularHCEmpleados(periodo));
        parametros.put("factorEmision",CalculoHC.getUnidadPorDefecto());
        parametros.put("huellaCarbono",org.calcularHCEnPeriodo(periodo));

        return new ModelAndView(parametros,"calculadora-organizacion.hbs");
    }

    public ModelAndView calcularCalculadoraHCTotal(Request request, Response response){
        this.setearCalculadoraHC();
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion org = this.obtenerOrganizacion(request, response);

        parametros.put("consumoActividad",org.calcularHCTotalActividades());
        parametros.put("consumoTrabajador",org.calcularHCTotalTrabajadores());
        parametros.put("factorEmision",CalculoHC.getUnidadPorDefecto());
        parametros.put("huellaCarbono",org.calcularHCTotal());

        return new ModelAndView(parametros,"calculadora-organizacion.hbs");
    }

    private void setearCalculadoraHC(){
        CalculoHC.setFactoresEmisionFE(this.repoFE.buscarTodos());
        CalculoHC.setUnidadPorDefecto(UnidadHC.GRAMO_EQ);
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"recomendaciones.hbs"); //dependen del tipo de cuenta?
    }

    public ModelAndView mostrarNuevaMedicion(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"subir-archivo-menu.hbs");
    }

    public Response registrarNuevaMedicion(Request request, Response response) {

        //VIENE DEL EXCEL, todo ajax/jquery
        Organizacion org = this.obtenerOrganizacion(request,response);
        FileHandlerThread thread = new FileHandlerThread("ruta/pendiente",org.getId());
        thread.start();

        response.redirect("/organizacion/mediciones");
        return response;
    }

    public ModelAndView mostrarNuevoReporte(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"subir-archivo-menu.hbs");
    }

    public Response aceptarVinculacion(Request request, Response response){
        Solicitud solicitud = this.obtenerSolicitud(request,response);
        solicitud.aceptarSolicitud();
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(solicitud);
        EntityManagerHelper.commit();
        return response;
    }
    public Response rechazarVinculacion(Request request, Response response){
        Solicitud solicitud = this.obtenerSolicitud(request,response);
        solicitud.rechazarSolicitud();
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(solicitud);
        EntityManagerHelper.commit();
        return response;
    }

    private Solicitud obtenerSolicitud(Request request, Response response){
//        Organizacion organizacion = this.obtenerOrganizacion(request,response);
//        Sector sector = organizacion.obtenerSectorPorNombre(request.queryParams("nombreSector")); //TODO que le pase la sol Id de una -> repoSolicitudes
//        return sector.getSolicitudes().stream().filter(sol -> sol.getId() == new Integer(request.queryParams("solicitudId"))).collect(Collectors.toList()).get(0);
        return this.repoSolicitudes.buscar(new Integer(request.queryParams("solicitudId")));
    }

}
