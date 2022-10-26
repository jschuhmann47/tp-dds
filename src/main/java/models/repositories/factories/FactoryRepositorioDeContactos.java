package models.repositories.factories;

import models.entities.organizaciones.contacto.Contacto;
import models.repositories.RepositorioDeContactos;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeContactos {
    private static RepositorioDeContactos repo = null;

    public static RepositorioDeContactos get(){
        if(repo == null){
            repo = new RepositorioDeContactos(new DAOHibernate<>(Contacto.class));
        }
        return repo;
    }
}
