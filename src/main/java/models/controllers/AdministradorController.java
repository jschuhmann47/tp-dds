package models.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class AdministradorController {

    public ModelAndView mostrar(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "administrador-menu.hbs");
    }

    public ModelAndView mostrarConfiguracionActualFE(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "factor-emision-menu.hbs");
    }

    public ModelAndView mostrarEdicionFE(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "factor-emision-editar.hbs");
    }

    public Response editarFE(Request request,Response response){
        //TODO buscar y guardar el valor nuevo
        return response;
    }
}
