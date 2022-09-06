package domain.transporte.privado;

import domain.geoDDS.Direccion;
import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.entidades.Distancia;
import domain.trayectos.TramoCompartido;
import domain.organizaciones.Trabajador;
import domain.transporte.CalcularHCTransporte;
import domain.transporte.MedioTransporte;
import domain.transporte.TipoCombustible;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.IOException;


@Entity
@DiscriminatorValue("transporte_privado")
public class TransportePrivado extends MedioTransporte {
    @Transient
    TramoCompartido tramoCompartido;

    public TransportePrivado() {
    }

    public TransportePrivado(TipoVehiculo tipo, TipoCombustible tipoCombustible) {
        this.tipo = tipo;
        this.tipoCombustible = tipoCombustible;
        this.tramoCompartido = new TramoCompartido();
    }


    public String detalle() {
        return "Tipo de vehiculo:" + tipo.toString() + " - " + "Tipo de combustible: " + tipoCombustible.toString();
    }


    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws IOException {
        return ServicioCalcularDistancia.distanciaEntre(origen, destino);
    }

    public Double getConsumoPorKM(){
        return CalcularHCTransporte.getConsumosPorKm().get(this.tipo);
    }

    public Double calcularHC(Distancia distancia) {
        return this.getConsumoPorKM() * distancia.valor / tramoCompartido.cantidadDeTrabajadores();
    }

    public void agregarTrabajadorATramoCompartido(Trabajador trabajador) throws Exception {
        tramoCompartido.agregarTrabajadorATramoCompartido(trabajador);
    }

}
