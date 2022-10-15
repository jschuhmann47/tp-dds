package models.controllers;

import models.entities.parametros.ParametroFE;
import models.helpers.MayusHelper;
import models.repositories.RepositorioDeParametrosFE;
import models.repositories.factories.FactoryRepositorioDeParametrosFE;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;

public class AdministradorController {

    RepositorioDeParametrosFE repoFE = FactoryRepositorioDeParametrosFE.get();

    public ModelAndView mostrar(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "administrador-menu.hbs");
    }

    public ModelAndView mostrarConfiguracionActualFE(Request request, Response response) {
        HashMap<String,Object> parametros = new HashMap<>();
        List<ParametroFE> parametrosFE = this.repoFE.buscarTodos();
        parametros.put("parametros",parametrosFE);
        return new ModelAndView(parametros, "factor-emision-menu.hbs");
    }

    public Response editarFE(Request request,Response response){
        editarParametroFE("auto",request);
        editarParametroFE("colectivo",request);
        //todo asi igual con todos los atributos
        return response;
    }

    private void editarParametroFE(String nombreAtributo, Request request){
        if(request.queryParams(nombreAtributo) != null){
            this.repoFE
                    .buscarNombre(MayusHelper.capitalize(nombreAtributo))
                    .setValor(new Double(request.queryParams(nombreAtributo)));
        }
    }
}
