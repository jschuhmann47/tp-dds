package domain.transporte.privado;

import domain.geoDDS.Direccion;
import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.IOException;

@Entity
@DiscriminatorValue("servicio_ecologico")
public class ServicioEcologico extends MedioTransporte {

    public ServicioEcologico() {
        this.tipo = null;
        this.tipoCombustible = null;
    }

    public String detalle() {
        return "";
    }


    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws IOException {
        return ServicioCalcularDistancia.distanciaEntre(origen, destino);
    }

    public Double getConsumoPorKM() {
        return 0.0;
    }

    public Double calcularHC(Distancia distancia) {
        return 0.0;
    }
}
