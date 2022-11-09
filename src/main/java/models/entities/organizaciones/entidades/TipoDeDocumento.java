package models.entities.organizaciones.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tipo_documento")
public class TipoDeDocumento {
    @Getter
    @Id
    @GeneratedValue
    private int id;

    @Getter
    @Enumerated(value = EnumType.STRING)
    @Column(name = "posible_tipo_documento")
    private PosibleTipoDocumento posibleTipoDocumento;

    public TipoDeDocumento(){

    }


    public TipoDeDocumento(PosibleTipoDocumento posibleTipoDocumento) {
        this.posibleTipoDocumento = posibleTipoDocumento;
    }
}
