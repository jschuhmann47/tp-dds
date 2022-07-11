package domain.organizaciones;

import domain.geoDDS.entidades.Municipio;


import java.util.ArrayList;
import java.util.List;

public class AgenteSectorial {

    //private Municipio municipio;

    public Double calcularHCEnMes(Integer mes, Integer anio) {

        List<String> organizaciones = new ArrayList<>(); //habria que instanciar una lista global ??
        
        return organizaciones.stream()
                .filter(org -> org.ubicacion.getMunicipio().getId() == this.municipio.getId())
                .mapToDouble(org -> org.calcularHCEnMes(mes, anio))
                .sum();
    }

    public Double calcularHCEnAnio(Integer anio) {

        List<String> organizaciones = new ArrayList<>(); //habria que instanciar una lista global ??

        return organizaciones.stream()
                .filter(org -> org.ubicacion.getMunicipio().getId() == this.municipio.getId())
                .mapToDouble(org -> org.calcularHCEnMes(anio))
                .sum();
    }
}
