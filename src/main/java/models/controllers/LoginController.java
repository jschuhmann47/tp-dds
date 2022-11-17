package models.controllers;

import db.EntityManagerHelper;
import models.entities.organizaciones.entidades.PosibleTipoDocumento;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.seguridad.ValidadorContrasenia;
import models.entities.seguridad.chequeos.Chequeo;
import models.entities.seguridad.cuentas.Permiso;
import models.entities.seguridad.cuentas.Rol;
import models.entities.seguridad.cuentas.TipoRecurso;
import models.entities.seguridad.cuentas.Usuario;
import models.helpers.HashingHelper;
import models.helpers.PersistenciaHelper;
import models.helpers.SessionHelper;
import models.repositories.RepositorioDeTipoDocumento;
import models.repositories.RepositorioDeUsuarios;
import models.repositories.factories.FactoryRepositorioDeTipoDocumento;
import models.repositories.factories.FactoryRepositorioDeUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.io.IOException;
import java.util.*;

public class LoginController {

    RepositorioDeTipoDocumento repoTipoDoc = FactoryRepositorioDeTipoDocumento.get();
    RepositorioDeUsuarios repoUsuarios = FactoryRepositorioDeUsuarios.get();

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

                response.redirect("/" + usuario.getTipoCuenta());
            }
            else{
                response.redirect("/login");

            }
        }
        catch (Exception e){
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
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("tiposDocumento",this.repoTipoDoc.buscarTodos());
        return new ModelAndView(parametros,"nuevo-usuario.hbs");
    }

    public ModelAndView crearNuevoUsuario(Request request, Response response) throws IOException {
        HashMap<String,Object> parametros = new HashMap<>();
        if(SessionHelper.atributosNoSonNull(request,"contrasenia","contraseniaChequeo","nombreUsuario","tipoDocumentoId","nroDocumento","nombre","apellido")){
            String contrasenia = request.queryParams("contrasenia");
            String contraseniaVerificacion = request.queryParams("contraseniaChequeo");
            if(!Objects.equals(contrasenia, contraseniaVerificacion)){
                parametros.put("error","Las contraseñas no coinciden. Por favor intente nuevamente.");
                return new ModelAndView(parametros,"status-nuevo-usuario.hbs");
            }
            if(this.repoUsuarios.existeUsuario(request.queryParams("nombreUsuario"))){
                parametros.put("error","Usuario ya existente. Por favor ingrese otro.");
                return new ModelAndView(parametros,"status-nuevo-usuario.hbs");
            }
            ValidadorContrasenia.inicializarChequeos();
            ValidadorContrasenia.setearPeoresContrasenias("src/main/java/models/entities/" +
                    "seguridad/chequeos/peoresContrasenias.txt");
            if(ValidadorContrasenia.esContraseniaValida(contrasenia)){

                Trabajador nuevoTrabajador = new Trabajador
                        (request.queryParams("apellido"), request.queryParams("nombre"),
                                this.repoTipoDoc.buscar(new Integer(request.queryParams("tipoDocumentoId"))),new Integer(request.queryParams("nroDocumento")));
                PersistenciaHelper.persistir(nuevoTrabajador);

                Usuario nuevoUser = new Usuario(request.queryParams("nombreUsuario"),
                        contrasenia,
                        Rol.BASICO,
                        nuevoTrabajador.getId(),
                        TipoRecurso.TRABAJADOR,
                        Permiso.VER_TRABAJADOR);

                PersistenciaHelper.persistir(nuevoUser);
            } else{
                parametros.put("error","La contraseña no cumple con los requisitos de seguridad. " +
                        "Chequee si su contraseña cumple con: " +
                        "Una mayuscula, una minuscula, un numero, y no esta en el top peores 1000 contraseñas.");
                return new ModelAndView(parametros,"status-nuevo-usuario.hbs");
            }
        } else{
            parametros.put("error","No se ingresaron todos los campos.");
            return new ModelAndView(parametros,"status-nuevo-usuario.hbs");
        }
        parametros.put("exito","Usuario creado con exito!");
        return new ModelAndView(parametros,"status-nuevo-usuario.hbs");
    }
}
