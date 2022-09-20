package domain.reportes;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class Reporte {
    private String descripcion;
    private Double valor;
    private LocalDate fecha;

    public Reporte(String descripcion, Double valor) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.fecha = LocalDate.now();

    }

    public void agregarProvinciaADescripcion(String provincia){
        descripcion = "Provincia: " + provincia + ". Descripcion: " + this.getDescripcion();
    }

    public String generarLeyenda(){
        return "Descripcion: " + this.getDescripcion() + "\n" + "Valor: " + this.getValor();
    }
}
