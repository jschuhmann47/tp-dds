package models.entities.transporte.privado;

import lombok.Getter;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.ServicioCalcularDistancia;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.trayectos.TramoCompartido;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.transporte.MedioTransporte;
import models.entities.transporte.TipoCombustible;

import javax.persistence.*;
import java.io.IOException;


@Entity
@DiscriminatorValue("transporte_privado")
@Embeddable
public class TransportePrivado extends MedioTransporte {
    @Getter
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "tramo_compartido_id",referencedColumnName = "id")
    TramoCompartido tramoCompartido;

//    @Transient
//    @Getter
//    private final String tipoTransporte = "transporte_privado";

    public TransportePrivado(){
        this.tipoTransporte = "transporte_privado";

    }

    public TransportePrivado(TipoVehiculo tipo, TipoCombustible tipoCombustible) {
        this.tipo = tipo;
        this.tipoCombustible = tipoCombustible;
        this.tramoCompartido = new TramoCompartido();
        this.tipoTransporte = "transporte_privado";
    }

    public TransportePrivado(TipoVehiculo tipo, TipoCombustible tipoCombustible,TramoCompartido tramoCompartido) {
        this.tipo = tipo;
        this.tipoCombustible = tipoCombustible;
        this.tramoCompartido = tramoCompartido;
        this.tipoTransporte = "transporte_privado";
    }


    public String detalle() {
        return "Tipo de vehiculo:" + this.tipo.toString() + " - " + "Tipo de combustible: " + this.tipoCombustible.toString();
    }


    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws IOException {
        return ServicioCalcularDistancia.distanciaEntre(origen, destino);
    }



    public Double calcularHC(Distancia distancia) {
        return this.getConsumoPorKM() * distancia.getValor() / tramoCompartido.cantidadDeTrabajadores();
    }

    public void agregarTrabajadorATramoCompartido(Trabajador trabajador) throws Exception {
        tramoCompartido.agregarTrabajadorATramoCompartido(trabajador);
    }

}
