package domain.transporte;

public class TransportePublico implements MedioTransporte{

    private Linea linea;

    public TransportePublico(Linea linea) {
        this.linea = linea;
    }

    public void detalle() {} //TODO que hace detalle???

    public void calcularDistancia() {} //TODO falta el calculo de distancia, puede que sea int


    public Linea getLinea() {
        return linea;
    }
    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
