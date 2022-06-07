package domain.transporte.privado;

import domain.geoDDS.Direccion;
import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.entidades.Distancia;
import domain.organizaciones.CompartirTramo;
import domain.organizaciones.Trabajador;
import domain.transporte.MedioTransporte;
import domain.transporte.TipoCombustible;

import java.io.IOException;
import java.util.List;

public class TransportePrivado implements MedioTransporte {
    TipoVehiculo tipo;
    TipoCombustible tipoCombustible;


    public List<Trabajador> getPersonasABordo() {
        return personasABordo;
    }



    List<Trabajador> personasABordo;


    public String detalle() {

        return "Tipo de vehiculo:" + tipo.toString() + " - " + "Tipo de combustible: " + tipoCombustible.toString();
    }


    public void setPersonasABordo(List<Trabajador> personasABordo) {
        this.personasABordo = personasABordo;
    }


    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws IOException {
        return ServicioCalcularDistancia.getInstance().distanciaEntre(origen, destino);
    }

    public void trabajadorPuedeCompartirVehiculo(Trabajador trabajador){
        if(CompartirTramo.validarTrabajador(trabajador,this.getPersonasABordo())){
            personasABordo.add(trabajador);
        }
    }

}
