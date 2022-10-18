package models.entities.trayectos;

import models.entities.CargaDeActividades.entidades.Periodicidad;
import models.entities.CargaDeActividades.entidades.Periodo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Frecuencia {
    public Frecuencia() {
    }

    public Frecuencia(Periodicidad frecuenciaDeUso, Integer vecesQueRealizaTrayectoPorMes) {
        this.frecuenciaDeUso = frecuenciaDeUso;
        this.vecesQueRealizaTrayectoPorMes = vecesQueRealizaTrayectoPorMes;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "frecuencia_de_uso",nullable = false)
    Periodicidad frecuenciaDeUso;

    @Column(name = "veces_por_mes",nullable = false)
    Integer vecesQueRealizaTrayectoPorMes;

    Integer vecesPorMes(Periodo periodo){
        if(periodo.getMes()==null){
            return vecesQueRealizaTrayectoPorMes*12;
        }
        return vecesQueRealizaTrayectoPorMes;
    }
}
