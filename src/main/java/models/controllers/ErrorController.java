package models.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class ErrorController {

    public static ModelAndView tirarError(Request request, Response response, String mensajeError){
         HashMap<String,Object> parametros = new HashMap<>();
         parametros.put("error",mensajeError);
         return new ModelAndView(parametros,"tirar-error.hbs");
    }
}
