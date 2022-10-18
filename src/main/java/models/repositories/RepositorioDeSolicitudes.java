package models.repositories;

import models.entities.organizaciones.solicitudes.Solicitud;
import models.repositories.daos.DAO;

public class RepositorioDeSolicitudes extends Repositorio<Solicitud>{
    public RepositorioDeSolicitudes(DAO<Solicitud> dao) {
        super(dao);
    }
}
