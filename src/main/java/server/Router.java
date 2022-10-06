package server;

import models.controllers.*;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.seguridad.cuentas.Permiso;
import models.entities.seguridad.cuentas.Rol;
import models.helpers.PermisoHelper;
import models.middlewares.AuthMiddleware;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("isTrue", BooleanHelper.isTrue)
                .build();
    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure(){

        OrganizacionController organizacionController = new OrganizacionController();
        LoginController loginController = new LoginController();
        MenuController menuController = new MenuController();
        TrabajadorController trabajadorController = new TrabajadorController();
        AdministradorController administradorController = new AdministradorController();


        Spark.path("/login", () -> {
            Spark.get("",loginController::inicio, Router.engine);
            Spark.post("",loginController::login);

        });
        //Spark.get("/login",(request, response) -> "Sos " + request.queryParams("nombre"));
        //Spark.before("/login", auth::verificarSesion);

        Spark.path("/menu",() -> {
            Spark.before("", AuthMiddleware::verificarSesion);
            Spark.before("/*", AuthMiddleware::verificarSesion);
            Spark.post("/logout",loginController::logout); //post o get?
            Spark.get("",menuController::inicio,Router.engine);

            Spark.path("/organizacion", () -> {
                Spark.before("", (request, response) -> {
                   if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_ORGANIZACION)){
                       response.redirect("/prohibido");
                       Spark.halt();
                   }
                });
                Spark.before("/*", (request, response) -> {
                    if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_ORGANIZACION)){
                        response.redirect("/prohibido");
                        Spark.halt();
                    }
                });
                Spark.get("",organizacionController::mostrar, Router.engine);
                Spark.get("/vinculaciones",organizacionController::mostrarVinculaciones, Router.engine);
                //Spark.get("/menu/:id/vinculaciones",organizacionController::mostrarVinculaciones, Router.engine);
            });

            Spark.path("/trabajador", () -> {
                Spark.before("", (request, response) -> {
                    if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_TRABAJADOR)){
                        response.redirect("/prohibido");
                        Spark.halt();
                    }
                });
                Spark.before("/*", (request, response) -> {
                    if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_TRABAJADOR)){
                        response.redirect("/prohibido");
                        Spark.halt();
                    }
                });

                Spark.get("",trabajadorController::mostrar, Router.engine);
            });

            Spark.path("/administrador", () -> {
                Spark.before("", (request, response) -> {
                    if(!PermisoHelper.usuarioTieneRol(request, Rol.ADMINISTRADOR)){
                        response.redirect("/prohibido");
                        Spark.halt();
                    }
                });
                Spark.before("/*", (request, response) -> {
                    if(!PermisoHelper.usuarioTieneRol(request, Rol.ADMINISTRADOR)){
                        response.redirect("/prohibido");
                        Spark.halt();
                    }
                });
                Spark.get("",administradorController::mostrar, Router.engine);
            });
        });




        //queryParam el de nombre=Eze&...
        //routeParam la de :id
    }
}