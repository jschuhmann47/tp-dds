package domain.trayectos;

import domain.CargaDeActividades.entidades.Periodicidad;
import domain.CargaDeActividades.entidades.Periodo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Frecuencia {
    public Frecuencia() {
    }

    public Frecuencia(Periodicidad frecuenciaDeUso, Integer vecesQueRealizaTrayectoPorMes) {
        this.frecuenciaDeUso = frecuenciaDeUso;
        this.vecesQueRealizaTrayectoPorMes = vecesQueRealizaTrayectoPorMes;
    }

    @Column(name = "frecuencia_de_uso")
    Periodicidad frecuenciaDeUso;

    @Column(name = "veces_por_mes")
    Integer vecesQueRealizaTrayectoPorMes;

    Integer vecesPorMes(Periodo periodo){
        if(periodo.getMes()==null){
            return vecesQueRealizaTrayectoPorMes*12;
        }
        return vecesQueRealizaTrayectoPorMes;
    }
}
