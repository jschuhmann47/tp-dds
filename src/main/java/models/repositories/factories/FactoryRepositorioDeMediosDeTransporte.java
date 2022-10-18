package models.repositories.factories;

import models.entities.transporte.MedioTransporte;
import models.repositories.RepositorioDeMediosDeTransporte;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeMediosDeTransporte {
    private static RepositorioDeMediosDeTransporte repo = null;

    public static RepositorioDeMediosDeTransporte get(){
        if(repo == null){
            repo = new RepositorioDeMediosDeTransporte(new DAOHibernate<>(MedioTransporte.class));
        }
        return repo;
    }
}
