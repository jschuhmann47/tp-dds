package models.controllers;

import db.EntityManagerHelper;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.entidades.TipoOrganizacion;
import models.entities.parametros.ParametroFE;
import models.entities.transporte.publico.Parada;
import models.helpers.SessionHelper;
import models.helpers.StringHelper;
import models.repositories.*;
import models.repositories.factories.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdministradorController {

    //TODO crear organizaciones,sectores,usuarios,etc
    RepositorioDeParametrosFE repoFE = FactoryRepositorioDeParametrosFE.get();
    RepositorioDeProvincias repoProvincias = FactoryRepositorioDeProvincias.get();
    RepositorioDeMunicipios repoMunicipios = FactoryRepositorioDeMunicipios.get();
    RepositorioDeLocalidades repoLocalidades = FactoryRepositorioDeLocalidades.get();
    RepositorioDeOrganizaciones repoOrgs = FactoryRepositorioDeOrganizaciones.get();

    public ModelAndView mostrar(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "administrador-menu.hbs");
    }

    public ModelAndView mostrarConfiguracionActualFE(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        List<ParametroFE> parametrosFE = this.repoFE.buscarTodos();
        parametros.put("parametros",parametrosFE);
        return new ModelAndView(parametros, "factor-emision-menu.hbs");
    }

    public Response editarFE(Request request,Response response){
        ParametroFE parametroFE = this.repoFE.buscar(new Integer(request.queryParams("id"))); //TODO algo asi
        parametroFE.setValor(new Double(request.queryParams("nuevoValor")));
        return response;
    }

    private void editarParametroFE(String nombreAtributo, Request request){
        if(request.queryParams(nombreAtributo) != null){
            this.repoFE
                    .buscarNombre(StringHelper.capitalize(nombreAtributo))
                    .setValor(new Double(request.queryParams(nombreAtributo)));
        }
    }

    public ModelAndView mostrarNuevaOrganizacion(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("provincias",this.repoProvincias.buscarTodos()); //TODO ver de traer municipios y localidad de esa provincia solo

        return new ModelAndView(parametros,"nueva-organizacion.hbs");
    }

    public Response crearNuevaOrganizacion(Request request, Response response){
        if(SessionHelper.atributosNoSonNull(request,"razonSocial","altura","calle","localidadId","tipoOrganizacion")){
            List<String> clasificaciones = new ArrayList<>(); //TODO ajax
            String razonSocial = request.queryParams("razonSocial");
            Direccion direccion = new Direccion
                    (new Integer (request.queryParams("altura")),
                            request.queryParams("calle"),
                            this.repoLocalidades.buscar(new Integer(request.queryParams("localidadId"))));
            Organizacion nuevaOrg =
                    new Organizacion(clasificaciones,razonSocial,new ArrayList<>(), TipoOrganizacion.valueOf(request.queryParams("tipoOrganizacion")),direccion);

            EntityManagerHelper.beginTransaction();
            EntityManagerHelper.getEntityManager().persist(nuevaOrg);
            EntityManagerHelper.commit();
        }



        return null;
    }

    public ModelAndView mostrarNuevoSector(Request request, Response response){
        return new ModelAndView(null,"nuevo-sector.hbs");
    }

    public Response crearNuevoSector(Request request, Response response){
        if(SessionHelper.atributosNoSonNull(request,"organizacionId","nombreSector")){
            Sector sector = new Sector(this.repoOrgs.buscar(new Integer(request.queryParams("organizacionId"))),
                    request.queryParams("nombreSector"),
                    new ArrayList<>());

            EntityManagerHelper.beginTransaction();
            EntityManagerHelper.getEntityManager().persist(sector);
            EntityManagerHelper.commit();
        }

        return response;
    }

    public ModelAndView mostrarNuevoTransporte(Request request, Response response){
        return new ModelAndView(null,"nuevo-transporte");
    }

    public Response crearNuevoTransporte(Request request, Response response){
        if(SessionHelper.atributosNoSonNull(request,"tipoTransporte")){
            switch ("tipoTransporte"){
                //TODO
            }

        }

        return null;
    }

    public ModelAndView mostrarNuevaParada(Request request, Response response){
        return new ModelAndView(null,"nueva-parada.hbs");
    }

    public Response crearNuevaParada(Request request, Response response){
        Distancia distAnteriorParada = new Distancia(new Double(request.queryParams("distanciaAnterior")),"KM");
        Distancia distSiguienteParada = new Distancia(new Double(request.queryParams("distanciaSiguiente")),"KM");

        Parada parada = new Parada(distAnteriorParada,distSiguienteParada,null); //TODO

        return response;
    }

}
