package domain.transporte.privado;

import domain.geoDDS.Direccion;
import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.entidades.Distancia;
import domain.organizaciones.TramoCompartido;
import domain.organizaciones.Trabajador;
import domain.transporte.MedioTransporte;
import domain.transporte.TipoCombustible;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransportePrivado implements MedioTransporte {
    TipoVehiculo tipo;
    TipoCombustible tipoCombustible;

    TramoCompartido compartirTramo = new TramoCompartido();


    public String detalle() {

        return "Tipo de vehiculo:" + tipo.toString() + " - " + "Tipo de combustible: " + tipoCombustible.toString();
    }


    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws IOException {
        return ServicioCalcularDistancia.getInstance().distanciaEntre(origen, destino);
    }

    public void trabajadorPuedeCompartirVehiculo(Trabajador trabajador) throws Exception {
        compartirTramo.validarTrabajador(trabajador);
    }

}
