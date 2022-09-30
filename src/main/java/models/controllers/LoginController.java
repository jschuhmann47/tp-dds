package models.controllers;

import models.entities.organizaciones.entidades.Organizacion;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class LoginController {

    public ModelAndView inicio(Request request, Response response){
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros,"login.hbs");
    }

    public Response login(Request request, Response response){
        try{
            RepositorioDeOrganizaciones organizaciones = FactoryRepositorioDeOrganizaciones.get();

            String nombreDeUsuario = request.queryParams("nombreDeUsuario");
            String contrasenia = request.queryParams("contrasenia");
            System.out.println("asdasdasdaaa" + nombreDeUsuario);
            //if(organizaciones.existe(nombreDeUsuario, contrasenia)){
            if(organizaciones.existePorId(nombreDeUsuario)){
                //Organizacion org = organizaciones.buscarOrganizacion(nombreDeUsuario, contrasenia);
                Organizacion org = organizaciones.buscarOrganizacionPorId(nombreDeUsuario);

                request.session(true);
                request.session().attribute("id", org.getId());

                response.redirect("/menu/" + org.getId());
            }
            else{
                response.redirect("/chau");

            }
        }
        catch (Exception e){
            //Funcionalidad disponible solo con persistencia en Base de Datos
            response.redirect("/error");
        }
        finally {
            return response;
        }
    }

    public Response logout(Request request, Response response){
        request.session().invalidate();
        response.redirect("/");
        return response;
    }
}
