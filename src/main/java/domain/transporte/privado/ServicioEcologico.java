package domain.transporte.privado;

import domain.geoDDS.Direccion;
import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

import java.io.IOException;

public class ServicioEcologico implements MedioTransporte {

    public String detalle() {
        return "";
    }


    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws IOException {
        return ServicioCalcularDistancia.distanciaEntre(origen, destino);
    }

    public Double getConsumoPorKM() {
        return 0.0;
    }

    public Double calcularHC(Distancia distancia) {
        return 0.0;
    }
}
