package domain.transporte.publico;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;

import javax.persistence.*;
import java.io.IOException;
import java.util.*;

@Entity
@Table(name = "linea")
public class Linea {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "nombre_linea")
    private String nombreDeLinea;

    @Transient
    private List<Parada> paradas=new ArrayList<>();

    public int getId() {
        return id;
    }

    public Linea() {
    }

    public Linea(String nombreDeLinea, Parada ... variasParadas) throws IOException {
        this.nombreDeLinea = nombreDeLinea;
        paradas.addAll(Arrays.asList(variasParadas));

    }


    public Distancia calcularDistanciaEntreParadas(Parada unaParada, Parada otraParada) throws Exception {
        int indiceParadaUno = paradas.indexOf(unaParada);
        int indiceParadaDos = paradas.indexOf(otraParada);
        if(indiceParadaUno == -1 || indiceParadaDos == -1){
            throw new Exception("Una de las paradas ingresadas no se encontro");
        }
        if(indiceParadaUno<indiceParadaDos){
            return distanciaEntreParadaAnteriorYSiguiente(unaParada,otraParada);
        }else{
            return distanciaEntreParadaAnteriorYSiguiente(otraParada,unaParada);
        }

    }

    private Distancia distanciaEntreParadaAnteriorYSiguiente(Parada anterior, Parada siguiente){
        Distancia distanciaTotal = new Distancia(0.0,"KM");
        Parada p = anterior;
        while (p != siguiente && p != null){
            distanciaTotal.valor += p.distanciaSiguiente.valor;
            p = p.paradaSiguiente;
        }
        return distanciaTotal;
    }

    private Parada buscarParadaDadaDireccion(Direccion direccion) throws Exception {
        Optional<Parada> posibleParada = this.paradas.stream().filter(p -> p.direccion == direccion).findFirst();
        if(posibleParada.isPresent()){
            return posibleParada.get();
        }else{
            throw new Exception("No existe parada en esa direccion");
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


    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws Exception {
        Parada paradaInicio = this.buscarParadaDadaDireccion(origen);
        Parada paradaFinal = this.buscarParadaDadaDireccion(destino);
        return this.calcularDistanciaEntreParadas(paradaInicio,paradaFinal);
    }
}
