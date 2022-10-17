package models.repositories;

import models.entities.parametros.ParametroFE;
import models.helpers.StringHelper;
import models.repositories.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioDeParametrosFE extends Repositorio<ParametroFE>{
    public RepositorioDeParametrosFE(DAO<ParametroFE> dao) {
        super(dao);
    }

    public ParametroFE buscarNombre(String nombre){
        return this.dao.buscar(this.condicionNombre(nombre));
    }

    private BusquedaCondicional condicionNombre(String nombre) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<ParametroFE> parametroFECriteriaQuery = criteriaBuilder.createQuery(ParametroFE.class);

        Root<ParametroFE> condicionRaiz = parametroFECriteriaQuery.from(ParametroFE.class);

        Predicate condicionNombre = criteriaBuilder.equal(condicionRaiz.get("nombre"), StringHelper.toEnumFormat(nombre));

        parametroFECriteriaQuery.where(condicionNombre);

        return new BusquedaCondicional(null, parametroFECriteriaQuery);
    }


}
