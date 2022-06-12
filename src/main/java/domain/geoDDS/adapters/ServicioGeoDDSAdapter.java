package domain.geoDDS.adapters;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

import java.io.IOException;

public interface ServicioGeoDDSAdapter {
    Distancia distanciaEntre(Direccion direccionOrigen, Direccion direccionDestino) throws IOException;

}
