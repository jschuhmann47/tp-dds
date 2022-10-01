package models.middleware;

import spark.Request;
import spark.Response;

public class Auth {
    public Response verificarSesion(Request request, Response response){
        if(!request.session().isNew()){
            response.redirect("/menu/" + request.session().attribute("id"));
        }
        return response;
    }
}
