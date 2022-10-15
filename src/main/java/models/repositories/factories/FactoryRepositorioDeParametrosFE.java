package models.repositories.factories;

import models.entities.parametros.ParametroFE;
import models.repositories.RepositorioDeParametrosFE;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeParametrosFE {
    private static RepositorioDeParametrosFE repo = null;

//    static{
//        repo = null;
//    }

    public static RepositorioDeParametrosFE get(){
        if(repo == null){
            repo = new RepositorioDeParametrosFE(new DAOHibernate<>(ParametroFE.class));
        }
        return repo;
    }
}
