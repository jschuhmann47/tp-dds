package domain.organizaciones.solicitudes;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class EstadoSolicitud {
    @Setter
    @Getter
    private PosibleEstadoSolicitud estadoSolicitud;
    @Setter
    @Getter
    private LocalDate fechaUltimaModificacion;
}
