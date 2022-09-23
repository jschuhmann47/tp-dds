package models.entities.transporte;

import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.transporte.privado.TipoVehiculo;

import javax.persistence.*;


@Entity
@Table(name = "medio_transporte")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "medio_tipo")
public abstract class MedioTransporte {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "tipo_vehiculo",nullable = false)
    protected TipoVehiculo tipo;
    @Column(name = "tipo_combustible",nullable = false)
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
