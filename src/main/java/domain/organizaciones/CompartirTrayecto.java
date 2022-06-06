package domain.organizaciones;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompartirTrayecto {

    public boolean validarTrabajador(Trabajador trabajador, List<Trabajador> personasABordo){

        if(personasABordo.isEmpty()){
            return true;
        }
        else{
            return perteneceAAlgunaOrg(trabajador, this.organizacionesALasQuePertenecenLosPasajeros(personasABordo));
        }
    }

    private boolean perteneceAAlgunaOrg(Trabajador trabajador,List<Organizacion> organizaciones){
        return !Collections.disjoint(organizacionesDeUnaPersona(trabajador),organizaciones);
    }

    private List<Organizacion> organizacionesALasQuePertenecenLosPasajeros(List<Trabajador> personasABordo){
        if(!personasABordo.isEmpty()){
            return this.organizacionesDeUnaPersona(personasABordo.stream().findFirst().get());
        }
        return Collections.emptyList();
    }

    private List<Organizacion> organizacionesDeUnaPersona(Trabajador trabajador){
        return trabajador.sectores.stream().map(s->s.organizacion).collect(Collectors.toList());
    }



}
