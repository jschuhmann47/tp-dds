package models.controllers;

import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.TipoOrganizacion;
import models.repositories.Repositorio;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class OrganizacionController {

    private Repositorio<Organizacion> repo;

    public OrganizacionController() {
        this.repo = FactoryRepositorioDeOrganizaciones.get();
    }


    public ModelAndView mostrar(Request request, Response response){
        HashMap<String,Object> parametros = new HashMap<>();
        Organizacion organizacionA = new Organizacion(null,null,
                "Valve Corporation S.A",null, TipoOrganizacion.EMPRESA,null);
        parametros.put("organizacion",organizacionA);
        return new ModelAndView(parametros, "organizacion-menu.hbs");
    }

}
