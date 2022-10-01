package models.repositories.factories;

import models.repositories.daos.DAOHibernate;
import models.entities.organizaciones.entidades.Organizacion;
import models.repositories.RepositorioDeOrganizaciones;

public class FactoryRepositorioDeOrganizaciones {
    private static RepositorioDeOrganizaciones repo = null;

//    static{
//        repo = null;
//    }

    public static RepositorioDeOrganizaciones get(){
        if(repo == null){
            repo = new RepositorioDeOrganizaciones(new DAOHibernate<>(Organizacion.class));
        }
        return repo;
    }
}
