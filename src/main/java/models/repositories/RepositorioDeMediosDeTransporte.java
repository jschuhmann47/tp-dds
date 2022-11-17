package models.repositories;

import models.entities.transporte.MedioTransporte;
import models.entities.transporte.privado.TransportePrivado;
import models.repositories.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class RepositorioDeMediosDeTransporte extends Repositorio<MedioTransporte> {
    public RepositorioDeMediosDeTransporte(DAO<MedioTransporte> dao) {
        super(dao);
    }
}
