package domain.transporte.publico;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.CalcularHCTransporte;
import domain.transporte.MedioTransporte;
import domain.transporte.TipoCombustible;

public class TransportePublico implements MedioTransporte {

    private Linea linea;
    private TipoTransportePublico tipo; //unificar con la de tipoVehiculo
    private TipoCombustible tipoCombustible;

    public TransportePublico(Linea linea) {
        this.linea = linea;
    }

    public String detalle() {
        return  "Tipo: " + tipo.toString() + " - " + "Parada incial: " + linea.detallePrimerParada()
                + " - " + "Parada final: " + linea.detalleUltimaParada() +  "Linea: " + linea.getNombreDeLinea();
    }


    public Distancia calcularDistancia(Direccion origen,Direccion destino) throws Exception { //todo cambiar en el uml
        return linea.calcularDistancia(origen, destino);

    }


    public Double calcularHC() {
        return null;
    }


    public Double getConsumoPorKM() {
        return CalcularHCTransporte.getConsumosPorKm().get(this.tipo); //fix TODO
    }

    public Linea getLinea() {
        return linea;
    }
    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
