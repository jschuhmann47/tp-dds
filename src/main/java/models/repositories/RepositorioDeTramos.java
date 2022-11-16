package models.repositories;

import db.EntityManagerHelper;
import models.entities.transporte.MedioTransporte;
import models.entities.transporte.privado.TransportePrivado;
import models.entities.trayectos.Tramo;
import models.repositories.daos.DAO;

import javax.persistence.DiscriminatorValue;
import javax.persistence.criteria.*;
import java.util.List;

public class RepositorioDeTramos extends Repositorio<Tramo>{
    public RepositorioDeTramos(DAO<Tramo> dao) {
        super(dao);
    }

    public List<Tramo> buscarTodosTransportePrivado(){
        return this.dao.buscarTodos(this.condicionTransportePrivado());

    }
//    public List<Tramo> buscarTodosTransportePrivado(){
//        return (List<Tramo>) EntityManagerHelper.getEntityManager().createQuery("FROM Tramo t WHERE TYPE(medioTransporte) = 'transporte_privado'").getResultList();
//
//    }

    private BusquedaCondicional condicionTransportePrivado() {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Tramo> tramoCriteriaQuery = criteriaBuilder.createQuery(Tramo.class);

        Root<Tramo> condicionRaiz = tramoCriteriaQuery.from(Tramo.class);

        Predicate condicion = criteriaBuilder.equal(condicionRaiz.get("medioTransporte").get("tipoTransporte"),"transporte_privado");
        //this.getClass().getAnnotation(DiscriminatorValue.class).value()
        tramoCriteriaQuery.where(condicion);
        return new BusquedaCondicional(null, tramoCriteriaQuery);
    }
}
