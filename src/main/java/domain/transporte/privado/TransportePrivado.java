package domain.transporte.privado;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;
import domain.transporte.TipoCombustible;

public class TransportePrivado implements MedioTransporte {
    TipoVehiculo tipo;
    TipoCombustible tipoCombustible;


    public String detalle() {

        return null;
    }


    public Distancia calcularDistancia(Direccion origen, Direccion destino) {

        return null;
    }
}
