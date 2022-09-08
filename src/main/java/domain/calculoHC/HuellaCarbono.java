package domain.calculoHC;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Embeddable
public class HuellaCarbono {
    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column(name = "huella_carbono_unidad",nullable = false)
    @Getter
    private UnidadHC unidad;
    @Setter
    @Column(name = "huella_carbono_valor",nullable = false)
    @Getter
    private double valor;
}
