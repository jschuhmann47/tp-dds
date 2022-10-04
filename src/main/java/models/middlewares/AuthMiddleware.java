package models.middlewares;

import spark.Request;
import spark.Response;

import static spark.Spark.halt;

public class AuthMiddleware {
    public Response verificarSesion(Request request, Response response){
        if(!request.session().isNew() || request.session().attribute("id") == null){
            response.redirect("/login");
        }
        return response;
    }
}
