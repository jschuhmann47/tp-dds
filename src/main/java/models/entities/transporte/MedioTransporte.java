package models.entities.transporte;

import lombok.Getter;
import models.entities.calculoHC.CalculoHC;
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

    @Getter
    @Column(name = "nombre")
    protected String nombre;

    @Getter
    @Column(name = "tipo_vehiculo",nullable = false)
    protected TipoVehiculo tipo;

    @Getter
    @Column(name = "tipo_combustible",nullable = false)
    @Enumerated(EnumType.STRING)
    protected TipoCombustible tipoCombustible;

    public int getId() {
        return id;
    }

    public abstract String detalle();

    public abstract Distancia calcularDistancia(Direccion origen, Direccion destino) throws Exception;
    public Double getConsumoPorKM(){
        return CalculoHC.getFactorEmision(this.getTipo().toString()).getValor();
    }

    public abstract Double calcularHC(Distancia distancia);
}
