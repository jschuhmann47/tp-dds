package models.repositories;

import models.entities.trayectos.Tramo;
import models.repositories.daos.DAO;

public class RepositorioDeTramos extends Repositorio<Tramo>{
    public RepositorioDeTramos(DAO<Tramo> dao) {
        super(dao);
    }
}
