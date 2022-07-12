package domain.organizaciones;


import domain.CargaDeDatos.entidades.Periodo;
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

    public Double calcularHCEnAnio(Periodo periodo) {

        return organizaciones.stream()
                .filter(org -> org.getUbicacion().getMunicipio().getId() == this.municipio.getId())
                .mapToDouble(org -> {
                    try {
                        return org.calcularHC(periodo);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }
}
