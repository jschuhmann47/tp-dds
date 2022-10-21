package models.repositories;

import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Provincia;
import models.repositories.daos.DAO;

import javax.persistence.criteria.*;
import java.util.List;

public class RepositorioDeMunicipios extends Repositorio<Municipio>{
    public RepositorioDeMunicipios(DAO<Municipio> dao) {
        super(dao);
    }

    public Municipio buscarNombre(String nombreMunicipio, String nombreProvincia){
        return this.dao.buscar(this.condicionNombreMunicipioYProvincia(nombreMunicipio,nombreProvincia));
    }

    public List<Municipio> buscarMunicipiosDeProvincia(Provincia provincia){
        return this.dao.buscarTodos(this.condicionEsDeProvincia(provincia));
    }

    private BusquedaCondicional condicionNombreMunicipioYProvincia(String nombreMunicipio, String nombreProvincia){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Municipio> municipioCriteriaQuery = criteriaBuilder.createQuery(Municipio.class);

        Root<Municipio> condicionRaiz = municipioCriteriaQuery.from(Municipio.class);
        Join<Municipio, Provincia> provinciaJoin = condicionRaiz.join("provincia",JoinType.INNER);

        Predicate condicion = criteriaBuilder.and(criteriaBuilder.equal(condicionRaiz.get("nombre"), nombreMunicipio.toUpperCase()),
                criteriaBuilder.equal(condicionRaiz.get("provincia").get("nombre"),nombreProvincia.toUpperCase()));

        municipioCriteriaQuery.where(condicion);
        return new BusquedaCondicional(null, municipioCriteriaQuery);
    }

    private BusquedaCondicional condicionEsDeProvincia(Provincia provincia){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Municipio> municipioCriteriaQuery = criteriaBuilder.createQuery(Municipio.class);

        Root<Municipio> condicionRaiz = municipioCriteriaQuery.from(Municipio.class);
//        Join<Municipio, Provincia> provinciaJoin = condicionRaiz.join("provincia",JoinType.INNER);

        Predicate condicion = criteriaBuilder.equal(condicionRaiz.get("provincia").get("id"),provincia.getId());

        municipioCriteriaQuery.where(condicion);
        return new BusquedaCondicional(null, municipioCriteriaQuery);
    }

}
