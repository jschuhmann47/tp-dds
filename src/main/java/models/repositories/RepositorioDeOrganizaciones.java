package models.repositories;

import db.EntityManagerHelper;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.entidades.Localidad;
import models.entities.geoDDS.entidades.Municipio;
import models.repositories.daos.DAO;
import models.entities.organizaciones.entidades.Organizacion;

import javax.persistence.criteria.*;
import java.util.List;

public class RepositorioDeOrganizaciones extends Repositorio<Organizacion> {
    public RepositorioDeOrganizaciones(DAO<Organizacion> dao) {
        super(dao);
    }


    public List<Organizacion> buscarTodosDeMunicipio(Municipio municipio) {
        return EntityManagerHelper.getEntityManager()
                .createQuery("FROM Organizacion o " +
                        "WHERE o.direccion.localidad.municipio.id = " + municipio.getId()).getResultList();
    }
//    public List<Organizacion> buscarTodosDeMunicipio(Municipio municipio) {
//        return this.dao.buscarTodos(this.condicionMunicipio(municipio));
//    }

    public Organizacion buscarPorRazonSocial(String razonSocial){
        return this.dao.buscar(this.condicionRazonSocial(razonSocial));
    }

    public List<Organizacion> buscarTodosClasificacion(String clasificacion) {
        return EntityManagerHelper.getEntityManager()
                .createQuery("FROM Organizacion o " +
                        "WHERE '" + clasificacion + "' MEMBER OF o.clasificacionOrg").getResultList();
    }


//    public List<Organizacion> buscarTodosClasificacion(String clasificacion) {
//        return this.dao.buscarTodos(this.condicionClasificacion(clasificacion));
//    }

    private BusquedaCondicional condicionRazonSocial(String razonSocial) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Organizacion> usuarioQuery = criteriaBuilder.createQuery(Organizacion.class);

        Root<Organizacion> condicionRaiz = usuarioQuery.from(Organizacion.class);

        Predicate condicionRazonSocial = criteriaBuilder.equal(condicionRaiz.get("razonSocial"), razonSocial);

        usuarioQuery.where(condicionRazonSocial);

        return new BusquedaCondicional(null, usuarioQuery);
    }

    private BusquedaCondicional condicionMunicipio(Municipio municipio){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Organizacion> usuarioQuery = criteriaBuilder.createQuery(Organizacion.class);

        Root<Organizacion> condicionRaiz = usuarioQuery.from(Organizacion.class);
        Join<Organizacion, Direccion> direccionJoin = condicionRaiz.join("direccion", JoinType.INNER);
        Join<Organizacion, Localidad> localidadJoin = condicionRaiz.join("localidad", JoinType.INNER);
        Join<Organizacion, Municipio> municipioJoin = condicionRaiz.join("municipio",JoinType.INNER); //ver si es el atributo o la columna


        Predicate condicionMunicipio = criteriaBuilder.equal(condicionRaiz.get("municipio"), municipio);

        usuarioQuery.where(condicionMunicipio);

        return new BusquedaCondicional(null, usuarioQuery);
    }



    public BusquedaCondicional condicionClasificacion(String clasificacion) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Organizacion> usuarioQuery = criteriaBuilder.createQuery(Organizacion.class);

        Root<Organizacion> condicionRaiz = usuarioQuery.from(Organizacion.class);
        Join<Organizacion, String> clasificacionJoin = condicionRaiz.join("clasificacionOrg", JoinType.INNER);

        Predicate condicionClasificacion = criteriaBuilder.equal(condicionRaiz.get("clasificacionOrg"), clasificacion); //todo chequear
//
        usuarioQuery.where(condicionClasificacion);

        return new BusquedaCondicional(null, usuarioQuery);
    }
}
