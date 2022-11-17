package models.repositories.factories;

import models.entities.transporte.privado.TransportePrivado;
import models.repositories.RepositorioDeTransportesPrivados;
import models.repositories.daos.DAOHibernate;

public class FactoryRepositorioDeTransportesPrivados {
    private static RepositorioDeTransportesPrivados repo = null;

    public static RepositorioDeTransportesPrivados get(){
        if(repo == null){
            repo = new RepositorioDeTransportesPrivados(new DAOHibernate<>(TransportePrivado.class));
        }
        return repo;
    }
}
