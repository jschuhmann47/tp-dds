package models.entities.organizaciones.solicitudes;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Embeddable
public class EstadoSolicitud {
    @Setter
    @Getter
    @Enumerated(value = EnumType.STRING)
    @Column(name = "estado_solicitud")
    private PosibleEstadoSolicitud estadoSolicitud;
    @Setter
    @Getter
    @Column(name = "fecha_ultima_modificacion",columnDefinition = "DATE")
    private LocalDate fechaUltimaModificacion;
}
