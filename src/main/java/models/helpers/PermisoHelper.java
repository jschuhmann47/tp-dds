package models.helpers;

import models.entities.seguridad.cuentas.Permiso;
import models.entities.seguridad.cuentas.Rol;
import spark.Request;
import spark.Response;
import spark.Spark;

public class PermisoHelper {

    public static boolean usuarioTienePermisos(Request request, Permiso permiso){
        //TODO
        return true;
    }

    public static boolean usuarioTieneRol(Request request, Rol rol){
        //TODO
        return true;
    }


    public static void chequearPermiso(Request request, Response response, Permiso permiso, String rutaARedirigir){
        if(!PermisoHelper.usuarioTienePermisos(request, permiso)){
            response.redirect(rutaARedirigir);
            Spark.halt();
        }
    }


}
