package models.repositories.factories;

import models.entities.trayectos.Tramo;
import models.repositories.RepositorioDeTramos;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeTramos {
    private static RepositorioDeTramos repo = null;

    public static RepositorioDeTramos get(){
        if(repo == null){
            repo = new RepositorioDeTramos(new DAOHibernate<>(Tramo.class));
        }
        return repo;
    }
}
