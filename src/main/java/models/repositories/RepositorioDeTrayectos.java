package models.repositories;

import models.entities.trayectos.Trayecto;
import models.repositories.daos.DAO;

public class RepositorioDeTrayectos extends Repositorio<Trayecto>{
    public RepositorioDeTrayectos(DAO<Trayecto> dao) {
        super(dao);
    }
}
