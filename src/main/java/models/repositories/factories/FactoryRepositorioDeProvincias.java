package models.repositories.factories;

import models.entities.geoDDS.entidades.Provincia;
import models.repositories.RepositorioDeProvincias;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeProvincias {
    private static RepositorioDeProvincias repo = null;

//    static{
//        repo = null;
//    }

    public static RepositorioDeProvincias get(){
        if(repo == null){
            repo = new RepositorioDeProvincias(new DAOHibernate<>(Provincia.class));
        }
        return repo;
    }
}
