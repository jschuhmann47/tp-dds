package server;

import models.controllers.*;
import models.entities.seguridad.cuentas.Permiso;
import models.entities.seguridad.cuentas.Rol;
import models.entities.seguridad.cuentas.TipoRecurso;
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
        ErrorHandlerController errorController = new ErrorHandlerController();
        AgenteSectorialController agenteController = new AgenteSectorialController();

        Spark.get("/",((request, response) -> {
            response.redirect("/login");
            return response;
        }));

        Spark.path("/login", () -> {
            Spark.get("",loginController::inicio, Router.engine);
            Spark.post("",loginController::login);

        });

        Spark.path("/menu",() -> {
            Spark.before("", AuthMiddleware::verificarSesion);
            Spark.before("/*", AuthMiddleware::verificarSesion);
            Spark.get("/logout",loginController::logout); //post o get?
            Spark.get("",menuController::inicio,Router.engine);

            Spark.path("/organizacion", () -> {
                Spark.before("", (request, response) -> {
                   if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_ORGANIZACION)
                           || !PermisoHelper.usuarioTieneRecursoDeTipo(request, TipoRecurso.ORGANIZACION)){ //todo o el id de ese tipo es null??
                       response.redirect("/prohibido");
                       Spark.halt("Recurso prohibido");
                   }
                });
                Spark.before("/*", (request, response) -> {
                    if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_ORGANIZACION)
                            || !PermisoHelper.usuarioTieneRecursoDeTipo(request, TipoRecurso.ORGANIZACION)){
                        response.redirect("/prohibido");
                        Spark.halt("Recurso prohibido");
                    }
                });
                Spark.get("",organizacionController::mostrar, Router.engine);

                Spark.path("/vinculaciones", () -> {
                    Spark.get("",organizacionController::mostrarVinculaciones, Router.engine);
                    Spark.post("/aceptar",organizacionController::aceptarVinculacion); //todo ruta ok?
                    Spark.post("/rechazar",organizacionController::rechazarVinculacion);
                });


                Spark.path("/mediciones", () -> {
                    Spark.get("",organizacionController::mostrarMedicion, Router.engine);
                    Spark.get("/agregar",organizacionController::mostrarNuevaMedicion, Router.engine);
                    Spark.post("/agregar",organizacionController::registrarNuevaMedicion);
                });
                Spark.path("/reportes", () -> {
                    Spark.get("",organizacionController::mostrarReportes, Router.engine);
                    Spark.get("/nuevo",organizacionController::mostrarNuevoReporte, Router.engine);
                });

                Spark.path("/calculadora", () -> {
                    Spark.get("",organizacionController::mostrarHC, Router.engine);
                    Spark.post("",organizacionController::calcularHC, Router.engine);
                });

                Spark.get("/recomendaciones",organizacionController::mostrarRecomendaciones, Router.engine);
            });

            Spark.path("/trabajador", () -> {
                Spark.before("", (request, response) -> {
                    if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_TRABAJADOR)
                        || !PermisoHelper.usuarioTieneRecursoDeTipo(request, TipoRecurso.TRABAJADOR)){
                        response.redirect("/prohibido");
                        Spark.halt("Recurso prohibido");
                    }
                });
                Spark.before("/*", (request, response) -> {
                    if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_TRABAJADOR)
                        || !PermisoHelper.usuarioTieneRecursoDeTipo(request, TipoRecurso.TRABAJADOR)){
                        response.redirect("/prohibido");
                        Spark.halt("Recurso prohibido");
                    }
                });

                Spark.get("",trabajadorController::mostrar, Router.engine);

                Spark.path("/calculadora", () -> {
                    Spark.get("",trabajadorController::mostrarCalculadora, Router.engine);
                    Spark.post("",trabajadorController::calcularHC, Router.engine);
                });

                Spark.path("/reportes", () -> {
                    Spark.get("",trabajadorController::mostrarReportes, Router.engine);
                });

                Spark.path("/vinculacion", () -> {
                    Spark.get("",trabajadorController::mostrarVinculaciones, Router.engine);
                    Spark.get("/nuevo",trabajadorController::mostrarNuevaVinculacion, Router.engine);
                    Spark.post("/nuevo",trabajadorController::nuevaSolicitud);
                });

                Spark.path("/trayectos", () -> {
                    Spark.get("",trabajadorController::mostrarTrayectos, Router.engine);
                    Spark.get("/nuevo",trabajadorController::mostrarNuevoTrayecto, Router.engine);
                    Spark.post("/nuevo",trabajadorController::registrarNuevoTrayecto);
                });

                Spark.get("/recomendaciones",trabajadorController::mostrarRecomendaciones, Router.engine);
            });

            Spark.path("/agente", () -> {
                Spark.before("", (request, response) -> {
                    if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_AGENTESECTORIAL)){
                        response.redirect("/prohibido");
                        Spark.halt("Recurso prohibido");
                    }
                });
                Spark.before("/*", (request, response) -> {
                    if(!PermisoHelper.usuarioTienePermisos(request, Permiso.VER_AGENTESECTORIAL)){
                        response.redirect("/prohibido");
                        Spark.halt("Recurso prohibido");
                    }
                });
                Spark.get("",agenteController::mostrar,Router.engine);

                Spark.path("/reportes", () -> {
                    Spark.get("",agenteController::mostrarReportes,Router.engine);
                    //TODO la parte de los reportes
                });

                Spark.get("/recomendaciones",agenteController::mostrarRecomendaciones,Router.engine);

            });

            Spark.path("/administrador", () -> {
                Spark.before("", (request, response) -> {
                    if(!PermisoHelper.usuarioTieneRol(request, Rol.ADMINISTRADOR)){
                        response.redirect("/prohibido");
                        Spark.halt("Recurso prohibido");
                    }
                });
                Spark.before("/*", (request, response) -> {
                    if(!PermisoHelper.usuarioTieneRol(request, Rol.ADMINISTRADOR)){
                        response.redirect("/prohibido");
                        Spark.halt("Recurso prohibido");
                    }
                });
                Spark.get("",administradorController::mostrar, Router.engine);
                Spark.path("/config", () -> {
                    Spark.get("",administradorController::mostrarConfiguracionActualFE, Router.engine);
                    Spark.post("",administradorController::editarFE);
                });

            });
        });
        Spark.get("/prohibido",errorController::prohibido,Router.engine);
        Spark.get("/error",errorController::error,Router.engine);











        //FIN
        Spark.get("/*",errorController::error,Router.engine);
    }
}