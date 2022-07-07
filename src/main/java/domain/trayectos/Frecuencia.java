package domain.trayectos;

public class Frecuencia {
    public Frecuencia(FrecuenciaDeUso frecuenciaDeUso, Integer diasQueHaceElTrayecto) {
        this.frecuenciaDeUso = frecuenciaDeUso;
        this.diasQueHaceElTrayecto = diasQueHaceElTrayecto;
    }

    FrecuenciaDeUso frecuenciaDeUso;
    Integer diasQueHaceElTrayecto;

    Integer vecesPorMes(){
        if(this.frecuenciaDeUso==FrecuenciaDeUso.SEMANAL){
            return diasQueHaceElTrayecto*4;
        }
        if(this.frecuenciaDeUso==FrecuenciaDeUso.MENSUAL){
            return diasQueHaceElTrayecto;
        }
        return null;
    }
}
