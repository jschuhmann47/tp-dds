package domain.transporte.publico;

import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.entidades.Distancia;
import domain.geoDDS.Direccion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Linea {

    private String nombreDeLinea;
    private List<Direccion> paradas;

    private HashMap<List<String>, List<String>> distanciasParadas;

    public Linea(String nombreDeLinea, List<Direccion> paradas) throws IOException {
        this.nombreDeLinea = nombreDeLinea;
        this.paradas = paradas;
//        distanciasParadas = new HashMap<String, List<String>>();
//        BufferedReader buffer = new BufferedReader(new FileReader("src/main/java/domain/" +
//                "transporte/publico/distancias.txt"));
//        String lineaLeida = buffer.readLine();
//        while (lineaLeida != null){
//            String[] leido = lineaLeida.split(":"); //s[1] should be the key, s[0] is what should go into the list
//            List<String> l = h.get(s[1]); //see if you already have a list for current key
//            if(l == null) { //if not create one and put it in the map
//                l = new ArrayList<String>();
//                h.put(s[1], l);
//            }
//            l.add(s[0]); //add s[0] into the list for current key
//            lineaLeida = buffer.readLine();
//        }
//        buffer.close();

    }

    public Distancia calcularDistanciaEntreParadas(Direccion actual, Direccion proxima) {




        return null; //TODO donde ponemos los datos "hardcodeados"
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
