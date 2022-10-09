package models.helpers;

import models.entities.seguridad.cuentas.Permiso;
import models.entities.seguridad.cuentas.Rol;
import models.entities.seguridad.cuentas.TipoRecurso;
import spark.Request;

public class PermisoHelper {

    public static Boolean usuarioTienePermisos(Request request, Permiso ... permisos){
        return SessionHelper.usuarioLogueado(request).tienePermisos(permisos);
    }

    public static Boolean usuarioTieneRol(Request request, Rol rol){
        return SessionHelper.usuarioLogueado(request).tieneRol(rol);
    }

    public static Boolean usuarioTieneRecursoDeTipo(Request request, TipoRecurso tipoRecurso){
        return SessionHelper.usuarioLogueado(request).tieneRecurso(tipoRecurso);
    }

}
