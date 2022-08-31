package domain.organizaciones;


import domain.CargaDeDatos.entidades.Periodo;
import domain.geoDDS.entidades.Municipio;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "agente_sectorial")
public class AgenteSectorial {
    @Id
    @GeneratedValue
    private int id;

    @Transient
    private Municipio municipio;

    @Transient //hacer que en Municipio este el agente?
    List<Organizacion> organizaciones;

    public AgenteSectorial() {
    }

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
