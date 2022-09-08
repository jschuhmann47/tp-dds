package domain.transporte.privado;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.io.IOException;


@Entity
@DiscriminatorValue("servicio_contratado")
public class ServicioContratado extends MedioTransporte {

    @Embedded
    private TransportePrivado transportePrivado;
    @Column(name = "servicio_contratado_nombre",nullable = false)
    private String nombre;

    public ServicioContratado() {
    }

    public ServicioContratado(TransportePrivado transportePrivado, String nombre) {
        this.transportePrivado = transportePrivado;
        this.nombre = nombre;
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
