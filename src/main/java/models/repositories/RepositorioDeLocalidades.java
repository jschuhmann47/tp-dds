package models.repositories;

import models.entities.geoDDS.entidades.Localidad;
import models.repositories.daos.DAO;

public class RepositorioDeLocalidades extends Repositorio<Localidad>{
    public RepositorioDeLocalidades(DAO<Localidad> dao) {
        super(dao);
    }
}
