package models.repositories;

import models.entities.organizaciones.entidades.Trabajador;
import models.repositories.daos.DAO;

public class RepositorioDeTrabajadores extends Repositorio<Trabajador>{
    public RepositorioDeTrabajadores(DAO<Trabajador> dao) {
        super(dao);
    }

}
