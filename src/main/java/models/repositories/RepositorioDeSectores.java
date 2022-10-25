package models.repositories;

import models.entities.organizaciones.entidades.Sector;
import models.repositories.daos.DAO;

public class RepositorioDeSectores extends Repositorio<Sector> {
    public RepositorioDeSectores(DAO<Sector> dao) {
        super(dao);
    }
}
