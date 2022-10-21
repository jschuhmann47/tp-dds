package models.repositories;

import models.entities.geoDDS.entidades.Localidad;
import models.entities.geoDDS.entidades.Municipio;
import models.repositories.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class RepositorioDeLocalidades extends Repositorio<Localidad>{
    public RepositorioDeLocalidades(DAO<Localidad> dao) {
        super(dao);
    }

    public List<Localidad> buscarLocalidadesDeMunicipio(Municipio municipio) {
        return this.dao.buscarTodos(this.condicionEsDeMunicipio(municipio));
    }

    private BusquedaCondicional condicionEsDeMunicipio(Municipio municipio){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Localidad> localidadCriteriaQuery = criteriaBuilder.createQuery(Localidad.class);

        Root<Localidad> condicionRaiz = localidadCriteriaQuery.from(Localidad.class);
//        Join<Localidad, Municipio> provinciaJoin = condicionRaiz.join("municipio",JoinType.INNER);

        Predicate condicion = criteriaBuilder.equal(condicionRaiz.get("municipio").get("id"),municipio.getId());

        localidadCriteriaQuery.where(condicion);
        return new BusquedaCondicional(null, localidadCriteriaQuery);
    }
}
