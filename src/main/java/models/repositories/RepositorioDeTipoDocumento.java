package models.repositories;

import models.entities.organizaciones.entidades.TipoDoc;
import models.repositories.daos.DAO;

public class RepositorioDeTipoDocumento extends Repositorio<TipoDoc>{
    public RepositorioDeTipoDocumento(DAO<TipoDoc> dao) {
        super(dao);
    }
}
