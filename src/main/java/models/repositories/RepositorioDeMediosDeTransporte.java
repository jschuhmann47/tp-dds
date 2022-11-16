package models.repositories;

import models.entities.transporte.MedioTransporte;
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

    public List<MedioTransporte> buscarTodosTransportesPrivados() {
        return this.dao.buscarTodos(this.condicionTransportePrivado());

    }

    private BusquedaCondicional condicionTransportePrivado() {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<MedioTransporte> localidadCriteriaQuery = criteriaBuilder.createQuery(MedioTransporte.class);

        Root<MedioTransporte> condicionRaiz = localidadCriteriaQuery.from(MedioTransporte.class);
//        Join<Localidad, Municipio> provinciaJoin = condicionRaiz.join("municipio",JoinType.INNER);

        Predicate condicion = criteriaBuilder.equal(condicionRaiz.get("tipoTransporte"),"transporte_privado");
        //this.getClass().getAnnotation(DiscriminatorValue.class).value()

        localidadCriteriaQuery.where(condicion);
        return new BusquedaCondicional(null, localidadCriteriaQuery);
    }
}
