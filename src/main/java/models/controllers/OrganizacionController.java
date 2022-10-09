package models.controllers;

import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.calculoHC.CalculoHC;
import models.entities.organizaciones.entidades.Organizacion;
import models.repositories.Repositorio;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrganizacionController {

    private Repositorio<Organizacion> repo;

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

        return new ModelAndView(parametros, "solicitudes-menu.hbs");
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

    public ModelAndView mostrarCalculadoraHC(Request request, Response response){
        String periodoQuery = request.queryParams("periodo"); //x query?
        HashMap<String, Object> parametros = new HashMap<>();
        if(periodoQuery != null){
            Organizacion org = this.obtenerOrganizacion(request, response);
            //PeriodoHelper.formatear(periodo);
            Periodo periodo = new Periodo(1,1); //TODO
            parametros.put("consumo-actividad",org.calcularHCActividadesEnPeriodo(periodo));
            parametros.put("consumo-trabajador",org.calcularHCEmpleados(periodo));
            parametros.put("periodo",periodo);
            parametros.put("factor-emision",CalculoHC.getUnidadPorDefecto());
            parametros.put("huella-carbono",org.calcularHCEnPeriodo(periodo));

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
        try{
            parametros.put("huella-carbono",org.calcularHCTotal());
        } catch (Exception e){
            //TODO
        }

        return new ModelAndView(parametros,"calculadora-organizacion-menu.hbs");
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"recomendaciones.hbs");
    }

    public ModelAndView mostrarNuevaMedicion(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"mediciones-formulario.hbs");
    }

    public Response registrarNuevaMedicion(Request request, Response response) {
        String nombre = request.queryParams("nombre");
        //TODO y persistir etc
        response.redirect("/menu/organizacion/mediciones");
        return response;
    }
}
