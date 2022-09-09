package domain.CargaDeActividades.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Periodo {
    @Getter
    @Setter
    @Column(name = "mes",nullable = true)
    Integer mes;
    @Getter
    @Setter
    @Column(name = "anio")
    Integer anio;

    public Periodo() {
    }

    public Periodo(Integer mes, Integer anio) {
        this.mes = mes;
        this.anio = anio;
    }

    public Periodo obtenerPeriodoAnterior(){
        Periodo periodoAnterior = new Periodo();

        if(this.getMes()==null){
            periodoAnterior.setMes(null);
            periodoAnterior.setAnio(this.getAnio()-1);
            return periodoAnterior;
        }

        if(this.getMes()-1 == 0){
            periodoAnterior.setMes(12);
            periodoAnterior.setAnio(this.getAnio()-1);
        }
        else{
            periodoAnterior.setMes(this.getMes()-1);
            periodoAnterior.setAnio(this.getAnio());
        }
        return periodoAnterior;

    }

}
