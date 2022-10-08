package models.entities.organizaciones.entidades;


import lombok.Getter;
import models.entities.CargaDeActividades.entidades.Periodo;
import models.entities.geoDDS.entidades.Municipio;
import models.entities.reportes.GeneradorReporte;
import models.entities.reportes.Reporte;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "agente_sectorial")
public class AgenteSectorial {
    @Getter
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "apellido",nullable = false)
    private String apellido;
    @Column(name = "nombre",nullable = false)
    private String nombre;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id",referencedColumnName = "id")
    private Municipio municipio;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "agente_sectorial_id",referencedColumnName = "id")
    List<Organizacion> organizaciones;

    public AgenteSectorial() {
    }

    public AgenteSectorial(String apellido, String nombre, Municipio municipio, List<Organizacion> organizaciones) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.municipio = municipio;
        this.organizaciones = organizaciones;
    }

    public Reporte calcularHCEnAnio(Periodo periodo) {
        return GeneradorReporte.HCTotalPorSectorTerritorialEnPeriodo(organizaciones,municipio,periodo);
    }
}
