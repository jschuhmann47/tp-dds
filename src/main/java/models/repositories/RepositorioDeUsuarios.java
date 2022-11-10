package models.repositories;

import models.entities.seguridad.cuentas.Usuario;
import models.repositories.daos.DAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class RepositorioDeUsuarios extends Repositorio<Usuario>{
    public RepositorioDeUsuarios(DAO<Usuario> dao) {
        super(dao);
    }

    public boolean existe(String nombreDeUsuario, String contrasenia) {
        return this.buscar(nombreDeUsuario,contrasenia) != null;
    }

    public boolean existeUsuario(String nombreDeUsuario) {
        return !this.usuarioConNombreDeUsuario(nombreDeUsuario).isEmpty();
    }

    public Usuario buscar(String nombreDeUsuario, String contrasenia){
        return this.dao.buscar(this.condicionUsuarioYContrasenia(nombreDeUsuario,contrasenia));
    }

    public List<Usuario> usuarioConNombreDeUsuario(String nombreDeUsuario){
        return this.dao.buscarTodos(this.condicionNombreUsuario(nombreDeUsuario));
    }

    private BusquedaCondicional condicionNombreUsuario(String nombreDeUsuario) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Usuario> usuarioQuery = criteriaBuilder.createQuery(Usuario.class);

        Root<Usuario> condicionRaiz = usuarioQuery.from(Usuario.class);

        Predicate condicion = criteriaBuilder.equal(condicionRaiz.get("nombreDeUsuario"), nombreDeUsuario);

        usuarioQuery.where(condicion);
        return new BusquedaCondicional(null, usuarioQuery);
    }

    private BusquedaCondicional condicionUsuarioYContrasenia(String nombreDeUsuario, String contrasenia){
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Usuario> usuarioQuery = criteriaBuilder.createQuery(Usuario.class);

        Root<Usuario> condicionRaiz = usuarioQuery.from(Usuario.class);

        Predicate condicion = criteriaBuilder
                .and(criteriaBuilder.equal(condicionRaiz.get("nombreDeUsuario"), nombreDeUsuario),
                        criteriaBuilder.equal(condicionRaiz.get("contrasenia"),contrasenia));

        usuarioQuery.where(condicion);
        return new BusquedaCondicional(null, usuarioQuery);
    }
}
