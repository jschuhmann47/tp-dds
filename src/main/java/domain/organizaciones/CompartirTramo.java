package domain.organizaciones;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompartirTramo {

    public static boolean validarTrabajador(Trabajador trabajador, List<Trabajador> personasABordo){

        if(personasABordo.isEmpty()){
            return true;
        }
        else{
            return perteneceAAlgunaOrg(trabajador, organizacionesALasQuePertenecenLosPasajeros(personasABordo));
        }
    }

    static boolean perteneceAAlgunaOrg(Trabajador trabajador,List<Organizacion> organizaciones){
        return !Collections.disjoint(organizacionesDeUnaPersona(trabajador),organizaciones);
    }

    static List<Organizacion> organizacionesALasQuePertenecenLosPasajeros(List<Trabajador> personasABordo){
        if(!personasABordo.isEmpty()){
            return organizacionesDeUnaPersona(personasABordo.stream().findFirst().get());
        }
        return Collections.emptyList();
    }

    static List<Organizacion> organizacionesDeUnaPersona(Trabajador trabajador){
        return trabajador.sectores.stream().map(s->s.organizacion).collect(Collectors.toList());
    }

    /*
    * pepe: A y B
    * jose: A
    * fulanito: A
    *
    * carlos: B
    * */

    //tomar las del primero e ir sacando las que no comparten

}
