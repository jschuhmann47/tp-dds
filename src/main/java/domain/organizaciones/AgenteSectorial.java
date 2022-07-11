package domain.organizaciones;


import domain.geoDDS.entidades.Municipio;


import java.util.ArrayList;
import java.util.List;

public class AgenteSectorial {

    private Municipio municipio;
    List<Organizacion> organizaciones;

    public AgenteSectorial(Municipio municipio, List<Organizacion> organizaciones){
        this.municipio = municipio;
        this.organizaciones = organizaciones;
    }

    public Double calcularHCEnMes(Integer mes, Integer anio) {
        
        return organizaciones.stream()
                .filter(org -> org.getUbicacion().getMunicipio().getId() == this.municipio.getId())
                .mapToDouble(org -> {
                    try {
                        return org.calcularHCEnMes(mes, anio);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }

    public Double calcularHCEnAnio(Integer anio) {

        return organizaciones.stream()
                .filter(org -> org.getUbicacion().getMunicipio().getId() == this.municipio.getId())
                .mapToDouble(org -> {
                    try {
                        return org.calcularHCEnAnio(anio);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }
}
