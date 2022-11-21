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

    public ModelAndView composicionHCPais(Request request, Response response){ //OK
        this.setearParametrosFE();
        HashMap<String, Object> parametros = new HashMap<>();

        List<Reporte> reportes = GeneradorReporte.ComposicionHCTotalPorProvincias(
                this.repoOrg.buscarTodos(),
                this.repoProvincia.buscarTodos()
        );
        parametros.put("reportes", reportes);
        return new ModelAndView(parametros,"agente/composicion-hc-pais.hbs");
    }

    public ModelAndView composicionHCOrganizacion(Request request, Response response){ //OK
        this.setearParametrosFE();
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion organizacion = this.repoOrg.buscar(new Integer(request.queryParams("organizacionId")));
        if(organizacion!=null){
            parametros.put("reportes",GeneradorReporte.ComposicionHCTotalDeUnaOrganizacion(organizacion));
        }
        return new ModelAndView(parametros,"agente/composicion-hc-organizacion.hbs");
    }

    public ModelAndView mostrarComposicionHCOrganizacion(Request request, Response response){ //OK
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("organizaciones",this.repoOrg.buscarTodos());
        return new ModelAndView(parametros,"agente/composicion-hc-organizacion.hbs");
    }

    public ModelAndView composicionHCMunicipio(Request request, Response response) throws IOException { //todo testear tema api
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
                parametros.put("municipios",this.repoMunicipio.buscarMunicipiosDeProvincia(provincia));
            }
            return new ModelAndView(parametros,"agente/composicion-hc-territorio-p.hbs");
        } else{
            parametros.put("provincias",ServicioCalcularDistancia.obtenerProvincias()); //this.repoProvincia.buscarTodos()
            return new ModelAndView(parametros,"agente/composicion-hc-territorio.hbs");
        }

    }

    public ModelAndView mostrarComposicionHCMunicipio(Request request, Response response) throws IOException { //hace get y post to_do aca //OK
        HashMap<String, Object> parametros = new HashMap<>();
//        this.setearDesplegablesDeMunicipios(request,response,parametros);
        parametros.put("provincias",this.repoProvincia.buscarTodos());
//        if(SessionHelper.atributosNoSonNull(request,"municipioId")){
//            return this.composicionHCMunicipio(request,response);
//        }
        return new ModelAndView(parametros,"agente/composicion-hc-territorio.hbs");
    }

    private void setearDesplegablesDeMunicipios(Request request, Response response, HashMap<String,Object> parametros){ //OK
        if(SessionHelper.atributosNoSonNull(request,"provinciaId")){
            Provincia provincia = this.repoProvincia.buscar(new Integer(request.queryParams("provinciaId")));
            parametros.put("provincia",provincia);
            parametros.put("municipios",this.repoMunicipio.buscarMunicipiosDeProvincia(provincia));
        }
    }

    public ModelAndView mostrarEvolucionHCMunicipio(Request request, Response response) { //OK
        HashMap<String, Object> parametros = new HashMap<>();
        this.setearDesplegablesDeMunicipios(request,response,parametros);
        if(SessionHelper.atributosNoSonNull(request,"municipioId","mes","anio")){
            return this.evolucionHCMunicipio(request,response);
        }
        return new ModelAndView(parametros,"agente/evolucion-hc-territorio.hbs");
    }

    public ModelAndView evolucionHCMunicipio(Request request, Response response){ //OK
        this.setearParametrosFE();
        HashMap<String, Object> parametros = new HashMap<>();
        Municipio municipio = this.repoMunicipio.buscar(new Integer(request.queryParams("municipioId")));
        Periodo periodo = PeriodoHelper.nuevoPeriodo(request.queryParams("mes"),request.queryParams("anio"));
        if(municipio != null){
            parametros.put("reportes", GeneradorReporte.evolucionHCTotalSectorTerritorial(this.repoOrg.buscarTodosDeMunicipio(municipio),municipio,periodo));
        }
        return new ModelAndView(parametros,"agente/evolucion-hc-territorio.hbs");
    }

    public ModelAndView HCPorClasificacionOrganizacion(Request request, Response response){
        this.setearParametrosFE();
        HashMap<String, Object> parametros = new HashMap<>();

        String clasificacion = request.queryParams("clasificacion");
        parametros.put("reportes",GeneradorReporte.HCTotalPorClasificacion(this.repoOrg.buscarTodosClasificacion(clasificacion),clasificacion));

        return new ModelAndView(parametros,"agente/hc-clasificacion-organizacion.hbs");
    }

    public ModelAndView evolucionHCOrganizacion(Request request, Response response){
        this.setearParametrosFE();
        HashMap<String, Object> parametros = new HashMap<>();
        Organizacion organizacion = this.repoOrg.buscarPorRazonSocial(request.queryParams("razonSocial"));
        if(organizacion != null){
            Periodo periodo = PeriodoHelper.nuevoPeriodo(request.queryParams("mes"),request.queryParams("anio"));
            parametros.put("reportes",GeneradorReporte.evolucionHCTotalOrganizacion(organizacion,periodo));
        }
        return new ModelAndView(parametros,"agente/evolucion-hc-organizacion.hbs");
    }

    public ModelAndView HCPorMunicipio(Request request, Response response){
        this.setearParametrosFE();
        HashMap<String, Object> parametros = new HashMap<>();
        Municipio municipio = this.buscarMunicipio(request.queryParams("municipio"),request.queryParams("provincia"));
        if(municipio != null){
            parametros.put("reportes", GeneradorReporte.HCTotalPorSectorTerritorial(this.repoOrg.buscarTodosDeMunicipio(municipio),municipio));
        }
        return new ModelAndView(parametros,"agente/hc-total-territorio.hbs");
    }

    public ModelAndView mostrarHCPorMunicipio(Request request, Response response){
        return new ModelAndView(null,"agente/hc-total-territorio.hbs");
    }

    private Municipio buscarMunicipio(String nombreMunicipio, String nombreProvincia){
        return this.repoMunicipio.buscarNombre(nombreMunicipio,nombreProvincia);
    }

    private Provincia buscarProvincia(String nombreProvincia){
        return this.repoProvincia.buscarNombre(nombreProvincia);
    }

    public ModelAndView mostrarReportes(Request request, Response response) {
        return new ModelAndView(null,"agente/reportes-menu.hbs");
    }

    public ModelAndView mostrarEvolucionHCOrganizacion(Request request, Response response) {
        return new ModelAndView(null,"agente/evolucion-hc-organizacion.hbs");
    }


    public ModelAndView mostrarHCPorClasificacionOrganizacion(Request request, Response response) {
        return new ModelAndView(null,"agente/hc-clasificacion-organizacion.hbs");
    }

    private void setearParametrosFE(){
        CalculoHC.setFactoresEmisionFE(this.repoFE.buscarTodos());
        CalculoHC.setUnidadPorDefecto(UnidadHC.GRAMO_EQ);
    }
}
