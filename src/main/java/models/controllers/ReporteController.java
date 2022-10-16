package models.controllers;

import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Provincia;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.reportes.GeneradorReporte;
import models.entities.reportes.Reporte;
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
import java.util.List;

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

    public ModelAndView composicionHCPais(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();

        List<Reporte> reportes = GeneradorReporte.ComposicionHCTotalPorProvincias(
                this.repoOrg.buscarTodos(),
                this.repoProvincia.buscarTodos()  //se asume que son todas de arg, se puede hacer mas extensible pero no es prioridad ahora
        );
        parametros.put("reportes", reportes);
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    public ModelAndView composicionHCOrganizacion(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion organizacion = this.repoOrg.buscarPorRazonSocial(request.queryParams("razonSocial"));
        if(organizacion!=null){
            parametros.put("reportes",GeneradorReporte.ComposicionHCTotalDeUnaOrganizacion(organizacion));
        }
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    public ModelAndView composicionHCMunicipio(Request request, Response response){ //Sector territorial
        HashMap<String, Object> parametros = new HashMap<>();
        Municipio municipio = this.buscarMunicipio(request.queryParams("municipio"),request.queryParams("provincia"));
        if(municipio != null){
            parametros.put("reportes", GeneradorReporte.ComposicionHCTotalPorSectorTerritorial(this.repoOrg.buscarTodosDeMunicipio(municipio),municipio));
        }
        return new ModelAndView(parametros,"reportes-composicion-hc-municipio.hbs");
    }

    public ModelAndView evolucionHCMunicipio(Request request, Response response){ //
        HashMap<String, Object> parametros = new HashMap<>();
        Municipio municipio = this.buscarMunicipio(request.queryParams("municipio"),request.queryParams("provincia"));
        Periodo periodo = new Periodo(new Integer(request.queryParams("mes")),new Integer(request.queryParams("anio")));
        if(municipio != null){
            parametros.put("reportes", GeneradorReporte.evolucionHCTotalSectorTerritorial(this.repoOrg.buscarTodosDeMunicipio(municipio),municipio,periodo));
        }
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    public ModelAndView HCPorClasificacionOrganizacion(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();

        String clasificacion = request.queryParams("clasificacion");
        parametros.put("reportes",GeneradorReporte.HCTotalPorClasificacion(this.repoOrg.buscarTodosClasificacion(clasificacion),clasificacion));

        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    public ModelAndView evolucionHCOrganizacion(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion organizacion = this.repoOrg.buscarPorRazonSocial(request.queryParams("razonSocial"));
        if(organizacion != null){
            Periodo periodo = new Periodo(new Integer(request.queryParams("mes")),new Integer(request.queryParams("anio")));
            parametros.put("reportes",GeneradorReporte.evolucionHCTotalOrganizacion(organizacion,periodo));
        }
        return new ModelAndView(parametros,"reportes-menu.hbs");
    }

    private Municipio buscarMunicipio(String nombreMunicipio, String nombreProvincia){
        return this.repoMunicipio.buscarNombre(nombreMunicipio,nombreProvincia);
    }

    private Provincia buscarProvincia(String nombreProvincia){
        return this.repoProvincia.buscarNombre(nombreProvincia);
    }
}
