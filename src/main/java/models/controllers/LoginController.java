package models.controllers;

import db.EntityManagerHelper;
import models.entities.seguridad.ValidadorContrasenia;
import models.entities.seguridad.cuentas.Permiso;
import models.entities.seguridad.cuentas.Rol;
import models.entities.seguridad.cuentas.TipoRecurso;
import models.entities.seguridad.cuentas.Usuario;
import models.helpers.HashingHelper;
import models.helpers.SessionHelper;
import models.repositories.RepositorioDeUsuarios;
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


            if(usuarios.existe(nombreDeUsuario,contrasenia)){
                Usuario usuario = usuarios.buscar(nombreDeUsuario,contrasenia);

                request.session(true);
                request.session().attribute("id", usuario.getId());
                request.session().attribute("resource_type",usuario.getTipoRecurso().toString());
                request.session().attribute("resource_id",usuario.obtenerIdRecurso());
                request.session().attribute("permissions",usuario.getPermisos());


                response.redirect("/" + usuario.getTipoRecurso().toString().toLowerCase());
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

    public ModelAndView mostrarNuevoUsuario(Request request, Response response){
        return new ModelAndView(null,"nuevo-usuario.hbs");
    }

    public Response crearNuevoUsuario(Request request, Response response){ //validar con el owasp
        if(SessionHelper.atributosNoSonNull(request,"contrasenia","tipoRecurso","nombreUsuario","rol")){
            String contrasenia = request.queryParams("contrasenia");
            TipoRecurso tipoRecurso = TipoRecurso.valueOf(request.queryParams("tipoRecurso"));
            if(ValidadorContrasenia.esContraseniaValida(contrasenia)){
                Integer idRecurso = 0; //trabajador, org, o municipio del agente
                switch (tipoRecurso){ //todo preguntar luego bien quien se puede registrar
                    case ORGANIZACION:
                        break;
                    case TRABAJADOR:
                        break;
                    case AGENTE:
                        break;
                }

                Usuario nuevoUser = new Usuario(request.queryParams("nombreUsuario"),
                        contrasenia,
                        Rol.valueOf(request.queryParams("rol")),
                        idRecurso,
                        tipoRecurso,
                        Permiso.VER_ORGANIZACION);
                EntityManagerHelper.getEntityManager().persist(nuevoUser);
            }
        }
        return response;
    }

}
