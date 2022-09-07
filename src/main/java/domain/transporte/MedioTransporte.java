package domain.transporte;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import domain.transporte.privado.TipoVehiculo;

import javax.persistence.*;
import java.io.IOException;


@Entity
@Table(name = "medio_transporte")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "medio_tipo")
public abstract class MedioTransporte {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "tipo_vehiculo")
    protected TipoVehiculo tipo;
    @Column(name = "tipo_combustible")
    @Enumerated(EnumType.STRING)
    protected TipoCombustible tipoCombustible;

    public int getId() {
        return id;
    }

    public abstract String detalle();

    public abstract Distancia calcularDistancia(Direccion origen, Direccion destino) throws Exception;
    public abstract Double getConsumoPorKM();

    public abstract Double calcularHC(Distancia distancia);
}
