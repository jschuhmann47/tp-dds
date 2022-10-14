package models.repositories;

import models.entities.organizaciones.entidades.AgenteSectorial;
import models.repositories.daos.DAO;

public class RepositorioDeAgentesSectoriales extends Repositorio<AgenteSectorial> {
    public RepositorioDeAgentesSectoriales(DAO<AgenteSectorial> dao) {
        super(dao);
    }
}
