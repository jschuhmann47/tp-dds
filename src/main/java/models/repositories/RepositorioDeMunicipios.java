package models.repositories;

import models.entities.geoDDS.entidades.Municipio;
import models.repositories.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioDeMunicipios extends Repositorio<Municipio>{
    public RepositorioDeMunicipios(DAO<Municipio> dao) {
        super(dao);
    }

    public Municipio buscarNombre(String nombreMunicipio, String nombreProvincia){
        return this.dao.buscar(this.condicionNombreMunicipioYProvincia(nombreMunicipio,nombreProvincia));
    }

    private BusquedaCondicional condicionNombreMunicipioYProvincia(String nombreMunicipio, String nombreProvincia){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Municipio> municipioCriteriaQuery = criteriaBuilder.createQuery(Municipio.class);

        Root<Municipio> condicionRaiz = municipioCriteriaQuery.from(Municipio.class);

        Predicate condicion = criteriaBuilder.and(criteriaBuilder.equal(condicionRaiz.get("nombre"), nombreMunicipio),
                criteriaBuilder.equal(condicionRaiz.get("provincia.nombre"),nombreProvincia));
                //TODO formatearlo mayus o minus?

        municipioCriteriaQuery.where(condicion);
        return new BusquedaCondicional(null, municipioCriteriaQuery);
    }
}
