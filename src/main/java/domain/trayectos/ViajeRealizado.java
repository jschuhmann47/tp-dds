package domain.trayectos;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "viaje_realizado")
public class ViajeRealizado {
    @Id
    @GeneratedValue
    private int id;
    
    @Getter
    private Integer mes;
    @Getter
    private Integer anio;
    @Getter
    private Integer vecesRealizadaEnMes;

    public ViajeRealizado(Integer mes, Integer anio) {
        this.mes = mes;
        this.anio = anio;
        this.vecesRealizadaEnMes = 0;
    }

    public ViajeRealizado() {
        this.vecesRealizadaEnMes = 0;
    }

    public void sumarViajes(int cantidad){
        this.vecesRealizadaEnMes+=cantidad;
    }

}
