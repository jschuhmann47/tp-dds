package models.controllers;

import models.entities.CargaDeActividades.entidades.Periodicidad;
import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.calculoHC.CalculoHC;
import models.entities.geoDDS.Direccion;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.trayectos.Frecuencia;
import models.entities.trayectos.Tramo;
import models.entities.trayectos.Trayecto;
import models.helpers.ListHelper;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.RepositorioDeTrabajadores;
import models.repositories.factories.FactoryRepositorioDeTrabajadores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public ModelAndView mostrarCalculadora(Request request, Response response) {
        return new ModelAndView(null,"calculadora-trabajador-menu.hbs");
    }

    public ModelAndView calcularHC(Request request, Response response) {
        Periodo periodo = new Periodo(new Integer(request.queryParams("mes")),new Integer(request.queryParams("anio")));
        HashMap<String, Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request, response);
        if(periodo.getAnio() != null){
            parametros.put("periodo",periodo);
            parametros.put("factor-emision", CalculoHC.getUnidadPorDefecto());
            parametros.put("huella-carbono",trabajador.calcularHC(periodo));
        } else{
            parametros.put("periodo","TOTAL");
            parametros.put("huella-carbono",trabajador.calcularHCTotal());
        }
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
        Sector sectorAVincularse = org.obtenerSectorPorNombre(request.queryParams("nombreSector"));
        trabajador.solicitarVinculacion(org,sectorAVincularse);

        //solicitud creada ok

        response.redirect("/menu/trabajador/solicitudes");
        return response;

    }


    public ModelAndView mostrarNuevaVinculacion(Request request, Response response) {
        return new ModelAndView(null,"nueva-vinculacion-trabajador-menu.hbs");
    }

    public ModelAndView mostrarNuevoTrayecto(Request request, Response response) {
        return new ModelAndView(null,"nuevo-trayecto-menu.hbs");
    }

    public Response registrarNuevoTrayecto(Request request, Response response) {
        //TODO parsear los tramos
        List<Tramo> tramos = new ArrayList<>(); //lo que llege del front

        Frecuencia frecuencia = new Frecuencia(Periodicidad.valueOf(request.queryParams("periodicidad")),new Integer("vecesPorMes"));
        Trayecto trayecto = new Trayecto(ListHelper.getFirstElement(tramos).getPuntoInicio(), ListHelper.getLastElement(tramos).getPuntoFinal(),tramos,frecuencia);
        return response;
    }
}
