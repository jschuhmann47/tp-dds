package models.controllers;

import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Provincia;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.entidades.TipoOrganizacion;
import models.entities.parametros.ParametroFE;
import models.entities.transporte.publico.Parada;
import models.helpers.PersistenciaHelper;
import models.helpers.SessionHelper;
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
    RepositorioDeMediosDeTransporte repoTransportes = FactoryRepositorioDeMediosDeTransporte.get();
    RepositorioDeSectores repoSectores = FactoryRepositorioDeSectores.get();

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
        if(SessionHelper.atributosNoSonNull(request,"nuevoValor","idParametro")){
            ParametroFE parametroFE = this.repoFE.buscar(new Integer(request.queryParams("idParametro")));
            parametroFE.setValor(new Double(request.queryParams("nuevoValor")));
            PersistenciaHelper.persistir(parametroFE);
        }
        response.redirect("/administrador/config");
        return response;
    }

    public ModelAndView mostrarNuevaOrganizacion(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        this.setearDesplegablesDeLocalidades(request, parametros);
        if(SessionHelper.atributosNoSonNull(request,"localidadId")){
            this.crearNuevaOrganizacion(request,response);
        }

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

            PersistenciaHelper.persistir(nuevaOrg);
        }


        return null;
    }

    public ModelAndView mostrarSectores(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Organizacion organizacion = this.repoOrgs.buscar(new Integer(request.queryParams("organizacionId")));
        parametros.put("sectores",organizacion.getSectores());
        return new ModelAndView(parametros,"sectores.hbs");
    }

    public Response crearNuevoSector(Request request, Response response){
        if(SessionHelper.atributosNoSonNull(request,"organizacionId","nombreSector")){
            Sector nuevoSector = new Sector(this.repoOrgs.buscar(new Integer(request.queryParams("organizacionId"))),
                    request.queryParams("nombreSector"),
                    new ArrayList<>());

            PersistenciaHelper.persistir(nuevoSector);
        }

        return response;
    }

    public ModelAndView mostrarTransportes(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("transportes",this.repoTransportes.buscarTodos()); //si es publico poner boton de mostrar paradas
        return new ModelAndView(parametros,"transportes.hbs");
    }

    public ModelAndView mostrarNuevoTransporte(Request request, Response response){
        //todo traer los tipos de vehiculos de la base o de memoria?
        return new ModelAndView(null,"nuevo-transporte.hbs");
    }

    public Response crearNuevoTransporte(Request request, Response response){
        if(SessionHelper.atributosNoSonNull(request,"tipoTransporte")){
            switch (request.queryParams("tipoTransporte")){ //el tipo privado publico etc, no el del transporte
                //TODO
            }

        }

        return null;
    }

    public ModelAndView mostrarParadas(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("paradas",this.repoTransportes.buscar(new Integer(request.queryParams("transportePublicoId"))));
        return new ModelAndView(parametros,"paradas.hbs");
    }

    public ModelAndView mostrarNuevaParada(Request request, Response response){
        return new ModelAndView(null,"nueva-parada.hbs");
    }

    public Response crearNuevaParada(Request request, Response response){
        Distancia distAnteriorParada = new Distancia(new Double(request.queryParams("distanciaAnterior")),"KM");
        Distancia distSiguienteParada = new Distancia(new Double(request.queryParams("distanciaSiguiente")),"KM");
        Direccion direccion = new Direccion
                (new Integer (request.queryParams("altura")),
                        request.queryParams("calle"),
                        this.repoLocalidades.buscar(new Integer(request.queryParams("localidadId"))));

        Parada parada = new Parada(distAnteriorParada,distSiguienteParada,direccion); //TODO

        PersistenciaHelper.persistir(parada);

        return response;
    }

    private void setearDesplegablesDeLocalidades(Request request, HashMap<String,Object> parametros){
        parametros.put("provincias",this.repoProvincias.buscarTodos());
        if(SessionHelper.atributosNoSonNull(request,"provinciaId")){
            Provincia provincia = this.repoProvincias.buscar(new Integer(request.queryParams("provinciaId")));
            parametros.put("municipios",this.repoMunicipios.buscarMunicipiosDeProvincia(provincia));
            if(SessionHelper.atributosNoSonNull(request,"municipioId")){
                Municipio municipio = this.repoMunicipios.buscar(new Integer(request.queryParams("municipioId")));
                parametros.put("localidades",this.repoLocalidades.buscarLocalidadesDeMunicipio(municipio));
            }
        }
    }

    public ModelAndView mostrarOrganizaciones(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("organizaciones",this.repoOrgs.buscarTodos());
        return new ModelAndView(parametros,"organizaciones-menu-admin.hbs");
    }

    public Response eliminarOrganizacion(Request request, Response response) {
        Organizacion organizacionAElminar = this.repoOrgs.buscar(new Integer(request.queryParams("organizacionId")));
        PersistenciaHelper.eliminar(organizacionAElminar); //ver q onda con los trabajadores de la org que se elimina
        response.redirect("/organizaciones");
        return response;
    }

    public ModelAndView mostrarNuevoSector(Request request, Response response) {
        return new ModelAndView(null,"nuevo-sector.hbs");
    }


    public Response eliminarSector(Request request, Response response) {
        Sector sectorAEliminar = this.repoSectores.buscar(new Integer(request.queryParams("sectorId")));
        PersistenciaHelper.eliminar(sectorAEliminar);
        response.redirect("/organizacion/sectores"); //todo arreglar las rutas
        return response;
    }
}
