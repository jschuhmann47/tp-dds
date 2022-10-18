package models.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import db.EntityManagerHelper;
import models.entities.CargaDeActividades.entidades.Periodicidad;
import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.calculoHC.CalculoHC;
import models.entities.calculoHC.UnidadHC;
import models.entities.geoDDS.Direccion;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.organizaciones.solicitudes.Solicitud;
import models.entities.trayectos.Frecuencia;
import models.entities.trayectos.Tramo;
import models.entities.trayectos.Trayecto;
import models.helpers.ListHelper;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.RepositorioDeParametrosFE;
import models.repositories.RepositorioDeTrabajadores;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeParametrosFE;
import models.repositories.factories.FactoryRepositorioDeTrabajadores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TrabajadorController {
    private RepositorioDeTrabajadores repoTrabajadores;
    private RepositorioDeOrganizaciones repoOrgs;
    private RepositorioDeParametrosFE repoFE;

    public TrabajadorController() {
        this.repoTrabajadores = FactoryRepositorioDeTrabajadores.get();
        this.repoOrgs = FactoryRepositorioDeOrganizaciones.get();
        this.repoFE = FactoryRepositorioDeParametrosFE.get();
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
        return new ModelAndView(null,"calculadora-trabajador.hbs");
    }

    public ModelAndView calcularHC(Request request, Response response) {
        this.setearCalculadoraHC();
        if(request.queryParams("mes") == null || request.queryParams("anio") == null){
            throw new RuntimeException("No se ingreso mes o año");
        }
        Periodo periodo = new Periodo(new Integer(request.queryParams("mes")),new Integer(request.queryParams("anio")));
        HashMap<String, Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request, response);
        parametros.put("trabajador",trabajador);
        //parametros.put("factorEmision", CalculoHC.getUnidadPorDefecto()).toString();
        if(periodo.getAnio() != null){
            parametros.put("huellaCarbono",trabajador.calcularHC(periodo));
        } else{
            parametros.put("huellaCarbono",trabajador.calcularHCTotal());
        }
        return new ModelAndView(parametros,"calculadora-trabajador.hbs");
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

        return new ModelAndView(parametros, "trayectos-menu.hbs");
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"recomendaciones.hbs"); //dependen del tipo de cuenta?
    }

    public Response nuevaSolicitud(Request request, Response response){
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        if(request.queryParams("nombreSector") == null || request.queryParams("razonSocial") == null){
            return response;
        }
        Organizacion org = this.repoOrgs.buscarPorRazonSocial(request.queryParams("razonSocial"));
        if(org != null){
            Sector sectorAVincularse = org.obtenerSectorPorNombre(request.queryParams("nombreSector"));
            if(sectorAVincularse != null){
                Solicitud sol = trabajador.solicitarVinculacion(org,sectorAVincularse);
                EntityManagerHelper.beginTransaction();
                EntityManagerHelper.getEntityManager().persist(sol);
                EntityManagerHelper.commit();
            }
        }

        response.redirect("/menu/trabajador/solicitudes");
        return response;

    }


    public ModelAndView mostrarNuevaVinculacion(Request request, Response response) {
        return new ModelAndView(null,"nueva-vinculacion.hbs");
    }

    public ModelAndView mostrarNuevoTrayecto(Request request, Response response) {
        return new ModelAndView(null,"agregar-trayecto.hbs");
    }

    public Response registrarNuevoTrayecto(Request request, Response response) {
        List<Tramo> tramos = this.decodearTramos(request.queryParams("tramos")); //testear

        Frecuencia frecuencia = new Frecuencia(Periodicidad.valueOf(request.queryParams("periodicidad")),new Integer("vecesPorMes"));
        Trayecto trayecto = new Trayecto
                        (ListHelper.getFirstElement(tramos).getPuntoInicio(),
                        ListHelper.getLastElement(tramos).getPuntoFinal(),
                        tramos,
                        frecuencia);

        Trabajador trabajador = this.obtenerTrabajador(request,response);
        trabajador.agregarTrayectos(trayecto);
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(trayecto);
        EntityManagerHelper.commit();
        return response;
    }

    private List<Tramo> decodearTramos(String body){
        Type tipoTramo = new TypeToken<ArrayList<Tramo>>() {}.getType();

        Gson gson = new Gson();
        return gson.fromJson(body, tipoTramo);
    }

    private void setearCalculadoraHC(){
        CalculoHC.setFactoresEmisionFE(this.repoFE.buscarTodos());
        CalculoHC.setUnidadPorDefecto(UnidadHC.GRAMO_EQ);
    }
}
