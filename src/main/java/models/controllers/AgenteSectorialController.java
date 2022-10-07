package models.controllers;

import models.entities.organizaciones.entidades.Organizacion;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class AgenteSectorialController {

    public ModelAndView mostrar(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "agente-menu.hbs");
    }
}
