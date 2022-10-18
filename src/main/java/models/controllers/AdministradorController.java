package models.controllers;

import models.entities.parametros.ParametroFE;
import models.helpers.StringHelper;
import models.repositories.RepositorioDeParametrosFE;
import models.repositories.factories.FactoryRepositorioDeParametrosFE;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class AdministradorController {

    //TODO crear organizaciones,sectores,usuarios,etc
    RepositorioDeParametrosFE repoFE = FactoryRepositorioDeParametrosFE.get();

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
        return null;
    }

    public Response crearNuevaOrganizacion(Request request, Response response){
        return null;
    }

    public ModelAndView mostrarNuevoSector(Request request, Response response){
        return null;
    }

    public Response crearNuevoSector(Request request, Response response){
        return null;
    }

    public ModelAndView mostrarNuevoTransporte(Request request, Response response){
        return null;
    }

    public Response crearNuevoTransporte(Request request, Response response){
        return null;
    }

    public ModelAndView mostrarNuevaParada(Request request, Response response){
        return null;
    }

    public Response crearNuevaParada(Request request, Response response){
        return null;
    }

}
