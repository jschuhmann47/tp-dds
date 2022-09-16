package domain.reportes;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Reporte {
    private String descripcion;
    private Double valor;

    public Reporte(String descripcion, Double valor) {
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public void agregarProvinciaADescripcion(String provincia){
        descripcion = "Provincia: " + provincia + ". Descripcion: " + this.getDescripcion();
    }

    public String generarLeyenda(){
        return "Descripcion: " + this.getDescripcion() + "\n" + "Valor: " + this.getValor();
    }
}
