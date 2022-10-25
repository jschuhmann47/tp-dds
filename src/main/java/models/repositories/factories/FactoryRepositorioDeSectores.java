package models.repositories.factories;

import models.entities.organizaciones.entidades.Sector;
import models.repositories.RepositorioDeSectores;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeSectores {

    private static RepositorioDeSectores repo = null;

    public static RepositorioDeSectores get(){
        if(repo == null){
            repo = new RepositorioDeSectores(new DAOHibernate<>(Sector.class));
        }
        return repo;
    }
}
