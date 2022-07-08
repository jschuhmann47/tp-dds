package domain.transporte.publico;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.CalcularHCTransporte;
import domain.transporte.MedioTransporte;
import domain.transporte.TipoCombustible;
import domain.transporte.privado.TipoVehiculo;

public class TransportePublico implements MedioTransporte {

    private Linea linea;
    private TipoVehiculo tipo;
    private TipoCombustible tipoCombustible;

    public TransportePublico(Linea linea, TipoVehiculo tipo, TipoCombustible tipoCombustible) {
        this.linea = linea;
        this.tipo = tipo;
        this.tipoCombustible = tipoCombustible;
    }

    public String detalle() {
        return  "Tipo: " + tipo.toString() + " - " + "Parada incial: " + linea.detallePrimerParada()
                + " - " + "Parada final: " + linea.detalleUltimaParada() +  "Linea: " + linea.getNombreDeLinea();
    }


    public Distancia calcularDistancia(Direccion origen,Direccion destino) throws Exception { //todo cambiar en el uml
        return linea.calcularDistancia(origen, destino);

    }


    public Double getConsumoPorKM() {
        return CalcularHCTransporte.getConsumosPorKm().get(this.tipo);
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
