package models.repositories;

import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Provincia;
import models.repositories.daos.DAO;

import javax.persistence.criteria.*;

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
        Join<Municipio, Provincia> provinciaJoin = condicionRaiz.join("provincia",JoinType.INNER);

        Predicate condicion = criteriaBuilder.and(criteriaBuilder.equal(condicionRaiz.get("nombre"), nombreMunicipio.toUpperCase()),
                criteriaBuilder.equal(condicionRaiz.get("provincia").get("nombre"),nombreProvincia.toUpperCase()));

        municipioCriteriaQuery.where(condicion);
        return new BusquedaCondicional(null, municipioCriteriaQuery);
    }
}
