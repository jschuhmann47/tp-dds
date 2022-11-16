package models.entities.transporte.privado;

import lombok.Getter;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.ServicioCalcularDistancia;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.transporte.MedioTransporte;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.IOException;

@Entity
@DiscriminatorValue("servicio_ecologico")
public class ServicioEcologico extends MedioTransporte {

//    @Transient
//    @Getter
//    private final String tipoTransporte = "servicio_ecologico";

    public ServicioEcologico() {
        this.tipo = null;
        this.tipoCombustible = null;
        this.tipoTransporte = "servicio_ecologico";
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
