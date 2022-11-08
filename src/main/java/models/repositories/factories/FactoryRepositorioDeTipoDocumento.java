package models.repositories.factories;

import models.entities.organizaciones.entidades.TipoDoc;
import models.repositories.RepositorioDeTipoDocumento;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeTipoDocumento {
    private static RepositorioDeTipoDocumento repo = null;

    public static RepositorioDeTipoDocumento get(){
        if(repo == null){
            repo = new RepositorioDeTipoDocumento(new DAOHibernate<>(TipoDoc.class));
        }
        return repo;
    }
}
