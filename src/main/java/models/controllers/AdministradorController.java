package models.controllers;

import models.entities.organizaciones.entidades.Trabajador;
import models.entities.seguridad.cuentas.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class AdministradorController {

    public ModelAndView mostrar(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "administrador-menu.hbs");
    }
}
