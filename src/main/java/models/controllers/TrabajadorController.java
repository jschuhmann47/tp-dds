package models.controllers;

import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Trabajador;
import models.repositories.Repositorio;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeTrabajadores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;

public class TrabajadorController {
    private Repositorio<Trabajador> repo;

    public TrabajadorController() {
        this.repo = FactoryRepositorioDeTrabajadores.get();
    }



    public ModelAndView mostrar(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        parametros.put("trabajador",trabajador);
        return new ModelAndView(parametros, "trabajador-menu.hbs");
    }

    private Trabajador obtenerTrabajador(Request request, Response response){
        Trabajador trabajador = this.repo.buscar(new Integer(request.session().attribute("resource_id").toString())); //todo validar
        if(trabajador == null){ //try catch
            response.redirect("/error");
            Spark.halt();
        }
        return trabajador;
    }

    public ModelAndView mostrarCalculadoraHC(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"calculadora-trabajador-menu.hbs");
    }

    public ModelAndView mostrarReportes(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"reportes-menu.hbs");
    }

    public ModelAndView mostrarVinculaciones(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        parametros.put("trabajador",trabajador);
        parametros.put("solicitudes",trabajador.getListaDeSolicitudes());

        return new ModelAndView(parametros, "solicitudes-trabajador-menu.hbs");
    }

    public ModelAndView mostrarTrayectos(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        parametros.put("trabajador",trabajador);
        parametros.put("trayectos",trabajador.getListaTrayectos());

        return new ModelAndView(parametros, "trayectos-trabajador-menu.hbs");
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"recomendaciones.hbs"); //dependen del tipo de cuenta?
    }
}
