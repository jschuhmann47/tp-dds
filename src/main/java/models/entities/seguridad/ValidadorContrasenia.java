package models.entities.seguridad;


import lombok.Getter;
import lombok.Setter;
import models.entities.seguridad.chequeos.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ValidadorContrasenia {
    private static final int caracteresMinimos=8;
    private static ArrayList<String> topPeoresContrasenias;

    @Getter
    @Setter
    private static List<Chequeo> listaDeChequeos = new ArrayList<>();

    public static ArrayList<String> getTopPeoresContrasenias() {
        return topPeoresContrasenias;
    }

    public static int getCaracteresMinimos() {
        return caracteresMinimos;
    }


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

    public static void setearPeoresContrasenias (String path) throws IOException {
        topPeoresContrasenias = new ArrayList<String>();
        BufferedReader buffer = new BufferedReader(new FileReader(path));
        String lineaLeida = buffer.readLine();
        while (lineaLeida != null){
            topPeoresContrasenias.add(lineaLeida);
            lineaLeida = buffer.readLine();
        }
        buffer.close();
        ValidadorContrasenia.inicializarChequeos();
    }

    public static void inicializarChequeos() {
        Minuscula minuscula = new Minuscula();
        Mayuscula mayuscula = new Mayuscula();
        TopPeores topPeores = new TopPeores();
        Numero numero = new Numero();
        Longitud longitud = new Longitud();

        listaDeChequeos.add(minuscula);
        listaDeChequeos.add(mayuscula);
        listaDeChequeos.add(numero);
        listaDeChequeos.add(topPeores);
        listaDeChequeos.add(longitud);
    }

}

