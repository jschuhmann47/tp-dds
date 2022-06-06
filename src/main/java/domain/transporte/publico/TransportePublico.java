package domain.transporte.publico;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

public class TransportePublico implements MedioTransporte {

    private Linea linea;

    public TransportePublico(Linea linea) {
        this.linea = linea;
    }

    public String detalle() {
        return null;
    } //TODO

    public Distancia calcularDistancia(Direccion origen, Direccion destino) {
        return linea.calcularDistanciaEntreParadas(origen, destino);
    }


    public Linea getLinea() {
        return linea;
    }
    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
