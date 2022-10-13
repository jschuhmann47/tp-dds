package models.repositories;

import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Provincia;
import models.repositories.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioDeProvincias extends Repositorio<Provincia> {
    public RepositorioDeProvincias(DAO<Provincia> dao) {
        super(dao);
    }

    public Provincia buscarNombre(String nombreProvincia){
        return this.dao.buscar(this.condicionNombreProvincia(nombreProvincia));
    }

    private BusquedaCondicional condicionNombreProvincia(String nombreProvincia){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Provincia> provinciaCriteriaQuery = criteriaBuilder.createQuery(Provincia.class);

        Root<Provincia> condicionRaiz = provinciaCriteriaQuery.from(Provincia.class);

        Predicate condicion = criteriaBuilder.equal(condicionRaiz.get("nombre"),nombreProvincia);
        //TODO formatearlo mayus o minus?

        provinciaCriteriaQuery.where(condicion);
        return new BusquedaCondicional(null, provinciaCriteriaQuery);
    }
}
