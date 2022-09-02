package domain.CargaDeDatos.entidades;

import domain.calculoHC.HuellaCarbono;
import domain.calculoHC.UnidadHC;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "actividad")
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
    @Getter
    private HuellaCarbono huellaCarbono = new HuellaCarbono();


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
