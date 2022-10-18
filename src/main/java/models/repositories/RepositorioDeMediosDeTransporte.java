package models.repositories;

import models.entities.transporte.MedioTransporte;
import models.repositories.daos.DAO;

public class RepositorioDeMediosDeTransporte extends Repositorio<MedioTransporte> {
    public RepositorioDeMediosDeTransporte(DAO<MedioTransporte> dao) {
        super(dao);
    }
}
