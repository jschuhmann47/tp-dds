package domain.organizaciones.entidades;


import domain.CargaDeActividades.entidades.Periodo;
import domain.geoDDS.entidades.Municipio;
import domain.reportes.GeneradorReporte;
import domain.reportes.Reporte;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "agente_sectorial")
public class AgenteSectorial {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id",referencedColumnName = "id")
    private Municipio municipio;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion")
    List<Organizacion> organizaciones;

    public AgenteSectorial() {
    }

    public AgenteSectorial(Municipio municipio, List<Organizacion> organizaciones){
        this.municipio = municipio;
        this.organizaciones = organizaciones;
    }

    public Reporte calcularHCEnAnio(Periodo periodo) {
        return GeneradorReporte.HCTotalPorSectorTerritorialEnPeriodo(organizaciones,municipio,periodo);
    }
}
