package models.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class AlertasController {

    public static ModelAndView tirarError(Request request, Response response, String mensajeError){
         HashMap<String,Object> parametros = new HashMap<>();
         parametros.put("error",mensajeError);
         return new ModelAndView(parametros,"tirar-alerta.hbs");
    }

    public static ModelAndView tirarExito(Request request, Response response, String mensajeExito){
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("exito",mensajeExito);
        return new ModelAndView(parametros,"tirar-alerta.hbs");
    }
}
