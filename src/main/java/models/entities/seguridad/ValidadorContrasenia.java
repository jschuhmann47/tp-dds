package models.entities.seguridad;


import models.entities.seguridad.chequeos.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ValidadorContrasenia {
    private static final int caracteresMinimos=8;
    private static ArrayList<String> topPeoresContrasenias;


    private static final List<Chequeo> listaDeChequeos = new ArrayList<>();

    public static ArrayList<String> getTopPeoresContrasenias() {
        return topPeoresContrasenias;
    }

    public static int getCaracteresMinimos() {
        return caracteresMinimos;
    }

    //Agregar lo de que se puede activar y desactivar los chequeos
    //preguntar que se desactiva y que no



    public static boolean esContraseniaValida(String contrasenia){
        for (Chequeo check : listaDeChequeos) {
            if(check.estaActivo()){
                if(!check.chequear(contrasenia)){
                    return false;
                }
            }
        }
        return true;
    }

    public ValidadorContrasenia(String path) throws IOException { //constructor
        topPeoresContrasenias = new ArrayList<String>();
        BufferedReader buffer = new BufferedReader(new FileReader(path));
        String lineaLeida = buffer.readLine();
        while (lineaLeida != null){
            topPeoresContrasenias.add(lineaLeida);
            lineaLeida = buffer.readLine();
        }
        buffer.close();
        this.inicializarChequeos();
    }

    private void inicializarChequeos() {
        Minuscula minuscula = Minuscula.getInstance();
        Mayuscula mayuscula = Mayuscula.getInstance();
        TopPeores topPeores = new TopPeores();
        Numero numero = Numero.getInstance();
        Longitud longitud = new Longitud();

        listaDeChequeos.add(minuscula);
        listaDeChequeos.add(mayuscula);
        listaDeChequeos.add(numero);
        listaDeChequeos.add(topPeores);
        listaDeChequeos.add(longitud);
    }

}

