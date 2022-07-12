package domain.trayectos;

public class Frecuencia {
    public Frecuencia(FrecuenciaDeUso frecuenciaDeUso, Integer cantDiasQueHaceElTrayecto) {
        this.frecuenciaDeUso = frecuenciaDeUso;
        this.cantDiasQueHaceElTrayecto = cantDiasQueHaceElTrayecto;
    }

    FrecuenciaDeUso frecuenciaDeUso;
    Integer cantDiasQueHaceElTrayecto;

    Integer vecesPorMes(){
        if(this.frecuenciaDeUso==FrecuenciaDeUso.SEMANAL){
            return cantDiasQueHaceElTrayecto *4;
        }
        if(this.frecuenciaDeUso==FrecuenciaDeUso.MENSUAL){
            return cantDiasQueHaceElTrayecto;
        }
        return null;
    }
}
