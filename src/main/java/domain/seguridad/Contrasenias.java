package domain.seguridad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Contrasenias {
    int caracteresMinimos=8;

    public void setContrasenia(String contrasenia) { //para testear
        this.contrasenia = contrasenia;
    }

    public String getContrasenia() { //para testear
        return contrasenia;
    }

    private String contrasenia;

    public boolean esContraseniaValida(String contrasenia) throws FileNotFoundException {
        return this.cumpleEstandaresDeContrasenia(contrasenia) && !this.estaEnElTopPeoresContrasenias(contrasenia);
    }

    private boolean cumpleEstandaresDeContrasenia(String contrasenia) {
        return this.cantidadCaracteres(contrasenia)>=caracteresMinimos &&
                this.contieneMinuscula(contrasenia) &&
                this.contieneMayuscula(contrasenia) &&
                this.contieneNumero(contrasenia);
    }

    /*
    private boolean chequeoCaracteres(String contrasenia) {
        char ch;
        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;
        for (int i = 0; i < contrasenia.length(); i++) {
            ch = contrasenia.charAt(i);
            if (Character.isDigit(ch)) {
                tieneNumero = true;
            }
            if (Character.isUpperCase(ch)) {
                tieneMayuscula = true;
            }
            if (Character.isLowerCase(ch)) {
                tieneMinuscula = true;
            }
        }
        return tieneNumero && tieneMayuscula && tieneMinuscula;
    }
     */

    private boolean contieneMinuscula(String contrasenia){
        Minuscula min=new Minuscula();
        return min.chequear(contrasenia);
    }
    private boolean contieneMayuscula(String contrasenia){
        Mayuscula mayus=new Mayuscula();
        return mayus.chequear(contrasenia);
    }
    private boolean contieneNumero(String contrasenia){
        Numero num=new Numero();
        return num.chequear(contrasenia);
    }
    //preferiblemente static esto
//para extraer la logica hicimos que cada clase sepa como resolver el chequeo.

    public boolean estaEnElTopPeoresContrasenias(String contrasenia) throws FileNotFoundException {
        try {
            File archivo = new File("peoresContrasenias.txt");
            Scanner scanner = new Scanner(archivo);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                if (Objects.equals(contrasenia, data)){
                    scanner.close();
                    return true;
                }
            }
            scanner.close();
            return false;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(); //no me acuerdo si funcionaban asi
        }
    }


    public int cantidadCaracteres(String contrasenia){
        return contrasenia.length();
    }

    public void crearContrasenia() throws FileNotFoundException {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Introduzca una contrasenia: ");
        String input = myObj.nextLine();
        while (!esContraseniaValida(input)){
            System.out.println("Su contrasenia es muy insegura. Por favor ingrese otra: ");
            input = myObj.nextLine();
        }
        System.out.println("Su contrsenia fue aceptada.");
        this.contrasenia=input;
    }
}
