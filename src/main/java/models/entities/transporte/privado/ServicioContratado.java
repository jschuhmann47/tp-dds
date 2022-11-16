package models.entities.transporte.privado;

import lombok.Getter;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.transporte.MedioTransporte;

import javax.persistence.*;
import java.io.IOException;


@Entity
@DiscriminatorValue("servicio_contratado")
public class ServicioContratado extends MedioTransporte {

//    @Transient
//    @Getter
//    private final String tipoTransporte = "servicio_contratado";

    @Embedded
    private TransportePrivado transportePrivado;

    @Column(name = "servicio_contratado_nombre")
    private String nombre;

    public ServicioContratado() {
        this.tipoTransporte = "servicio_contratado";
    }

    public ServicioContratado(TransportePrivado transportePrivado, String nombre) {
        this.transportePrivado = transportePrivado;
        this.nombre = nombre;
        this.tipoTransporte = "servicio_contratado";
    }

    public String detalle() {
        return "Nombre del servicio contratado: " + nombre;
    }

    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws IOException {
        return transportePrivado.calcularDistancia(origen,destino);
    }

    public Double getConsumoPorKM() {
        return transportePrivado.getConsumoPorKM();
    }

    public Double calcularHC(Distancia distancia) {
        return transportePrivado.calcularHC(distancia);
    }

}
