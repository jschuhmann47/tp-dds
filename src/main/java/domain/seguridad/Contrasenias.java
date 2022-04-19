package domain.seguridad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Contrasenias {
    int caracteresMinimos=8;
    public boolean esContraseniaValida(String contrasenia) {
        if (this.cumpleCondicionesDeContrasenia(contrasenia) && not(this.estaEnElTopPeoresContrasenias(contrasenia))) {
            return true;
        }
        return false;
    }

    private boolean cumpleCondicionesDeContrasenia(String contrasenia) {
        return this.cantidadCaracteres(contrasenia)<caracteresMinimos && this.chequeoCaracteres(contrasenia);

    }


    private static boolean chequeoCaracteres(String contrasenia) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for (int i = 0; i < contrasenia.length(); i++) {
            ch = contrasenia.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if (numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }

    private boolean estaEnElTopPeoresContrasenias(String contrasenia) {
        //TODO
        //leer del archivo y comparar c/u?
        return true;
    }


    public class ReadFile {
        public static void main(String[] args) {
            try {
                File myObj = new File("peoresContrasenias.txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    System.out.println(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    public int cantidadCaracteres(String contrasenia){
        return contrasenia.length();
    }

    public String crearContrasenia(){
        Scanner myObj = new Scanner(System.in);
        System.out.println("Introduzca una contrasenia: ");
        String input = myObj.nextLine();
        while (!esContraseniaValida(input)){
            System.out.println("Su contrasenia es muy insegura. Por favor ingrese otra: ");
            input = myObj.nextLine();
        }
        System.out.println("Su contrsenia fue aceptada.");
        return input;
    }

    /*
    *
    *
    * Siguiendo las recomendaciones del OWASP (Proyecto Abierto de Seguridad en Aplicaciones Web) que se ha constituido
    *  en un estándar de facto para la seguridad, se pide:
- No utilice credenciales por defecto en su software, particularmente en el caso de administradores.
- Implemente controles contra contraseñas débiles. Cuando el usuario ingrese una nueva clave, la
misma puede verificarse contra la lista del Top 10.000 de peores contraseñas.
OWASP Top 10 - 2017


- Alinear la política de longitud, complejidad y rotación de contraseñas con las recomendaciones
de la Sección 5.1.1 para Secretos Memorizados de la Guía NIST3 800-634

    * */
}
