package server;

import models.controllers.LoginController;
import models.controllers.MenuController;
import models.controllers.OrganizacionController;
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
                Spark.get("",organizacionController::mostrar, Router.engine);
                Spark.get("/vinculaciones",organizacionController::mostrarVinculaciones, Router.engine);
                //Spark.get("/menu/:id/vinculaciones",organizacionController::mostrarVinculaciones, Router.engine);
            });

        });




        //queryParam el de nombre=Eze&...
        //routeParam la de :id
    }
}