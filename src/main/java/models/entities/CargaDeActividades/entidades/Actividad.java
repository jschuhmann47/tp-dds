package models.entities.CargaDeActividades.entidades;

import models.entities.calculoHC.HuellaCarbono;
import models.entities.calculoHC.UnidadHC;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "actividad")
@Getter
public class Actividad {

    @Id
    @GeneratedValue
    private int id;

    @Enumerated(value = EnumType.STRING)
    public TipoActividad tipoActividad;

    @Enumerated(value = EnumType.STRING)
    public TipoDeConsumo tipoDeConsumo;

    @Enumerated(value = EnumType.STRING)
    public Unidad unidad;

    @Embedded
    public Periodo periodo;

    @Enumerated(value = EnumType.STRING)
    public Periodicidad periodicidad;

    @Column(name = "valor")
    public Double valor;

    @Embedded
    private final HuellaCarbono huellaCarbono = new HuellaCarbono();


    public void setHuellaCarbono(UnidadHC unidad, Double valor){
        this.huellaCarbono.setUnidad(unidad);
        this.huellaCarbono.setValor(valor);
    }


    public Actividad() {

    }


    public Actividad(TipoActividad tipoActividad, TipoDeConsumo tipoDeConsumo, Unidad unidad, Periodo periodo,
                     Periodicidad periodicidad, Double valor) {
        this.tipoActividad = tipoActividad;
        this.tipoDeConsumo = tipoDeConsumo;
        this.unidad = unidad;
        this.periodo = periodo;
        this.periodicidad = periodicidad;
        this.valor = valor;
    }
}
