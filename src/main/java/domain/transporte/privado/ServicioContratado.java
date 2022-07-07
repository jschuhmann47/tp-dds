package domain.transporte.privado;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

import java.io.IOException;

public class ServicioContratado implements MedioTransporte {
    private TransportePrivado transportePrivado;
    private String nombre;

    public String detalle() {
        return "Nombre del servicio contratado: " + nombre;
    }

    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws IOException {
        return transportePrivado.calcularDistancia(origen,destino);
    }

    public Double getConsumoPorKM() {
        return transportePrivado.getConsumoPorKM();
    }

    public Double calcularHC(Distancia distancia) {
        return transportePrivado.calcularHC(distancia);
    }

}
