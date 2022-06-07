package domain.transporte.publico;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

import java.io.IOException;
import java.util.*;

public class Linea {

    private String nombreDeLinea;

    private List<Parada> paradas=new ArrayList<>();

    public Linea(String nombreDeLinea, Direccion ... variasParadas) throws IOException {
        this.nombreDeLinea = nombreDeLinea;
        paradas.addAll(Arrays.asList(variasParadas));

    }


    public Distancia calcularDistanciaEntreParadas(Direccion anterior, Direccion proxima) throws Exception {
        double distanciaTotal = 0;
        Direccion direccionActual = anterior;
        while(direccionActual != proxima){
            distanciaTotal += this.distanciaALaSiguiente(direccionActual);
            direccionActual = this.paradaSiguiente(direccionActual);
        }
        return new Distancia(String.valueOf(distanciaTotal),"KM");
    }
    private Double distanciaALaSiguiente(Direccion direccion) throws Exception {
        if(distanciaParadasSiguientes.get(direccion)==null){
            throw new Exception("No hay siguiente parada");
        }
        else{
            return distanciaParadasSiguientes.get(direccion);
        }
    }

    private Double distanciaALaAnterior(Direccion direccion) throws Exception {
        if(distanciaParadasAnteriores.get(direccion)==null){
            throw new Exception("No hay anterior parada");
        }
        else{
            return distanciaParadasAnteriores.get(direccion);
        }
    }

    private Direccion paradaSiguiente(Direccion direccion){
        return paradasSiguientes.get(direccion);
    }

    private Direccion paradaAnterior(Direccion direccion){
        return paradasAnteriores.get(direccion);
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
    public void setParadas(Direccion ... paradas) {
        this.paradas.addAll(Arrays.asList(paradas));
    }

    public String detallePrimerParada() {
        return paradas.get(0).getCalle() + " " + paradas.get(0).getAltura();
    }

    public String detalleUltimaParada() {
        return paradas.get(paradas.size()-1).getCalle() + " " + paradas.get(paradas.size()-1).getAltura();
    }


//    public Parada encontrarParada(Direccion actual) throws Exception {
//        Optional<Parada> posibleParada = distanciasParadas.stream().filter(t->t.direccion==actual).findFirst();
//        if(posibleParada.isPresent()){
//            return posibleParada.get();
//        }
//        else {
//            throw new Exception();
//        }
//    }

    //    public Distancia calcularDistanciaEntreParadas(Direccion anterior, Direccion proxima) throws Exception {
//        double distanciaTotal = 0;
//        int indiceActual = distanciasParadas.indexOf(encontrarParada(anterior));
//        int indiceProxima = distanciasParadas.indexOf(encontrarParada(proxima));
//        for (; indiceActual != distanciasParadas.size() && indiceActual!=indiceProxima;indiceActual++) {
//            distanciaTotal += distanciasParadas.get(indiceActual).distanciaALaSiguiente;
//        }
//        //List<Double> distancias = distanciasParadas.stream().mapToDouble(t->t.distanciaALaSiguiente).collect();
//        return new Distancia(String.valueOf(distanciaTotal),"KM");
//    }

}
