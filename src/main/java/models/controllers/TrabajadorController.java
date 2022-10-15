package models.controllers;

import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.entidades.Trabajador;
import models.repositories.Repositorio;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.RepositorioDeTrabajadores;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeTrabajadores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TrabajadorController {
    private RepositorioDeTrabajadores repoTrabajadores;
    private RepositorioDeOrganizaciones repoOrgs;

    public TrabajadorController() {
        this.repoTrabajadores = FactoryRepositorioDeTrabajadores.get();
    }



    public ModelAndView mostrar(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        parametros.put("trabajador",trabajador);
        return new ModelAndView(parametros, "trabajador-menu.hbs");
    }

    private Trabajador obtenerTrabajador(Request request, Response response){
        Trabajador trabajador = this.repoTrabajadores.buscar(new Integer(request.session().attribute("resource_id").toString())); //todo validar
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

        return new ModelAndView(parametros, "agregar-trayecto.hbs");
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"recomendaciones.hbs"); //dependen del tipo de cuenta?
    }

    public Response nuevaSolicitud(Request request, Response response){
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        if(request.queryParams("nombreSector") == null || request.queryParams("razonSocial") == null){
            //error no escribio
            return response;
        }
        Organizacion org = this.repoOrgs.buscarPorRazonSocial(request.queryParams("razonSocial"));
        List<Sector> posibleSector = org.getSectores().stream()
                .filter(s -> Objects.equals(s.getNombreSector(), request.queryParams("nombreSector")))
                .collect(Collectors.toList());
//                .get(0); //ta bien asi?
        if(posibleSector.isEmpty()){
            //error no se encontro
        }
        else{
            Sector sectorAVincularse = posibleSector.get(0); //se supone que hay uno solo con el nombre ese
            trabajador.solicitarVinculacion(org,sectorAVincularse);
            //solicitud creada ok
            response.redirect("/menu/trabajador/solicitudes");
        }
        return response;

    }

}
