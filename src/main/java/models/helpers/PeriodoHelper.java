package models.helpers;

import models.entities.CargaDeActividades.entidades.Periodo;

import java.time.LocalDate;

public class PeriodoHelper {

    public static Periodo nuevoPeriodo(String mes, String anio){
        int mesNum = Integer.parseInt(mes), anioNum = Integer.parseInt(anio);
        if(!PeriodoHelper.mesEntreUnoYDoce(mesNum) || anioNum > LocalDate.now().getYear()){
            throw new RuntimeException("Periodo invalido");
        }
        return new Periodo(mesNum,anioNum);
    }

    private static Boolean mesEntreUnoYDoce(Integer mes){
        return mes >= 1 && mes <= 12;
    }
}
