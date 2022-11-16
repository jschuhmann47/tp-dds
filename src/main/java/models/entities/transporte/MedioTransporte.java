package models.entities.transporte;

import lombok.Getter;
import lombok.Setter;
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
    @Getter
    private int id;

    @Getter
    @Setter
    @Column(name = "nombre")
    protected String nombre;

    @Getter
    @Column(name = "tipo_transporte")
    protected String tipoTransporte;

    @Getter
    @Column(name = "tipo_vehiculo",nullable = false)
    @Enumerated(value = EnumType.STRING)
    protected TipoVehiculo tipo;

    @Getter
    @Column(name = "tipo_combustible",nullable = false)
    @Enumerated(EnumType.STRING)
    protected TipoCombustible tipoCombustible;

    public abstract String detalle();

    public abstract Distancia calcularDistancia(Direccion origen, Direccion destino) throws Exception;
    public Double getConsumoPorKM(){
        return CalculoHC.getFactorEmision(this.getTipo().toString()).getValor();
    }

    public abstract Double calcularHC(Distancia distancia);
}
