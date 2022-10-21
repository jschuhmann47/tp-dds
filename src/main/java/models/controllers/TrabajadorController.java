package models.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import db.EntityManagerHelper;
import models.entities.CargaDeActividades.entidades.Periodicidad;
import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.calculoHC.CalculoHC;
import models.entities.calculoHC.UnidadHC;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.organizaciones.solicitudes.Solicitud;
import models.entities.trayectos.Frecuencia;
import models.entities.trayectos.Tramo;
import models.entities.trayectos.Trayecto;
import models.helpers.ListHelper;
import models.helpers.PeriodoHelper;
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
    private RepositorioDeProvincias repoProvincias;
    private RepositorioDeMunicipios repoMunicipios;
    private RepositorioDeTrabajadores repoTrabajadores;
    private RepositorioDeOrganizaciones repoOrgs;
    private RepositorioDeParametrosFE repoFE;
    private RepositorioDeLocalidades repoLocalidades;
    private RepositorioDeSolicitudes repoSolicitudes;

    public TrabajadorController() {
        this.repoTrabajadores = FactoryRepositorioDeTrabajadores.get();
        this.repoOrgs = FactoryRepositorioDeOrganizaciones.get();
        this.repoFE = FactoryRepositorioDeParametrosFE.get();
        this.repoLocalidades = FactoryRepositorioDeLocalidades.get();
        this.repoMunicipios = FactoryRepositorioDeMunicipios.get();
        this.repoProvincias = FactoryRepositorioDeProvincias.get();
        this.repoSolicitudes = FactoryRepositorioDeSolicitudes.get();
    }



    public ModelAndView mostrar(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        parametros.put("trabajador",trabajador);
        return new ModelAndView(parametros, "trabajador-menu.hbs");
    }

    private Trabajador obtenerTrabajador(Request request, Response response){
        Trabajador trabajador = this.repoTrabajadores.buscar(new Integer(request.session().attribute("resource_id"))); //todo validar
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

    public ModelAndView mostrarNuevaVinculacion(Request request, Response response) {
        return new ModelAndView(null,"nueva-vinculacion.hbs");
    }

    public Response eliminarVinculacion(Request request, Response response){
        Solicitud solicitud = this.repoSolicitudes.buscar(new Integer(request.queryParams("solicitudId"))); //con ajax
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().remove(solicitud);
        EntityManagerHelper.commit();
        return response;
    }


    public Response nuevaVinculacion(Request request, Response response){
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

        response.redirect("/trabajador/solicitudes");
        return response;

    }


    public ModelAndView mostrarTrayectos(Request request, Response response) { //TODO mostrar tramos y editar etc
        HashMap<String,Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        parametros.put("trabajador",trabajador);
        parametros.put("trayectos",trabajador.getListaTrayectos());

        return new ModelAndView(parametros, "trayectos-menu.hbs");
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"recomendaciones.hbs"); //dependen del tipo de cuenta?
    }


    public ModelAndView mostrarNuevoTrayecto(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("localidades",this.repoLocalidades.buscarTodos());
        return new ModelAndView(parametros,"agregar-trayecto.hbs");
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

    public Response eliminarTrayecto(Request request, Response response){
//        Trayecto trayecto = this.repoTrayectos.buscar(new Integer(request.queryParams("trayectoId"))); //con ajax
    //        EntityManagerHelper.beginTransaction();
    //        EntityManagerHelper.getEntityManager().remove(solicitud);
    //        EntityManagerHelper.commit();
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
