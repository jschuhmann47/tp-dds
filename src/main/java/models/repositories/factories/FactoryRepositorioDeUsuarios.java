package models.repositories.factories;

import models.entities.seguridad.cuentas.Usuario;
import models.repositories.RepositorioDeUsuarios;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeUsuarios {
    private static RepositorioDeUsuarios repo = null;

    public static RepositorioDeUsuarios get(){
        if(repo == null){
            repo = new RepositorioDeUsuarios(new DAOHibernate<>(Usuario.class));
        }
        return repo;
    }
}
