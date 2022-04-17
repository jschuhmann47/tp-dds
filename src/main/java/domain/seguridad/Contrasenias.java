package domain.seguridad;

import java.util.Scanner;

public class Contrasenias {
    int caracteresMinimos=8;
    public boolean esContraseniaValida(String contrasenia){
        if(this.cantidadCaracteres(contrasenia)<caracteresMinimos || this.estaEnElTopPeoresContrasenias(contrasenia)) {
            return false;
        }
        return true;
    }

    private boolean estaEnElTopPeoresContrasenias(String contrasenia) {
        //TODO
        //leer del archivo y comparar c/u?
        return true;
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
