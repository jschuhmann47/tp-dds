package models.entities.transporte.publico;

import lombok.Getter;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.transporte.MedioTransporte;
import models.entities.transporte.TipoCombustible;
import models.entities.transporte.privado.TipoVehiculo;

import javax.persistence.*;


@Entity
@DiscriminatorValue("transporte_publico")
public class TransportePublico extends MedioTransporte {

    @OneToOne
    @JoinColumn(name = "linea_id",referencedColumnName = "id")
    private Linea linea;

//    @Transient
//    @Getter
//    private final String tipoTransporte = "transporte_publico";

    public TransportePublico() {
        this.tipoTransporte = "transporte_publico";
    }

    public TransportePublico(Linea linea, TipoVehiculo tipo, TipoCombustible tipoCombustible) {
        this.linea = linea;
        this.tipo = tipo;
        this.tipoCombustible = tipoCombustible;
        this.tipoTransporte = "transporte_publico";
    }

    public String detalle() {
        return  "Tipo: " + tipo.toString() + " - " + "Parada incial: " + linea.detallePrimerParada()
                + " - " + "Parada final: " + linea.detalleUltimaParada() +  "Linea: " + linea.getNombreDeLinea();
    }


    public Distancia calcularDistancia(Direccion origen, Direccion destino) throws Exception {
        return linea.calcularDistancia(origen, destino);

    }

    public Double calcularHC(Distancia distancia) {
        return this.getConsumoPorKM() * distancia.valor;
    }

    public Linea getLinea() {
        return linea;
    }
    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
