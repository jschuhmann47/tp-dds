package domain.transporte.publico;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

import java.io.IOException;
import java.util.*;

public class Linea {

    private String nombreDeLinea;

    private List<Parada> paradas=new ArrayList<>();

    public Linea(String nombreDeLinea, Parada ... variasParadas) throws IOException {
        this.nombreDeLinea = nombreDeLinea;
        paradas.addAll(Arrays.asList(variasParadas));

    }


    public Distancia calcularDistanciaEntreParadas(Parada anterior, Parada proxima) throws Exception {

        Distancia distanciaTotal = new Distancia((double) 0,"KM");
        Parada p = anterior;
        while (p != proxima && p != null){
            distanciaTotal.valor += p.distanciaSiguiente.valor;
            p = p.paradaSiguiente;
        }
        if (p==null){
            throw new Exception("La parada proxima no existe o no esta despues de la ingresada");
        }
        else{
            return distanciaTotal;
        }
    }


    public String getNombreDeLinea() {
        return nombreDeLinea;
    }

    public void setNombreDeLinea(String nombreDeLinea) {
        this.nombreDeLinea = nombreDeLinea;
    }

    public List<Parada> getParadas() {
        return paradas;
    }

    public void setParadas(Parada ... paradas) {
        this.paradas.addAll(Arrays.asList(paradas));
    }

    public String detallePrimerParada() {
        return paradas.get(0).direccion.getCalle() + " " + paradas.get(0).direccion.getAltura();
    }

    public String detalleUltimaParada() {
        return paradas.get(paradas.size()-1).direccion.getCalle() + " " + paradas.get(paradas.size()-1).direccion.getAltura();
    }


}
