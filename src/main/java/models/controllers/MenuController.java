package models.controllers;

import models.entities.seguridad.cuentas.Usuario;
import models.repositories.RepositorioDeUsuarios;
import models.repositories.factories.FactoryRepositorioDeUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

public class MenuController {

    public ModelAndView inicio(Request request, Response response){
        RepositorioDeUsuarios usuarios = FactoryRepositorioDeUsuarios.get();
        Map<String, Object> parametros = new HashMap<>();

        Usuario usuario = usuarios.buscar(request.session().attribute("id"));
        if(usuario == null){
            request.session().invalidate();
            Spark.halt();
        }
        parametros.put("usuario",usuario);
        return new ModelAndView(parametros,"menu.hbs");
    }
}
