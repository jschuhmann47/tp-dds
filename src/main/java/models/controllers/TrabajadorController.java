package models.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import db.EntityManagerHelper;
import models.entities.CargaDeActividades.entidades.Periodicidad;
import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.calculoHC.CalculoHC;
import models.entities.calculoHC.UnidadHC;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.ServicioCalcularDistancia;
import models.entities.geoDDS.adapters.ServicioGeoDDSRetrofitAdapter;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.organizaciones.solicitudes.PosibleEstadoSolicitud;
import models.entities.organizaciones.solicitudes.Solicitud;
import models.entities.transporte.MedioTransporte;
import models.entities.transporte.privado.TransportePrivado;
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
import java.util.stream.Collectors;

public class TrabajadorController {
    private final RepositorioDeTrabajadores repoTrabajadores;
    private final RepositorioDeOrganizaciones repoOrgs;
    private final RepositorioDeParametrosFE repoFE;
    private final RepositorioDeLocalidades repoLocalidades;
    private final RepositorioDeSolicitudes repoSolicitudes;
    private final RepositorioDeMediosDeTransporte repoTransportes;
    private final RepositorioDeTrayectos repoTrayectos;
    private final RepositorioDeSectores repoSectores;
    private final RepositorioDeTramos repoTramos;
    private final RepositorioDeTransportesPrivados repoPrivados;

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
        this.repoPrivados = FactoryRepositorioDeTransportesPrivados.get();
    }



    public ModelAndView mostrar(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        parametros.put("trabajador",trabajador);
        return new ModelAndView(parametros, "trabajador/trabajador-menu.hbs");
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
        return new ModelAndView(null,"trabajador/calculadora-trabajador.hbs");
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
        return new ModelAndView(parametros,"trabajador/calculadora-trabajador.hbs");
    }

    public ModelAndView mostrarVinculaciones(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Trabajador trabajador = this.obtenerTrabajador(request,response);
        parametros.put("trabajador",trabajador);
        parametros.put("solicitudes",trabajador.getListaDeSolicitudes());
        parametros.put("pendiente", PosibleEstadoSolicitud.PENDIENTE);

        return new ModelAndView(parametros, "trabajador/solicitudes-trabajador-menu.hbs");
    }

    public ModelAndView mostrarNuevaVinculacion(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("organizaciones",this.repoOrgs.buscarTodos());
        if(SessionHelper.atributosNoSonNull(request,"organizacionId")){
            parametros.put("sectores",this.repoOrgs.buscar(new Integer(request.queryParams("organizacionId"))).getSectores());
        }
        return new ModelAndView(parametros,"trabajador/nueva-vinculacion.hbs");
    }

    public Response eliminarVinculacion(Request request, Response response){
        if(SessionHelper.atributosNoSonNull(request,"solicitudId")){
            Solicitud solicitud = this.repoSolicitudes.buscar(new Integer(request.queryParams("solicitudId")));
            PersistenciaHelper.eliminar(solicitud);
        }
        response.redirect("/trabajador/vinculacion");
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

        return new ModelAndView(parametros, "trabajador/trayectos-menu.hbs");
    }

    public ModelAndView mostrarRecomendaciones(Request request, Response response) {
        return new ModelAndView(new HashMap<String,Object>(),"recomendaciones.hbs");
    }


    public ModelAndView mostrarNuevoTrayecto(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("localidades",this.repoLocalidades.buscarTodos());
        List<String> frecuenciasDeUso = new ArrayList<>();
        frecuenciasDeUso.add(Periodicidad.MENSUAL.toString());
        frecuenciasDeUso.add(Periodicidad.ANUAL.toString());
        parametros.put("frecuencias",frecuenciasDeUso);
        return new ModelAndView(parametros,"trabajador/agregar-trayecto.hbs");
    }

    public ModelAndView mostrarNuevoTramo(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("localidades",this.repoLocalidades.buscarTodos());
        parametros.put("transportes",this.repoTransportes.buscarTodos());
        parametros.put("trayectoId",this.repoTrayectos.buscar(new Integer(request.queryParams("trayectoId"))).getId());
        return new ModelAndView(parametros,"trabajador/agregar-tramo.hbs");
    }

    public ModelAndView mostrarEditarTramo(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("tramo",this.repoTramos.buscar(new Integer(request.queryParams("tramoId"))));
        return new ModelAndView(parametros,"trabajador/editar-tramo.hbs");
    }


    public ModelAndView mostrarTramosDelTrayecto(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Trayecto trayecto = this.repoTrayectos.buscar(new Integer(request.queryParams("trayectoId")));
        parametros.put("tramos",trayecto.getTramos()); //q no sea null
        parametros.put("trayectoId",trayecto.getId());
        return new ModelAndView(parametros,"trabajador/menu-tramos.hbs");
    }

    public Response registrarNuevoTramo(Request request, Response response) throws Exception {
        if(SessionHelper.atributosNoSonNull(request,"medioTransporteId","alturaInicio","calleInicio","localidadInicioId","calleDestino","alturaDestino","localidadDestinoId","trayectoId")){
            ServicioCalcularDistancia.setAdapter(new ServicioGeoDDSRetrofitAdapter());
            MedioTransporte medio = this.repoTransportes.buscar(new Integer(request.queryParams("medioTransporteId")));
            Tramo tramo = new Tramo
                            (medio,
                            new Direccion
                                    (new Integer(request.queryParams("alturaInicio")),request.queryParams("calleInicio"),
                                            this.repoLocalidades.buscar(new Integer(request.queryParams("localidadInicioId")))),
                            new Direccion
                                    (new Integer(request.queryParams("alturaDestino")),request.queryParams("calleDestino"),
                                        this.repoLocalidades.buscar(new Integer(request.queryParams("localidadDestinoId"))))
                    );

            Trayecto trayecto = this.repoTrayectos.buscar(new Integer(request.queryParams("trayectoId")));
            trayecto.cargarTramos(tramo);
            PersistenciaHelper.persistir(trayecto);
        }
        response.redirect("/trabajador/trayectos");
        return response;
    }

    public ModelAndView mostrarUnirseATramoCompartido(Request request,Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        List<Tramo> tramos = this.repoTramos.buscarTodosTransportePrivado();
        parametros.put("tramos",tramos);
        parametros.put("trayectoId",request.queryParams("trayectoId"));
        return new ModelAndView(parametros,"trabajador/tramo-compartido-menu.hbs");
    }

    public Response unirseATramoCompartido(Request request, Response response) throws Exception {
        if (SessionHelper.atributosNoSonNull(request,"medioId","tramoId","trayectoId")){
            TransportePrivado tp = this.repoPrivados.buscar(new Integer(request.queryParams("medioId")));
            tp.agregarTrabajadorATramoCompartido(this.obtenerTrabajador(request,response));

            Tramo tramoAAgregar = this.repoTramos.buscar(new Integer(request.queryParams("tramoId")));

            Trayecto trayecto = this.repoTrayectos.buscar(new Integer(request.queryParams("trayectoId")));
            if(!trayecto.getTramos().contains(tramoAAgregar)){
                trayecto.cargarTramos(tramoAAgregar);
            }


            PersistenciaHelper.persistir(tp,trayecto);

        }else{
            response.redirect("/error");
        }
        response.redirect("/trabajador/trayectos");
        return response;
    }

    public Response editarTramo(Request request, Response response){ //todo pantalla de editar que esten los datos cargados, similar a la de los FE
        Tramo tramo = this.repoTramos.buscar(new Integer(request.queryParams("tramoId")));
        if(SessionHelper.atributosNoSonNull(request,"alturaInicio")){
            tramo.getPuntoInicio().setAltura(new Integer(request.queryParams("alturaInicio")));
        }
        //asi con todos los atributos
        response.redirect("/trabajador/trayecto");
        return response;
    }

    public Response registrarNuevoTrayecto(Request request, Response response) {
        if(SessionHelper.atributosNoSonNull(request,"alturaSalida","calleSalida","localidadSalidaId","alturaDestino","calleDestino",
                "localidadDestinoId","frecuenciaDeUso","cantidadPorMes")){

            Frecuencia frecuencia = new Frecuencia(Periodicidad.valueOf(request.queryParams("frecuenciaDeUso")),new Integer(request.queryParams("cantidadPorMes")));
            Trayecto trayecto = new Trayecto
                    (new Direccion(new Integer(request.queryParams("alturaSalida")),
                            request.queryParams("calleSalida"),
                            this.repoLocalidades.buscar(new Integer(request.queryParams("localidadSalidaId")))),
                    new Direccion(new Integer(request.queryParams("alturaDestino")),
                                    request.queryParams("calleDestino"),
                                    this.repoLocalidades.buscar(new Integer(request.queryParams("localidadDestinoId")))),
                            new ArrayList<>(),
                            frecuencia);

            Trabajador trabajador = this.obtenerTrabajador(request,response);
            trabajador.agregarTrayectos(trayecto);
            PersistenciaHelper.persistir(trayecto);
        }
        response.redirect("/trabajador/trayectos");
        return response;
    }

    public Response eliminarTrayecto(Request request, Response response){
        if(SessionHelper.atributosNoSonNull(request,"trayectoId")){
            Trayecto trayecto = this.repoTrayectos.buscar(new Integer(request.queryParams("trayectoId")));
            if(trayecto.getTramos().isEmpty()){
                PersistenciaHelper.eliminar(trayecto);
            }
            response.redirect("/trabajador/trayectos"); //probar si anda, no se si falta asociar el id
                //error de que no puede borrar

        }
        return response;
    }

    public Response eliminarTramo(Request request, Response response){
        if(SessionHelper.atributosNoSonNull(request,"tramoId")){
            Tramo tramo = this.repoTramos.buscar(new Integer(request.queryParams("tramoId"))); //todo buscar tramo del
            PersistenciaHelper.eliminar(tramo);
            response.redirect("/trabajador/trayectos"); //probar si anda, no se si falta asociar el id
        } else{
            response.redirect("/error");
        }
        return response;
    }

    private void setearCalculadoraHC(){
        CalculoHC.setFactoresEmisionFE(this.repoFE.buscarTodos());
        CalculoHC.setUnidadPorDefecto(UnidadHC.GRAMO_EQ);
    }


}
