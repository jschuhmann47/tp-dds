package models.controllers;

import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Trabajador;
import models.repositories.Repositorio;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeTrabajadores;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class TrabajadorController {
    private Repositorio<Trabajador> repo;

    public TrabajadorController() {
        this.repo = FactoryRepositorioDeTrabajadores.get();
    }



    public ModelAndView mostrar(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        Trabajador trabajador = this.repo.buscar(new Integer(request.session().attribute("id").toString())); //todo validar
        if(trabajador == null){ //try catch
            response.redirect("/error");
        }
        parametros.put("trabajador",trabajador);

        return new ModelAndView(parametros, "trabajador-menu.hbs");
    }
}
