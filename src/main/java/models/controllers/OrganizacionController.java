package models.controllers;

import models.entities.CargaDeActividades.entidades.*;
import models.entities.calculoHC.CalculoHC;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.solicitudes.Solicitud;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrganizacionController {

    private RepositorioDeOrganizaciones repo;

    public OrganizacionController() {
        this.repo = FactoryRepositorioDeOrganizaciones.get();
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

        return new ModelAndView(parametros, "solicitudes-organizacion-menu.hbs");
    }

    public ModelAndView mostrarMedicion(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion org = this.obtenerOrganizacion(request, response);
        parametros.put("organizacion", org); //todo cargar las mediciones
        parametros.put("mediciones",org.getListaDeActividades());
        return new ModelAndView(parametros, "mediciones-menu.hbs");
    }

    private Organizacion obtenerOrganizacion(Request request, Response response){
        if(this.repo.existe(new Integer(request.session().attribute("resource_id").toString()))){ //todo validar tipo
            return this.repo.buscar(new Integer(request.session().attribute("resource_id").toString()));
        } else{
            response.redirect("/error"); //que hacer aca
            Spark.halt();
        }
        return null;
    }

    public ModelAndView mostrarReportes(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"reportes-menu.hbs"); //mostrar los botones y al tocar dice el valor
    }

    public ModelAndView mostrarHC(Request request, Response response){
        return new ModelAndView(new HashMap<String,Object>(),"calculadora-organizacion-menu.hbs");
    }

    public ModelAndView calcularHC(Request request, Response response){
        String periodoQuery = request.queryParams("periodo"); //x query?
        Periodo periodo = new Periodo(new Integer(request.queryParams("mes")),new Integer(request.queryParams("anio")));
        HashMap<String, Object> parametros = new HashMap<>();
        if(periodo.getAnio() != null){
            Organizacion org = this.obtenerOrganizacion(request, response);
            parametros.put("consumo-actividad",org.calcularHCActividadesEnPeriodo(periodo));
            parametros.put("consumo-trabajador",org.calcularHCEmpleados(periodo));
            parametros.put("periodo",periodo); //si mes null en la vista?
            parametros.put("factor-emision",CalculoHC.getUnidadPorDefecto());
            parametros.put("huella-carbono",org.calcularHCEnPeriodo(periodo));
        } else{
            return this.calcularCalculadoraHCTotal(request,response);
        }
        return new ModelAndView(parametros,"calculadora-organizacion-menu.hbs");
    }

    public ModelAndView calcularCalculadoraHCTotal(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion org = this.obtenerOrganizacion(request, response);

        parametros.put("consumo-actividad",org.calcularHCTotalActividades());
        parametros.put("consumo-trabajador",org.calcularHCTotalTrabajadores());
        parametros.put("periodo","TOTAL");
        parametros.put("factor-emision",CalculoHC.getUnidadPorDefecto());
        parametros.put("huella-carbono",org.calcularHCTotal());

        return new ModelAndView(parametros,"calculadora-organizacion-menu.hbs");
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"recomendaciones.hbs"); //dependen del tipo de cuenta?
    }

    public ModelAndView mostrarNuevaMedicion(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"subir-archivo-menu.hbs");
    }

    public Response registrarNuevaMedicion(Request request, Response response) {
        //TODO chequear q no haya ningun campo null
        Actividad actividad = new Actividad(
                TipoActividad.valueOf(request.queryParams("tipoDeActividad")),
                TipoDeConsumo.valueOf(request.queryParams("tipoDeConsumo")),
                Unidad.valueOf(request.queryParams("unidad")),
                new Periodo(new Integer(request.queryParams("mes")),new Integer(request.queryParams("anio"))),
                Periodicidad.valueOf(request.queryParams("periodicidad")),
                new Double(request.queryParams("valor"))
        );
        Organizacion org = this.obtenerOrganizacion(request,response);
        org.agregarActividad(actividad);
        // persistir actividad?
        response.redirect("/menu/organizacion/mediciones");
        return response;
    }

    public ModelAndView mostrarNuevoReporte(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"subir-archivo-menu.hbs");
    }

    public Response aceptarVinculacion(Request request, Response response){
        Solicitud solicitud = this.obtenerSolicitud(request,response);
        solicitud.aceptarSolicitud();
        return response;
    }
    public Response rechazarVinculacion(Request request, Response response){
        Solicitud solicitud = this.obtenerSolicitud(request,response);
        solicitud.rechazarSolicitud();
        return response;
    }

    private Solicitud obtenerSolicitud(Request request, Response response){
        Organizacion organizacion = this.obtenerOrganizacion(request,response);
        Sector sector = this.obtenerSector(organizacion,request); //TODO revisar esto de la solID
        return sector.getSolicitudes().stream().filter(sol -> sol.getId() == new Integer(request.queryParams("solicitudId"))).collect(Collectors.toList()).get(0);

    }

    private Sector obtenerSector(Organizacion organizacion, Request request){
        List<Sector> posibleSector = organizacion.getSectores().stream()
                .filter(s -> Objects.equals(s.getNombreSector(), request.queryParams("nombreSector")))
                .collect(Collectors.toList());
        if(posibleSector.isEmpty()){
            throw new RuntimeException("No se encontro el sector " + request.queryParams("nombreSector"));
        }else{
            return posibleSector.get(0);
        }
    }
}
