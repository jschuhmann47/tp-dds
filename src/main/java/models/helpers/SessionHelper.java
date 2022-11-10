package models.helpers;

import db.EntityManagerHelper;
import models.entities.seguridad.cuentas.Usuario;
import spark.Request;

import java.util.Objects;

public class SessionHelper {
    public static Usuario usuarioLogueado(Request request) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Usuario.class, request.session().attribute("id"));
    }

    public static Boolean atributosNoSonNull(Request request,String ... strings){
        for (String s : strings){
            if (request.queryParams(s) == null || Objects.equals(request.queryParams(s), "")){
                return false;
            }
        }
        return true;
    }
}
