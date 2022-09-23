package models.entities.transporte.privado;

import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.ServicioCalcularDistancia;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.transporte.MedioTransporte;

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
