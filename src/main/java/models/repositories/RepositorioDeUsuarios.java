package models.repositories;

import models.entities.seguridad.cuentas.Usuario;
import models.repositories.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RepositorioDeUsuarios extends Repositorio<Usuario>{
    public RepositorioDeUsuarios(DAO<Usuario> dao) {
        super(dao);
    }

    public boolean existe(String nombreDeUsuario, String contrasenia) {
        return this.buscar(nombreDeUsuario,contrasenia) != null;
    }

    public Usuario buscar(String nombreDeUsuario, String contrasenia){
        return this.dao.buscar(this.condicionUsuarioYContrasenia(nombreDeUsuario,contrasenia));
    }

    private BusquedaCondicional condicionUsuarioYContrasenia(String nombreDeUsuario, String contrasenia){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Usuario> usuarioQuery = criteriaBuilder.createQuery(Usuario.class);

        Root<Usuario> condicionRaiz = usuarioQuery.from(Usuario.class);

        Predicate condicion = criteriaBuilder
                .and(criteriaBuilder.equal(condicionRaiz.get("nombreUsuario"), nombreDeUsuario),
                        criteriaBuilder.equal(condicionRaiz.get("contrasenia"),contrasenia));

        usuarioQuery.where(condicion);
        return new BusquedaCondicional(null, usuarioQuery);
    }
}
