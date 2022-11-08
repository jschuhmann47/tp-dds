package models.entities.calculoHC;

import models.entities.CargaDeActividades.entidades.Actividad;
import models.entities.CargaDeActividades.entidades.Periodo;
import lombok.Getter;
import lombok.Setter;
import models.entities.parametros.ParametroFE;

import java.util.*;
import java.util.stream.Collectors;

public class CalculoHC {

    @Getter
    @Setter
    static UnidadHC unidadPorDefecto;

    public static String getUnidadPorDefectoString(){
        switch (CalculoHC.getUnidadPorDefecto()){
            case GRAMO_EQ:
                return "g CO2eq";
            case KILOGRAMO_EQ:
                return "kg CO2eq";
            case TONELADA_EQ:
                return "tn CO2eq";
            default:
                return null;
        }
    }

    @Getter
    @Setter
    static List<ParametroFE> factoresEmisionFE;


    public static ParametroFE getFactorEmision(String nombre){
        List<ParametroFE> posibleFactor = factoresEmisionFE.stream().filter(f -> Objects.equals(f.getNombre(), nombre)).collect(Collectors.toList());
        if(posibleFactor.isEmpty()){
            throw new RuntimeException("No existe el factor de emision");
        }else{
            return posibleFactor.get(0);
        }
    }

    public static void calcularHCDeActividad(Actividad actividad){
        Double valorAct = actividad.getValor() * CalculoHC.getFactorEmision(actividad.getTipoDeConsumo().toString()).getValor();
        //CORRESPONDENCIA EN LA BASE CON EL ENUM? SI PARA MANTENER ESTANDAR CON EL EXCEL
        actividad.setHuellaCarbono(CalculoHC.getUnidadPorDefecto(), valorAct);
    }


    public static Double calcularHCDeListaDeActividadesEnPeriodo(List<Actividad> actividades, Periodo periodo) {

        List<Actividad> listaHC = actividades.stream()
                .filter(a-> Objects.equals(a.getPeriodo().getAnio(), periodo.getAnio())).collect(Collectors.toList());
        if(periodo.getMes()!=null){
            listaHC=listaHC.stream().filter(a->Objects.equals(a.getPeriodo().getMes(), periodo.getMes())).collect(Collectors.toList());
        }
        return sumarListaHC(listaHC.stream().map(a->a.getHuellaCarbono().getValor()).collect(Collectors.toList()));
    }

    public static Double calcularHCDeListaDeActividadesTotal(List<Actividad> actividades){
        return actividades.stream().mapToDouble(actividad -> actividad.getHuellaCarbono().getValor()).sum();
    }


    private static Double sumarListaHC(List<Double> listaHC){
        return listaHC.stream().mapToDouble(t->t).sum();
    }

}
