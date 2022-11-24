package models.controllers;

import models.entities.seguridad.cuentas.Usuario;
import models.helpers.SessionHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class ErrorController {

    public ModelAndView prohibido(Request request, Response response){
        return new ModelAndView(null,"prohibido.hbs");
    }

    public ModelAndView error(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Usuario user = SessionHelper.usuarioLogueado(request);
        if(user == null){
            parametros.put("rutaInicio", "login");
        }else{
            parametros.put("rutaInicio", user.getTipoCuenta());
        }
        return new ModelAndView(parametros,"404.hbs");
    }
}
