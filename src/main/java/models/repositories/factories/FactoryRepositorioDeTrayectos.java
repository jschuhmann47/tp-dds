package models.repositories.factories;

import models.entities.trayectos.Trayecto;
import models.repositories.RepositorioDeTrayectos;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeTrayectos {
    private static RepositorioDeTrayectos repo = null;

    public static RepositorioDeTrayectos get(){
        if(repo == null){
            repo = new RepositorioDeTrayectos(new DAOHibernate<>(Trayecto.class));
        }
        return repo;
    }
}
