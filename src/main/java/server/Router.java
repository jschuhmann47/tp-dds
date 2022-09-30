package server;

import models.controllers.LoginController;
import models.controllers.OrganizacionController;
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

        //Spark.get("/menu", organizacionController::mostrar, Router.engine);
        //Spark.get("/login",(request, response) -> "Sos " + request.queryParams("nombre"));
        Spark.get("/",loginController::inicio, Router.engine);
        Spark.post("/",loginController::login);
        Spark.get("/menu/:id",organizacionController::mostrar, Router.engine);
    }
}