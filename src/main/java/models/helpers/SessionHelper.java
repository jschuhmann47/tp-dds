package models.helpers;

import db.EntityManagerHelper;
import models.entities.seguridad.cuentas.Usuario;
import spark.Request;

public class SessionHelper {
    public static Usuario usuarioLogueado(Request request) {
        return EntityManagerHelper
                .getEntityManager()
                .find(Usuario.class, request.session().attribute("id"));
    }
}
