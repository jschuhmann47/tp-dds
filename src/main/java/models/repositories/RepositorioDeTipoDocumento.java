package models.repositories;

import models.entities.organizaciones.entidades.TipoDeDocumento;
import models.repositories.daos.DAO;

public class RepositorioDeTipoDocumento extends Repositorio<TipoDeDocumento>{
    public RepositorioDeTipoDocumento(DAO<TipoDeDocumento> dao) {
        super(dao);
    }
}
