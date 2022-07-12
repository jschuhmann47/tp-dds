package domain.trayectos;

import domain.CargaDeDatos.entidades.Periodicidad;
import domain.CargaDeDatos.entidades.Periodo;

public class Frecuencia {
    public Frecuencia(Periodicidad frecuenciaDeUso, Integer vecesQueRealizaTrayectoPorMes) {
        this.frecuenciaDeUso = frecuenciaDeUso;
        this.vecesQueRealizaTrayectoPorMes = vecesQueRealizaTrayectoPorMes;
    }

    Periodicidad frecuenciaDeUso;
    Integer vecesQueRealizaTrayectoPorMes;

    Integer vecesPorMes(Periodo periodo){
        if(periodo.getMes()==null){
            return vecesQueRealizaTrayectoPorMes*12;
        }
        return vecesQueRealizaTrayectoPorMes;
    }
}
