package models.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ErrorHandlerController {

    public ModelAndView prohibido(Request request, Response response){
        return new ModelAndView(null,"prohibido.hbs");
    }

    public ModelAndView error(Request request, Response response){
        return new ModelAndView(null,"error.hbs");
    }
}
