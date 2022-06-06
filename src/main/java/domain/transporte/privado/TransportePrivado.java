package domain.transporte.privado;

import com.sun.org.apache.xpath.internal.operations.Or;
import domain.geoDDS.Direccion;
import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.entidades.Distancia;
import domain.organizaciones.CompartirTrayecto;
import domain.organizaciones.Organizacion;
import domain.organizaciones.Trabajador;
import domain.transporte.MedioTransporte;
import domain.transporte.TipoCombustible;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransportePrivado implements MedioTransporte {
    TipoVehiculo tipo;
    TipoCombustible tipoCombustible;
    CompartirTrayecto compartirTrayecto = new CompartirTrayecto();

    public List<Trabajador> getPersonasABordo() {
        return personasABordo;
    }

    public void setPersonasABordo(List<Trabajador> personasABordo) {
        this.personasABordo = personasABordo;
    }

    List<Trabajador> personasABordo;


    public String detalle() {

        return "Tipo de vehiculo:" + tipo.toString() + " - " + "Tipo de combustible: " + tipoCombustible.toString();
    }


    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws IOException {
        return ServicioCalcularDistancia.getInstance().distanciaEntre(origen, destino);
    }

    public boolean trabajadorPuedeCompartirVehiculo(Trabajador trabajador){
        return compartirTrayecto.validarTrabajador(trabajador,this.getPersonasABordo());
    }

}
