package models.controllers;

import models.entities.organizaciones.entidades.Organizacion;
import models.repositories.Repositorio;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrganizacionController {

    private Repositorio<Organizacion> repo;

    public OrganizacionController() {
        this.repo = FactoryRepositorioDeOrganizaciones.get();
    }


    public ModelAndView mostrar(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Organizacion org = this.repo.buscar(new Integer(request.params("id")));
        if(org == null){ //try catch
            response.redirect("/error");
        }
        parametros.put("organizacion",org);
//        parametros.put("huella",org.calcularHCTotal());

        return new ModelAndView(parametros, "organizacion-menu.hbs");
    }

    public ModelAndView mostrarVinculaciones(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Organizacion org = this.repo.buscar(new Integer(request.params("id")));
        if(org == null){
            return new ModelAndView(parametros, "welcome.hbs");
        }
        parametros.put("organizacion",org);
        parametros.put("solicitudes",org.getListaDeSolicitudes());

        return new ModelAndView(parametros, "solicitudes-menu.hbs");
    }

}
