package domain.transporte.publico;

import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.entidades.Distancia;
import domain.geoDDS.Direccion;

import java.io.IOException;
import java.util.List;

public class Linea {

    private String nombreDeLinea;
    private List<Direccion> paradas;

    public Linea(String nombreDeLinea, List<Direccion> paradas) {
        this.nombreDeLinea = nombreDeLinea;
        this.paradas = paradas;
    }

    public Distancia calcularDistanciaEntreParadas(Direccion actual, Direccion proxima) throws IOException {
        return ServicioCalcularDistancia.getInstance().distanciaEntre(actual, proxima);
    }


    public String getNombreDeLinea() {
        return nombreDeLinea;
    }
    public void setNombreDeLinea(String nombreDeLinea) {
        this.nombreDeLinea = nombreDeLinea;
    }
    public List<Direccion> getParadas() {
        return paradas;
    }
    public void setParadas(List<Direccion> paradas) {
        this.paradas = paradas;
    }
}
