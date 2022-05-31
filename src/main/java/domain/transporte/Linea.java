package domain.transporte;

import java.util.List;
//FALTA EL IMPORT DE DIRECCION

public class Linea {

    private String nombreDeLinea;
    private List<Direccion> paradas;

    public Linea(String nombreDeLinea, List<Direccion> paradas) {
        this.nombreDeLinea = nombreDeLinea;
        this.paradas = paradas;
    }

    public int calcularDistanciaEntreParadas(Direccion actual, Direccion proxima) {
        return 0; //TODO falta calculo de distancia entre paradas
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
