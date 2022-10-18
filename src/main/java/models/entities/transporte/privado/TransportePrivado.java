package models.entities.transporte.privado;

import lombok.Getter;
import models.entities.calculoHC.CalculoHC;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.ServicioCalcularDistancia;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.trayectos.TramoCompartido;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.calculoHC.CalcularHCTransporte;
import models.entities.transporte.MedioTransporte;
import models.entities.transporte.TipoCombustible;

import javax.persistence.*;
import java.io.IOException;


@Entity
@DiscriminatorValue("transporte_privado")
@Embeddable
public class TransportePrivado extends MedioTransporte {
    @Getter
    @OneToOne(mappedBy = "transportePrivado",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    TramoCompartido tramoCompartido;

    public TransportePrivado() {

    }

    public TransportePrivado(TipoVehiculo tipo, TipoCombustible tipoCombustible) {
        this.tipo = tipo;
        this.tipoCombustible = tipoCombustible;
        this.tramoCompartido = new TramoCompartido();
    }

    public TransportePrivado(TipoVehiculo tipo, TipoCombustible tipoCombustible,TramoCompartido tramoCompartido) {
        this.tipo = tipo;
        this.tipoCombustible = tipoCombustible;
        this.tramoCompartido = tramoCompartido;
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
