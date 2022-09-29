package models.repositories;

import models.daos.DAO;
import models.entities.organizaciones.entidades.Organizacion;

public class RepositorioDeOrganizaciones extends Repositorio<Organizacion> {
    public RepositorioDeOrganizaciones(DAO<Organizacion> dao) {
        super(dao);
    }
}
