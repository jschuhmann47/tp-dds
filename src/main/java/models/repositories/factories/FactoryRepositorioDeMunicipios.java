package models.repositories.factories;

import models.entities.geoDDS.entidades.Municipio;
import models.repositories.RepositorioDeMunicipios;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeMunicipios {

    private static RepositorioDeMunicipios repo = null;

//    static{
//        repo = null;
//    }

    public static RepositorioDeMunicipios get(){
        if(repo == null){
            repo = new RepositorioDeMunicipios(new DAOHibernate<>(Municipio.class));
        }
        return repo;
    }
}
