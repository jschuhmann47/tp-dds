package domain.transporte.publico;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.MedioTransporte;

public class TransportePublico implements MedioTransporte {

    private Linea linea;
    private TipoTransportePublico tipo;

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

    public Linea getLinea() {
        return linea;
    }
    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
