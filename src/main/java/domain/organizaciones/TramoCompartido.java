package domain.organizaciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TramoCompartido {

    private List<Organizacion> orgPosibles = new ArrayList<>();
    private List<Trabajador> personasABordo = new ArrayList<>();

    public void validarTrabajador(Trabajador trabajador) throws Exception {

        if(this.personasABordo.isEmpty()){
            this.agregarPersonaATramo(trabajador);
        }
        else{
            perteneceAAlgunaOrg(trabajador, this.orgPosibles);
        }
    }

    void perteneceAAlgunaOrg(Trabajador trabajador,List<Organizacion> organizaciones) throws Exception {
        if (!Collections.disjoint(organizacionesDeUnaPersona(trabajador),organizaciones)){
            this.agregarPersonaATramo(trabajador);

        }else{
            throw new Exception("El trabajador ingresado no puede compartir este tramo con las personas ya cargadas al mismo");
        }
    }

    //borrar organizacionesALasQuePertenecenLosPasajeros del uml

    List<Organizacion> organizacionesDeUnaPersona(Trabajador trabajador){
        return trabajador.sectores.stream().map(s->s.organizacion).collect(Collectors.toList());
    }

    private void agregarPersonaATramo(Trabajador trabajador){
        this.personasABordo.add(trabajador);
        this.orgPosibles.addAll(organizacionesDeUnaPersona(trabajador));
    }

    public int cantidadDeTrabajadores(){
        return personasABordo.size();
    }

}
