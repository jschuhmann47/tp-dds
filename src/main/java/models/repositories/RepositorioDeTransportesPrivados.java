package models.repositories;

import models.entities.transporte.MedioTransporte;
import models.entities.transporte.privado.TransportePrivado;
import models.repositories.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class RepositorioDeTransportesPrivados extends Repositorio<TransportePrivado>{
    public RepositorioDeTransportesPrivados(DAO<TransportePrivado> dao) {
        super(dao);
    }

    public List<TransportePrivado> buscarTodosTransportesPrivados() {
        return this.dao.buscarTodos(this.condicionTransportePrivado());
    }

    public TransportePrivado buscarTransportePrivado(Integer id) {
        return this.dao.buscar(this.condicionTransportePrivadoPorId(id));
    }

    private BusquedaCondicional condicionTransportePrivado() {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<MedioTransporte> localidadCriteriaQuery = criteriaBuilder.createQuery(MedioTransporte.class);

        Root<MedioTransporte> condicionRaiz = localidadCriteriaQuery.from(MedioTransporte.class);

        Predicate condicion = criteriaBuilder.equal(condicionRaiz.get("tipoTransporte"),"transporte_privado");

        localidadCriteriaQuery.where(condicion);
        return new BusquedaCondicional(null, localidadCriteriaQuery);
    }

    private BusquedaCondicional condicionTransportePrivadoPorId(Integer id) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<MedioTransporte> localidadCriteriaQuery = criteriaBuilder.createQuery(MedioTransporte.class);

        Root<MedioTransporte> condicionRaiz = localidadCriteriaQuery.from(MedioTransporte.class);


        Predicate condicion = criteriaBuilder
                .and(criteriaBuilder.equal(condicionRaiz.get("tipoTransporte"),"transporte_privado"),
                        criteriaBuilder.equal(condicionRaiz.get("tipoTransporte").get("id"),id));

        localidadCriteriaQuery.where(condicion);
        return new BusquedaCondicional(null, localidadCriteriaQuery);
    }
}
