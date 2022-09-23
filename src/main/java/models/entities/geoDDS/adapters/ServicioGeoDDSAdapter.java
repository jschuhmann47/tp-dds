package models.entities.geoDDS.adapters;

import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.entidades.Distancia;

import java.io.IOException;

public interface ServicioGeoDDSAdapter {
    Distancia distanciaEntre(Direccion direccionOrigen, Direccion direccionDestino) throws IOException;

}
