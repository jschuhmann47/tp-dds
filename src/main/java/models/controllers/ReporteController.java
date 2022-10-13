package models.controllers;

import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Provincia;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.reportes.GeneradorReporte;
import models.repositories.Repositorio;
import models.repositories.RepositorioDeMunicipios;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.RepositorioDeProvincias;
import models.repositories.factories.FactoryRepositorioDeMunicipios;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeProvincias;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class ReporteController {
    private RepositorioDeOrganizaciones repoOrg;
    private RepositorioDeMunicipios repoMunicipio;
    private RepositorioDeProvincias repoProvincia;

    public ReporteController(){
        this.repoOrg = FactoryRepositorioDeOrganizaciones.get();
        this.repoMunicipio = FactoryRepositorioDeMunicipios.get();
        this.repoProvincia = FactoryRepositorioDeProvincias.get();
    }

    public ModelAndView mostrarMenu(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    public ModelAndView composicionHCTerritorio(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        Municipio municipio = this.buscarMunicipio(request.queryParams("municipio"),request.queryParams("provincia"));
        parametros.put("reporte", GeneradorReporte.ComposicionHCTotalPorSectorTerritorial(repoOrg.buscarTodos(),municipio));
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    public ModelAndView composicionHCPais(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    public ModelAndView composicionHCOrganizacion(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    public ModelAndView composicionHCMunicipio(Request request, Response response){ //Sector territorial
        HashMap<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    public ModelAndView evolucionHCMunicipio(Request request, Response response){ //
        HashMap<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    public ModelAndView HCPorTipoOrganizacion(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    public ModelAndView evolucionHCOrganizacion(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    private Municipio buscarMunicipio(String nombreMunicipio, String nombreProvincia){
        return this.repoMunicipio.buscarNombre(nombreMunicipio,nombreProvincia);
    }

    private Provincia buscarProvincia(String nombreProvincia){
        return this.repoProvincia.buscarNombre(nombreProvincia);
    }
}
