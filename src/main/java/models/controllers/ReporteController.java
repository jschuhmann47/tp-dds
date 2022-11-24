package models.controllers;

import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.calculoHC.CalculoHC;
import models.entities.calculoHC.UnidadHC;
import models.entities.geoDDS.ServicioCalcularDistancia;
import models.entities.geoDDS.adapters.ServicioGeoDDSRetrofitAdapter;
import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Provincia;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.reportes.GeneradorReporte;
import models.entities.reportes.Reporte;
import models.helpers.PeriodoHelper;
import models.helpers.SessionHelper;
import models.repositories.*;
import models.repositories.factories.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ReporteController {
    private final RepositorioDeOrganizaciones repoOrg;
    private final RepositorioDeMunicipios repoMunicipio;
    private final RepositorioDeProvincias repoProvincia;
    private final RepositorioDeParametrosFE repoFE;
    private final RepositorioDeLocalidades repoLocalidad;

    public ReporteController(){
        this.repoOrg = FactoryRepositorioDeOrganizaciones.get();
        this.repoMunicipio = FactoryRepositorioDeMunicipios.get();
        this.repoProvincia = FactoryRepositorioDeProvincias.get();
        this.repoFE = FactoryRepositorioDeParametrosFE.get();
        this.repoLocalidad = FactoryRepositorioDeLocalidades.get();
    }

    public ModelAndView mostrarMenu(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"agente/reportes-menu.hbs");
    }

    public ModelAndView composicionHCPais(Request request, Response response){ 
        this.setearParametrosFE();
        HashMap<String, Object> parametros = new HashMap<>();

        List<Reporte> reportes = GeneradorReporte.ComposicionHCTotalPorProvincias(
                this.repoOrg.buscarTodos(),
                this.repoProvincia.buscarTodos()
        );
        parametros.put("reportes", reportes);
        return new ModelAndView(parametros,"agente/composicion-hc-pais.hbs");
    }

    public ModelAndView composicionHCOrganizacion(Request request, Response response){ 
        this.setearParametrosFE();
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion organizacion = this.repoOrg.buscar(new Integer(request.queryParams("organizacionId")));
        if(organizacion!=null){
            parametros.put("reportes",GeneradorReporte.ComposicionHCTotalDeUnaOrganizacion(organizacion));
        }
        return new ModelAndView(parametros,"agente/composicion-hc-organizacion.hbs");
    }

    public ModelAndView mostrarComposicionHCOrganizacion(Request request, Response response){ 
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("organizaciones",this.repoOrg.buscarTodos());
        return new ModelAndView(parametros,"agente/composicion-hc-organizacion.hbs");
    }

    public ModelAndView composicionHCMunicipio(Request request, Response response) throws IOException {
        this.setearParametrosFE();
        ServicioCalcularDistancia.setAdapter(new ServicioGeoDDSRetrofitAdapter());
        HashMap<String, Object> parametros = new HashMap<>();
        if(SessionHelper.atributosNoSonNull(request,"provinciaId")){
            Provincia provincia = this.repoProvincia.buscar(new Integer(request.queryParams("provinciaId")));
            parametros.put("provincia",provincia);
            if(SessionHelper.atributosNoSonNull(request,"municipioId")){
                Municipio municipio = this.repoMunicipio.buscar(new Integer(request.queryParams("municipioId")));
                parametros.put("reportes", GeneradorReporte.ComposicionHCTotalPorSectorTerritorial(this.repoOrg.buscarTodosDeMunicipio(municipio),municipio));
            }else{
//                parametros.put("municipios",ServicioCalcularDistancia.municipiosDeProvincia(new Integer(request.queryParams("provinciaId"))));
                parametros.put("municipios",ServicioCalcularDistancia.municipiosDeProvincia(provincia.getId()));
            }
            return new ModelAndView(parametros,"agente/composicion-hc-territorio-p.hbs");
        } else{
            parametros.put("provincias",ServicioCalcularDistancia.obtenerProvincias()); //this.repoProvincia.buscarTodos()
            return new ModelAndView(parametros,"agente/composicion-hc-territorio.hbs");
        }

    }

    public ModelAndView mostrarComposicionHCMunicipio(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("provincias",this.repoProvincia.buscarTodos());
        return new ModelAndView(parametros,"agente/composicion-hc-territorio.hbs");
    }

    public ModelAndView mostrarEvolucionHCMunicipio(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("provincias",this.repoProvincia.buscarTodos());
        return new ModelAndView(parametros,"agente/evolucion-hc-territorio.hbs");
    }

    public ModelAndView evolucionHCMunicipio(Request request, Response response) throws IOException { 
        this.setearParametrosFE();
        ServicioCalcularDistancia.setAdapter(new ServicioGeoDDSRetrofitAdapter());
        HashMap<String, Object> parametros = new HashMap<>();
        if(SessionHelper.atributosNoSonNull(request,"provinciaId")){
            Provincia provincia = this.repoProvincia.buscar(new Integer(request.queryParams("provinciaId")));
            parametros.put("provincia",provincia);
            if(SessionHelper.atributosNoSonNull(request,"municipioId")){
                Municipio municipio = this.repoMunicipio.buscar(new Integer(request.queryParams("municipioId")));
                Periodo periodo = PeriodoHelper.nuevoPeriodo(request.queryParams("mes"),request.queryParams("anio"));
                parametros.put("reportes", Collections.singletonList(GeneradorReporte.evolucionHCTotalSectorTerritorial(this.repoOrg.buscarTodosDeMunicipio(municipio),municipio,periodo)));
            }else{
//                parametros.put("municipios",ServicioCalcularDistancia.municipiosDeProvincia(new Integer(request.queryParams("provinciaId"))));
                parametros.put("municipios",ServicioCalcularDistancia.municipiosDeProvincia(provincia.getId()));
            }
            return new ModelAndView(parametros,"agente/evolucion-hc-territorio-p.hbs");
        } else{
            parametros.put("provincias",ServicioCalcularDistancia.obtenerProvincias()); //this.repoProvincia.buscarTodos()
            return new ModelAndView(parametros,"agente/evolucion-hc-territorio.hbs");
        }
    }

    public ModelAndView HCPorClasificacionOrganizacion(Request request, Response response){
        this.setearParametrosFE();
        HashMap<String, Object> parametros = new HashMap<>();

        String clasificacion = request.queryParams("clasificacion");
        parametros.put("reportes",Collections.singletonList(GeneradorReporte.HCTotalPorClasificacion(this.repoOrg.buscarTodosClasificacion(clasificacion),clasificacion)));

        return new ModelAndView(parametros,"agente/hc-clasificacion-organizacion.hbs");
    }

    public ModelAndView evolucionHCOrganizacion(Request request, Response response){
        this.setearParametrosFE();
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion organizacion = this.repoOrg.buscar(new Integer(request.queryParams("organizacionId")));
        if(organizacion != null){
            Periodo periodo = PeriodoHelper.nuevoPeriodo(request.queryParams("mes"),request.queryParams("anio"));
            parametros.put("reportes",Collections.singletonList(GeneradorReporte.evolucionHCTotalOrganizacion(organizacion,periodo)));
        }
        return new ModelAndView(parametros,"agente/evolucion-hc-organizacion.hbs");
    }

    public ModelAndView HCPorMunicipio(Request request, Response response) throws IOException { //repite logica dps refactorear
        this.setearParametrosFE();
        ServicioCalcularDistancia.setAdapter(new ServicioGeoDDSRetrofitAdapter());
        HashMap<String, Object> parametros = new HashMap<>();
        if(SessionHelper.atributosNoSonNull(request,"provinciaId")){
            Provincia provincia = this.repoProvincia.buscar(new Integer(request.queryParams("provinciaId")));
            parametros.put("provincia",provincia);
            if(SessionHelper.atributosNoSonNull(request,"municipioId")){
                Municipio municipio = this.repoMunicipio.buscar(new Integer(request.queryParams("municipioId")));
                parametros.put("reportes", Collections.singletonList(GeneradorReporte.HCTotalPorSectorTerritorial(this.repoOrg.buscarTodosDeMunicipio(municipio), municipio)));
            }else{
//                parametros.put("municipios",ServicioCalcularDistancia.municipiosDeProvincia(new Integer(request.queryParams("provinciaId"))));
                parametros.put("municipios",ServicioCalcularDistancia.municipiosDeProvincia(provincia.getId()));
            }
            return new ModelAndView(parametros,"agente/hc-total-municipio-p.hbs");
        } else{
            parametros.put("provincias",ServicioCalcularDistancia.obtenerProvincias()); //this.repoProvincia.buscarTodos()
            return new ModelAndView(parametros,"agente/hc-total-municipio.hbs");
        }

    }


    public ModelAndView mostrarHCPorMunicipio(Request request, Response response){
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("provincias",this.repoProvincia.buscarTodos());
        return new ModelAndView(parametros,"agente/hc-total-municipio.hbs");
    }

    public ModelAndView mostrarReportes(Request request, Response response) {
        return new ModelAndView(null,"agente/reportes-menu.hbs");
    }

    public ModelAndView mostrarEvolucionHCOrganizacion(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("organizaciones",this.repoOrg.buscarTodos());
        return new ModelAndView(parametros,"agente/evolucion-hc-organizacion.hbs");
    }


    public ModelAndView mostrarHCPorClasificacionOrganizacion(Request request, Response response) {
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("organizaciones",this.repoOrg.buscarTodos());
        return new ModelAndView(parametros,"agente/hc-clasificacion-organizacion.hbs");
    }

    private void setearParametrosFE(){
        CalculoHC.setFactoresEmisionFE(this.repoFE.buscarTodos());
        CalculoHC.setUnidadPorDefecto(UnidadHC.GRAMO_EQ);
    }
}
