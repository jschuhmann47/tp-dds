package models.repositories.factories;

import models.entities.geoDDS.entidades.Municipio;
import models.entities.organizaciones.entidades.AgenteSectorial;
import models.repositories.RepositorioDeAgentesSectoriales;
import models.repositories.RepositorioDeMunicipios;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeAgentesSectoriales {
    private static RepositorioDeAgentesSectoriales repo = null;

//    static{
//        repo = null;
//    }

    public static RepositorioDeAgentesSectoriales get(){
        if(repo == null){
            repo = new RepositorioDeAgentesSectoriales(new DAOHibernate<>(AgenteSectorial.class));
        }
        return repo;
    }
}
