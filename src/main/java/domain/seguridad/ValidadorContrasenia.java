package domain.seguridad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ValidadorContrasenia {
    private int caracteresMinimos=8; //separar
    ArrayList<String> topPeoresContrasenias = new ArrayList<String>();
    //Agregar lo de que se puede activar y desactivar los chequeos
    //preguntar que se desactiva y que no

    public boolean esContraseniaValida(String contrasenia) throws FileNotFoundException {
        if (this.cumpleEstandaresDeContrasenia(contrasenia)){
            return !this.estaEnElTopPeoresContrasenias(contrasenia);
        }
        return false;
    }

    private boolean cumpleEstandaresDeContrasenia(String contrasenia) {
        return this.cantidadCaracteres(contrasenia)>=caracteresMinimos &&
                this.contieneMinuscula(contrasenia) &&
                this.contieneMayuscula(contrasenia) &&
                this.contieneNumero(contrasenia);
    }

    private boolean contieneMinuscula(String contrasenia){ //mas modular esto
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

    private boolean estaEnElTopPeoresContrasenias(String contrasenia) throws FileNotFoundException {
        try {
            File archivo = new File("src/main/java/domain/seguridad/peoresContrasenias.txt"); //cargarlo a un array
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

    private int cantidadCaracteres(String contrasenia){
        return contrasenia.length();
    }

}
