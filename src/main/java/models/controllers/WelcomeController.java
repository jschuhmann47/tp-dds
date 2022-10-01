package models.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class WelcomeController {

    public ModelAndView inicio(Request request, Response response){
        return new ModelAndView(new HashMap<>(),"welcome.hbs");
    }
}
