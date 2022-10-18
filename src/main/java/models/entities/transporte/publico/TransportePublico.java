package models.entities.transporte.publico;

import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.transporte.MedioTransporte;
import models.entities.transporte.TipoCombustible;
import models.entities.transporte.privado.TipoVehiculo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
@DiscriminatorValue("transporte_publico")
public class TransportePublico extends MedioTransporte {

    @OneToOne
    @JoinColumn(name = "linea_id",referencedColumnName = "id")
    private Linea linea;


    public TransportePublico() {
    }

    public TransportePublico(Linea linea, TipoVehiculo tipo, TipoCombustible tipoCombustible) {
        this.linea = linea;
        this.tipo = tipo;
        this.tipoCombustible = tipoCombustible;
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
