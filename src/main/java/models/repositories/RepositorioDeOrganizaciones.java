package models.repositories;

import models.daos.DAO;
import models.entities.organizaciones.entidades.Organizacion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioDeOrganizaciones extends Repositorio<Organizacion> {
    public RepositorioDeOrganizaciones(DAO<Organizacion> dao) {
        super(dao);
    }

    public Organizacion buscarOrganizacion(String username, String password){
        return this.dao.buscar(this.condicionUsuarioYContrasenia(username,password));
    }
    public Organizacion buscarOrganizacionPorId(String id){
        return this.dao.buscar(this.condicionId(id));
    }
    public Boolean existe(String username, String password){
        return buscarOrganizacion(username,password) != null;
    }
    public Boolean existePorId(String id){
        return buscarOrganizacionPorId(id) != null;
    }

    private BusquedaCondicional condicionUsuarioYContrasenia(String username, String password) {
        return null;
    }

    private BusquedaCondicional condicionId(String id){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Organizacion> usuarioQuery = criteriaBuilder.createQuery(Organizacion.class);

        Root<Organizacion> condicionRaiz = usuarioQuery.from(Organizacion.class);

        Predicate condicionId = criteriaBuilder.equal(condicionRaiz.get("id"), id);

        usuarioQuery.where(condicionId);

        return new BusquedaCondicional(null, usuarioQuery);
    }
}
