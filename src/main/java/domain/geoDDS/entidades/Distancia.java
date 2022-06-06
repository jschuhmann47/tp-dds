package domain.geoDDS.entidades;

public class Distancia {
    public String valor;
    public String unidad;

    public Distancia() {
    }

    public Distancia(String valor, String unidad) {
        this.valor = valor;
        this.unidad = unidad;
    }

    public Double convertirValor(){
        return Double.parseDouble(this.valor);
    }
}
