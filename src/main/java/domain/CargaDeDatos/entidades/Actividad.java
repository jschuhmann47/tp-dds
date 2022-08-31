package domain.CargaDeDatos.entidades;

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
    public Periodo periodo; //ponerlo aca

    @Enumerated(value = EnumType.STRING)
    public Periodicidad periodicidad;

    @Column(name = "valor")
    public Double valor;




    public Actividad() {

    }


    public Actividad(TipoActividad tipoActividad, TipoDeConsumo tipoDeConsumo, Unidad unidad, Periodo periodo, Periodicidad periodicidad, Double valor) {
        this.tipoActividad = tipoActividad;
        this.tipoDeConsumo = tipoDeConsumo;
        this.unidad = unidad;
        this.periodo = periodo;
        this.periodicidad = periodicidad;
        this.valor = valor;
    }
}
