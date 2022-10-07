package models.repositories;

import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Trabajador;
import models.repositories.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioDeTrabajadores extends Repositorio<Trabajador> {
    public RepositorioDeTrabajadores(DAO<Trabajador> dao) {
        super(dao);
    }

    public Trabajador buscar(String id) {
        return this.dao.buscar(this.condicionId(id));
    }

    public Boolean existe(String id) {
        return this.buscar(id) != null;
    }

    private BusquedaCondicional condicionId(String id) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Trabajador> usuarioQuery = criteriaBuilder.createQuery(Trabajador.class);

        Root<Trabajador> condicionRaiz = usuarioQuery.from(Trabajador.class);

        Predicate condicionId = criteriaBuilder.equal(condicionRaiz.get("id"), id);

        usuarioQuery.where(condicionId);

        return new BusquedaCondicional(null, usuarioQuery);
    }
}
