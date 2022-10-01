package server;

import models.controllers.LoginController;
import models.controllers.OrganizacionController;
import models.controllers.WelcomeController;
import models.middleware.Auth;
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
        WelcomeController wc = new WelcomeController();
        OrganizacionController organizacionController = new OrganizacionController();
        LoginController loginController = new LoginController();
        Auth auth = new Auth();

        Spark.get("/", wc::inicio, Router.engine);
        //Spark.get("/login",(request, response) -> "Sos " + request.queryParams("nombre"));
        //Spark.before("/login", auth::verificarSesion);
        Spark.get("/login",loginController::inicio, Router.engine);

        Spark.post("/login",loginController::login);
        Spark.get("/menu/:id",organizacionController::mostrar, Router.engine);
    }
}