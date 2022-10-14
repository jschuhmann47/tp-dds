package models.repositories.daos;


import db.EntityManagerHelper;
import models.repositories.BusquedaCondicional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DAOHibernate<T> implements DAO<T> {
    private Class<T> type;

    public DAOHibernate(Class<T> type){
        this.type = type;
    }

    @Override
    public List<T> buscarTodos() {
        CriteriaBuilder builder = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> critera = builder.createQuery(this.type);
        critera.from(type);
        return EntityManagerHelper.getEntityManager().createQuery(critera).getResultList();
    }

    @Override
    public T buscar(int id) {
        return EntityManagerHelper.getEntityManager().find(type, id);
    }

    @Override
    public T buscar(BusquedaCondicional condicional) {
        return (T) EntityManagerHelper.getEntityManager()
                .createQuery(condicional.getCondicionCriterio())
                .getSingleResult();
    }

    @Override
    public List<T> buscarTodos(BusquedaCondicional condicional) {
        return (List<T>) EntityManagerHelper.getEntityManager()
                .createQuery(condicional.getCondicionCriterio())
                .getResultList();
    }

    public List<T> buscarQuery(String query) {
        return (List<T>) EntityManagerHelper.getEntityManager()
                .createQuery(query)
                .getResultList();
    }

    @Override
    public void agregar(Object unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    @Override
    public void modificar(Object unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    @Override
    public void eliminar(Object unObjeto) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(unObjeto);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
}
