package models.repositories.factories;

import models.entities.organizaciones.solicitudes.Solicitud;
import models.repositories.RepositorioDeSolicitudes;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeSolicitudes {

    private static RepositorioDeSolicitudes repo = null;

    public static RepositorioDeSolicitudes get(){
        if(repo == null){
            repo = new RepositorioDeSolicitudes(new DAOHibernate<>(Solicitud.class));
        }
        return repo;
    }
}
