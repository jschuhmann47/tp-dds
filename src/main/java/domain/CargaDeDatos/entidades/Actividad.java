package domain.CargaDeDatos.entidades;

import javax.persistence.*;

@Entity
@Table(name = "actividad")
public class Actividad {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "tipo_actividad")
    public TipoActividad tipoActividad;

    @Column(name = "tipo_consumo")
    public TipoDeConsumo tipoDeConsumo;

    @Column(name = "unidad")
    public Unidad unidad;

    @Transient
    public Periodo periodo; //ponerlo aca

    @Column(name = "periodicidad")
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
