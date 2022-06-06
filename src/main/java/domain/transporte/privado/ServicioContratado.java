package domain.transporte.privado;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

import java.io.IOException;

public class ServicioContratado implements MedioTransporte {
    private TransportePrivado transportePrivado;
    private String nombre; //clase servicio?

    public String detalle() {
        return null;
    }

    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws IOException {
        return transportePrivado.calcularDistancia(origen,destino);
    }
}
