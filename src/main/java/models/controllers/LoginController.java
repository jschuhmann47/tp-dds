package models.controllers;

import models.entities.organizaciones.entidades.Organizacion;
import models.entities.seguridad.cuentas.Usuario;
import models.helpers.HashingHelper;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.RepositorioDeUsuarios;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeUsuarios;
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
            RepositorioDeUsuarios usuarios = FactoryRepositorioDeUsuarios.get();

            String nombreDeUsuario = request.queryParams("nombreDeUsuario");
            String contrasenia = HashingHelper.hashear(request.queryParams("contrasenia"));

            //if(organizaciones.existe(nombreDeUsuario, contrasenia)){
            if(usuarios.existe(nombreDeUsuario,contrasenia)){
                //Organizacion org = organizaciones.buscarOrganizacion(nombreDeUsuario, contrasenia);
                Usuario usuario = usuarios.buscar(nombreDeUsuario,contrasenia);

                request.session(true);
                request.session().attribute("id", usuario.getId());

                response.redirect("/organizaciones/" + usuario.getId());
            }
            else{
                response.redirect("/login");

            }
        }
        catch (Exception e){
            //Funcionalidad disponible solo con persistencia en Base de Datos
            response.redirect("/login");

        }
        finally {
            return response;
        }
    }

    public Response logout(Request request, Response response){
        request.session().invalidate();
        response.redirect("/login");
        return response;
    }
}
