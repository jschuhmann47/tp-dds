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
import models.helpers.PeriodoHelper;
import models.helpers.PersistenciaHelper;
import models.helpers.SessionHelper;
import models.repositories.*;
import models.repositories.factories.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TrabajadorController {
    private RepositorioDeTrabajadores repoTrabajadores;
    private RepositorioDeOrganizaciones repoOrgs;
    private RepositorioDeParametrosFE repoFE;
    private RepositorioDeLocalidades repoLocalidades;
    private RepositorioDeSolicitudes repoSolicitudes;
    private RepositorioDeMediosDeTransporte repoTransportes;
    private RepositorioDeTrayectos repoTrayectos;
    private RepositorioDeSectores repoSectores;
    private RepositorioDeTramos repoTramos;

    public TrabajadorController() {
        this.repoTrabajadores = FactoryRepositorioDeTrabajadores.get();
        this.repoOrgs = FactoryRepositorioDeOrganizaciones.get();
        this.repoFE = FactoryRepositorioDeParametrosFE.get();
        this.repoLocalidades = FactoryRepositorioDeLocalidades.get();
        this.repoSolicitudes = FactoryRepositorioDeSolicitudes.get();
        this.repoTransportes = FactoryRepositorioDeMediosDeTransporte.get();
        this.repoTrayectos = FactoryRepositorioDeTrayectos.get();
        this.repoSectores = FactoryRepositorioDeSectores.get();
        this.repoTramos = FactoryRepositorioDeTramos.get();
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

        if(request.queryParams("anio") == null){
            throw new RuntimeException("No se ingreso el año");
        }
        Periodo periodo = PeriodoHelper.nuevoPeriodo(request.queryParams("mes"),request.queryParams("anio"));
        HashMap<String, Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request, response);
        parametros.put("trabajador",trabajador);
        //parametros.put("factorEmision", CalculoHC.getUnidadPorDefecto()).toString();
        if(periodo.getAnio() != null){
            parametros.put("huellaCarbono",trabajador.calcularHC(periodo));
        } else{
            parametros.put("huellaCarbono",trabajador.calcularHCTotal());
        }
        parametros.put("factorEmision",CalculoHC.getUnidadPorDefectoString());
        return new ModelAndView(parametros,"calculadora-trabajador.hbs");
    }

    public ModelAndView mostrarVinculaciones(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        parametros.put("trabajador",trabajador);
        parametros.put("solicitudes",trabajador.getListaDeSolicitudes());

        return new ModelAndView(parametros, "solicitudes-trabajador-menu.hbs");
    }

    public ModelAndView mostrarNuevaVinculacion(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("organizaciones",this.repoOrgs.buscarTodos());
        if(SessionHelper.atributosNoSonNull(request,"organizacionId")){
            parametros.put("sectores",this.repoOrgs.buscar(new Integer(request.queryParams("organizacionId"))).getSectores());
        }
        return new ModelAndView(parametros,"nueva-vinculacion.hbs");
    }

    public Response eliminarVinculacion(Request request, Response response){
        Solicitud solicitud = this.repoSolicitudes.buscar(new Integer(request.queryParams("solicitudId"))); //con ajax
        PersistenciaHelper.persistir(solicitud);
        return response;
    }


    public Response nuevaVinculacion(Request request, Response response){
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        if(SessionHelper.atributosNoSonNull(request,"nombreSector","organizacionId","sectorId")){
            Organizacion org = this.repoOrgs.buscar(new Integer(request.queryParams("organizacionId")));
            if(org != null){
                Sector sectorAVincularse = this.repoSectores.buscar(new Integer(request.queryParams("sectorId")));
                if(sectorAVincularse != null){
                    Solicitud sol = trabajador.solicitarVinculacion(org,sectorAVincularse);
                    PersistenciaHelper.persistir(sol);
                }
            }
        }


        response.redirect("/trabajador/vinculacion");
        return response;

    }


    public ModelAndView mostrarTrayectos(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        parametros.put("trabajador",trabajador);
        parametros.put("trayectos",trabajador.getListaTrayectos());

        return new ModelAndView(parametros, "trayectos-menu.hbs");
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"recomendaciones.hbs");
    }


    public ModelAndView mostrarNuevoTrayecto(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("localidades",this.repoLocalidades.buscarTodos());
        return new ModelAndView(parametros,"agregar-trayecto.hbs");
    }

    public ModelAndView mostrarNuevoTramo(Request request, Response response) { //TODO editar tramos
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("localidades",this.repoLocalidades.buscarTodos());
        parametros.put("transportes",this.repoTransportes.buscarTodos());
        return new ModelAndView(parametros,"agregar-tramo.hbs");
    }

    public ModelAndView mostrarEditarTramo(Request request, Response response) { //TODO editar tramos
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("tramo",this.repoTramos.buscar(new Integer(request.queryParams("tramoId"))));
        return new ModelAndView(parametros,"editar-tramo.hbs");
    }


    public ModelAndView mostrarTramosDelTrayecto(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("tramos",this.repoTrayectos.buscar(new Integer(request.queryParams("trayectoId"))).getTramos()); //q no sea null
        return new ModelAndView(parametros,"menu-tramos.hbs");
    }

    public Response registrarNuevoTramo(Request request, Response response) throws Exception {
        if(SessionHelper.atributosNoSonNull(request,"medioTransporteId","alturaInicio","calleInicio","localidadInicioId","calleDestino","alturaDestino","localidadDestinoId","trayectoId")){
            Tramo tramo = new Tramo
                    (this.repoTransportes.buscar(new Integer(request.queryParams("medioTransporteId"))),
                            new Direccion
                                    (new Integer(request.queryParams("alturaInicio")),request.queryParams("calleInicio"),
                                            this.repoLocalidades.buscar(new Integer(request.queryParams("localidadInicioId")))),
                            new Direccion
                                    (new Integer(request.queryParams("alturaDestino")),request.queryParams("calleDestino"),
                                        this.repoLocalidades.buscar(new Integer(request.queryParams("localidadDestinoId"))))
                    );

            PersistenciaHelper.persistir(tramo); //todo asociarlo al trayecto
        }
        return response;
    }

    public Response editarTramo(Request request, Response response){ //todo pantalla de editar que esten los datos cargados, similar a la de los FE
        Tramo tramo = this.repoTramos.buscar(new Integer(request.queryParams("tramoId")));
        if(SessionHelper.atributosNoSonNull(request,"alturaInicio")){
            tramo.getPuntoInicio().setAltura(new Integer(request.queryParams("alturaInicio")));
        }
        //asi con todos los atributos
        response.redirect("/trabajador/trayectos");
        return response;
    }

    public Response registrarNuevoTrayecto(Request request, Response response) {
        if(SessionHelper.atributosNoSonNull(request,"tramos","periodicidad","vecesPorMes")){
            List<Tramo> tramos = this.decodearTramos(request.queryParams("tramos")); //testear

            Frecuencia frecuencia = new Frecuencia(Periodicidad.valueOf(request.queryParams("periodicidad")),new Integer("vecesPorMes"));
            Trayecto trayecto = new Trayecto
                    (ListHelper.getFirstElement(tramos).getPuntoInicio(),
                            ListHelper.getLastElement(tramos).getPuntoFinal(),
                            tramos,
                            frecuencia);

            Trabajador trabajador = this.obtenerTrabajador(request,response);
            trabajador.agregarTrayectos(trayecto);
            PersistenciaHelper.persistir(trayecto);
        }

        return response;
    }

    public Response eliminarTrayecto(Request request, Response response){
        Trayecto trayecto = this.repoTrayectos.buscar(new Integer(request.queryParams("trayectoId"))); //con ajax
        PersistenciaHelper.eliminar(trayecto);
        return response;
    }

    public Response eliminarTramo(Request request, Response response){
        Trayecto trayecto = this.repoTrayectos.buscar(new Integer(request.queryParams("trayectoId"))); //con ajax
        PersistenciaHelper.eliminar(trayecto);
        return response;
    }

    private List<Tramo> decodearTramos(String body){
        Type tipoTramo = new TypeToken<ArrayList<Tramo>>() {}.getType(); //sacar esto a otro lado

        Gson gson = new Gson();
        return gson.fromJson(body, tipoTramo);
    }

    private void setearCalculadoraHC(){
        CalculoHC.setFactoresEmisionFE(this.repoFE.buscarTodos());
        CalculoHC.setUnidadPorDefecto(UnidadHC.GRAMO_EQ);
    }
}
