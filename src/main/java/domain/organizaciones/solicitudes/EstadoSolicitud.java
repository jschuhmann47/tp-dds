package domain.organizaciones.solicitudes;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class EstadoSolicitud {
    @Setter
    @Getter
    @Column(name = "estado_solicitud")
    private PosibleEstadoSolicitud estadoSolicitud;
    @Setter
    @Getter
    @Column(name = "fecha_ultima_modificacion")
    private LocalDate fechaUltimaModificacion;
}
