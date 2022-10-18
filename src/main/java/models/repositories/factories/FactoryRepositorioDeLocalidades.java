package models.repositories.factories;

import models.entities.geoDDS.entidades.Localidad;
import models.repositories.RepositorioDeLocalidades;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeLocalidades {
    private static RepositorioDeLocalidades repo = null;

    public static RepositorioDeLocalidades get(){
        if(repo == null){
            repo = new RepositorioDeLocalidades(new DAOHibernate<>(Localidad.class));
        }
        return repo;
    }
}
