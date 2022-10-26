package models.repositories;

import models.entities.organizaciones.contacto.Contacto;
import models.repositories.daos.DAO;

public class RepositorioDeContactos extends Repositorio<Contacto>{
    public RepositorioDeContactos(DAO<Contacto> dao) {
        super(dao);
    }
}
