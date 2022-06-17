package domain.seguridad;

import domain.seguridad.chequeos.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ValidadorContrasenia {
    private static int caracteresMinimos=8; //separar
    private static ArrayList<String> topPeoresContrasenias;


    private static List<Chequeo> listaDeChequeos = new ArrayList<Chequeo>();

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

    public ValidadorContrasenia() throws IOException { //constructor
        topPeoresContrasenias = new ArrayList<String>();
        BufferedReader buffer = new BufferedReader(new FileReader("src/main/java/domain/" +
                                                                    "seguridad/chequeos/peoresContrasenias.txt"));
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


//    private boolean cumpleEstandaresDeContrasenia(String contrasenia) {
//        return this.cantidadCaracteres(contrasenia)>=caracteresMinimos &&
//                this.contieneMinuscula(contrasenia) &&
//                this.contieneMayuscula(contrasenia) &&
//                this.contieneNumero(contrasenia);
//    }
//
//    private boolean contieneMinuscula(String contrasenia){ //mas modular esto
//        Minuscula min=new Minuscula();
//        return min.chequear(contrasenia);
//    }
//    private boolean contieneMayuscula(String contrasenia){
//        Mayuscula mayus=new Mayuscula();
//        return mayus.chequear(contrasenia);
//    }
//    private boolean contieneNumero(String contrasenia){
//        Numero num=new Numero();
//        return num.chequear(contrasenia);
//    }
    //preferiblemente static esto
//para extraer la logica hicimos que cada clase sepa como resolver el chequeo.

//    private boolean estaEnElTopPeoresContrasenias(String contrasenia) throws FileNotFoundException {
//        for (String leido : topPeoresContrasenias){
//            if (Objects.equals(leido,contrasenia)){
//                return true;
//            }
//        }
//        return false;
//    }

}

