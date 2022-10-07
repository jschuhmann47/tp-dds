package models.repositories;

import models.repositories.daos.DAO;
import models.entities.organizaciones.entidades.Organizacion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioDeOrganizaciones extends Repositorio<Organizacion> {
    public RepositorioDeOrganizaciones(DAO<Organizacion> dao) {
        super(dao);
    }

//    public Organizacion buscar(String id){
//        return this.dao.buscar(this.condicionId(id));
//    }
//    public Boolean existe(String id){
//        return this.buscar(id) != null;
//    }
//
//    private BusquedaCondicional condicionId(String id){
//        CriteriaBuilder criteriaBuilder = criteriaBuilder();
//        CriteriaQuery<Organizacion> usuarioQuery = criteriaBuilder.createQuery(Organizacion.class);
//
//        Root<Organizacion> condicionRaiz = usuarioQuery.from(Organizacion.class);
//
//        Predicate condicionId = criteriaBuilder.equal(condicionRaiz.get("id"), id);
//
//        usuarioQuery.where(condicionId);
//
//        return new BusquedaCondicional(null, usuarioQuery);
//    }
}
