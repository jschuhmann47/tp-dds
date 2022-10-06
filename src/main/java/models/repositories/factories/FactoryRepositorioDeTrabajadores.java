package models.repositories.factories;

import models.entities.organizaciones.entidades.Trabajador;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.RepositorioDeTrabajadores;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeTrabajadores {

    private static RepositorioDeTrabajadores repo = null;

//    static{
//        repo = null;
//    }

    public static RepositorioDeTrabajadores get(){
        if(repo == null){
            repo = new RepositorioDeTrabajadores(new DAOHibernate<>(Trabajador.class));
        }
        return repo;
    }
}
