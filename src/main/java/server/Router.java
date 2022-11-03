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
        ReporteController reporteController = new ReporteController();

//        Spark.get("",((request, response) -> {
//            Spark.before("", AuthMiddleware::verificarSesion);
//            Spark.before("/*", AuthMiddleware::verificarSesion);
//            return response;
//        }));


        Spark.path("/login", () -> {
            Spark.get("",loginController::inicio, Router.engine);
            Spark.post("",loginController::login);

        });
        Spark.get("/logout",loginController::logout);


        //todo botones de eliminar ej trayecto
        Spark.path("",() -> {
            Spark.before("/", AuthMiddleware::verificarSesion);
            Spark.get("",menuController::inicio,Router.engine);
            Spark.path("/organizacion", () -> {
                Spark.before("", AuthMiddleware::verificarSesion);
                Spark.before("/*", AuthMiddleware::verificarSesion);
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
                    Spark.post("/aceptar",organizacionController::aceptarVinculacion);
                    Spark.post("/rechazar",organizacionController::rechazarVinculacion);
                });


                Spark.path("/mediciones", () -> { //excel
                    Spark.get("",organizacionController::mostrarMedicion, Router.engine);
                    Spark.get("/agregar",organizacionController::mostrarNuevaMedicion, Router.engine);
                    Spark.post("/agregar",organizacionController::registrarNuevaMedicion);
                });

                //quitar boton de reportes

                Spark.path("/calculadora", () -> {
                    Spark.get("",organizacionController::mostrarHC, Router.engine);
                    Spark.post("",organizacionController::calcularHC, Router.engine);
                });

                Spark.path("/contactos", () -> {
                    Spark.get("",organizacionController::mostrarContactos, Router.engine);
                    Spark.get("/nuevo",organizacionController::mostrarNuevoContacto, Router.engine);
                    Spark.post("/nuevo",organizacionController::registrarNuevoContacto);
                    Spark.get("/editar",organizacionController::mostrarEditarContacto, Router.engine);
                    Spark.post("/editar",organizacionController::editarContacto);
                    Spark.delete("",organizacionController::eliminarContacto);
                });

                Spark.get("/recomendaciones",organizacionController::mostrarRecomendaciones, Router.engine);
            });

            Spark.path("/trabajador", () -> {
                Spark.before("", AuthMiddleware::verificarSesion);
                Spark.before("/*", AuthMiddleware::verificarSesion);
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

                //quitar boton de reportes

                Spark.path("/vinculacion", () -> {
                    Spark.get("",trabajadorController::mostrarVinculaciones, Router.engine);
                    Spark.get("/nuevo",trabajadorController::mostrarNuevaVinculacion, Router.engine);
                    Spark.post("/nuevo",trabajadorController::nuevaVinculacion);
                });

                Spark.path("/trayectos", () -> {
                    Spark.get("",trabajadorController::mostrarTrayectos, Router.engine);
                    Spark.get("/nuevo",trabajadorController::mostrarNuevoTrayecto, Router.engine);
                    Spark.post("/nuevo",trabajadorController::registrarNuevoTrayecto);
                });

                Spark.path("/trayecto", () -> { //aca entra habiendo elegido un trayecto de la lista de trayectos
                    Spark.get("/tramos", trabajadorController::mostrarTramosDelTrayecto, Router.engine);
                    Spark.path("/tramos", () -> {
                        Spark.get("/editar",trabajadorController::mostrarEditarTramo, Router.engine);
                        Spark.post("/editar",trabajadorController::editarTramo);
                        Spark.get("/nuevo",trabajadorController::mostrarNuevoTramo, Router.engine);
                        Spark.post("/nuevo",trabajadorController::registrarNuevoTramo);
                        Spark.delete("",trabajadorController::eliminarTramo);
                    });
                    Spark.delete("",trabajadorController::eliminarTrayecto);
                });

                Spark.get("/recomendaciones",trabajadorController::mostrarRecomendaciones, Router.engine);
            });

            Spark.path("/agente", () -> {
                Spark.before("", AuthMiddleware::verificarSesion);
                Spark.before("/*", AuthMiddleware::verificarSesion);
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
                    Spark.get("",reporteController::mostrarReportes, Router.engine);

                    Spark.get("/composicionHCOrganizacion",reporteController::mostrarComposicionHCOrganizacion, Router.engine);
                    Spark.post("/composicionHCOrganizacion",reporteController::composicionHCOrganizacion, Router.engine);

                    Spark.get("/composicionHCTerritorio",reporteController::mostrarComposicionHCMunicipio, Router.engine);
                    Spark.post("/composicionHCTerritorio",reporteController::composicionHCMunicipio, Router.engine);

                    Spark.get("/composicionHCPais",reporteController::composicionHCPais, Router.engine);

                    Spark.get("/composicionHCTerritorio",reporteController::mostrarComposicionHCMunicipio, Router.engine);
                    Spark.post("/composicionHCTerritorio",reporteController::composicionHCMunicipio, Router.engine);

                    Spark.get("/evolucionHCOrganizacion",reporteController::mostrarEvolucionHCOrganizacion, Router.engine);
                    Spark.post("/evolucionHCOrganizacion",reporteController::evolucionHCOrganizacion, Router.engine);

                    Spark.get("/evolucionHCTerritorio",reporteController::mostrarEvolucionHCMunicipio, Router.engine);
                    Spark.post("/evolucionHCTerritorio",reporteController::evolucionHCMunicipio, Router.engine);

                    Spark.get("/HCTipoOrganizacion",reporteController::mostrarHCPorClasificacionOrganizacion, Router.engine);
                    Spark.post("/HCTipoOrganizacion",reporteController::HCPorClasificacionOrganizacion, Router.engine);

                    Spark.get("/HCTotalTerritorio",reporteController::mostrarHCPorMunicipio, Router.engine);
                    Spark.post("/HCTotalTerritorio",reporteController::HCPorMunicipio, Router.engine);

                    Spark.get("/HCTotalTerritorio",reporteController::mostrarHCPorMunicipio, Router.engine);
                    Spark.post("/HCTotalTerritorio",reporteController::HCPorMunicipio, Router.engine);


                });

                Spark.get("/recomendaciones",agenteController::mostrarRecomendaciones,Router.engine);

            });

            Spark.path("/administrador", () -> {
                Spark.before("", AuthMiddleware::verificarSesion);
                Spark.before("/*", AuthMiddleware::verificarSesion);
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

                Spark.path("/organizaciones", () -> {
                    Spark.get("",administradorController::mostrarOrganizaciones);
                    Spark.get("/nueva",administradorController::mostrarNuevaOrganizacion);
                    Spark.post("/nueva",administradorController::crearNuevaOrganizacion);
                    Spark.delete("",administradorController::eliminarOrganizacion);

                    //aca organizacion en vez de organizaciones
                    Spark.get("/sectores",administradorController::mostrarSectores); //tres botones
                    Spark.delete("/sectores",administradorController::eliminarSector);
                    Spark.get("/nuevoSector",administradorController::mostrarNuevoSector);
                    Spark.post("/nuevoSector",administradorController::crearNuevoSector);


                });


                Spark.path("/transportes", () -> {
                    Spark.get("",administradorController::mostrarTransportes, Router.engine);
                    Spark.get("/nuevo",administradorController::mostrarNuevoTransporte, Router.engine);
                    Spark.post("/nuevo",administradorController::crearNuevoTransporte);

                    //en transporte solo
                    Spark.get("/paradas",administradorController::mostrarParadas, Router.engine);
                    Spark.get("/nueva",administradorController::mostrarNuevaParada, Router.engine);
                    Spark.post("/nueva",administradorController::crearNuevaParada);

                });


            });
        });


        Spark.get("/prohibido",errorController::prohibido,Router.engine);
        Spark.get("/error",errorController::error,Router.engine);











        //FIN
        Spark.get("/*",errorController::error,Router.engine);
    }
}