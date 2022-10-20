package test.domain.organizaciones;

import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.organizaciones.solicitudes.PosibleEstadoSolicitud;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SolicitudTest {

    @Test
    @DisplayName("Se agrega una solicitud y se acepta")
    public void solicitudAceptada(){
        Organizacion organizacion = new Organizacion(null, null,new ArrayList<>(),null,null);
        Sector s = new Sector(organizacion,"sistemas",new ArrayList<>());
        organizacion.agregarNuevoSector(s);
        Trabajador t = new Trabajador();

        t.solicitarVinculacion(organizacion,s);

        s.getSolicitudes().get(0).aceptarSolicitud();

        Assertions.assertEquals(1,s.getSolicitudes().size());
        Assertions.assertEquals(1,t.getListaDeSolicitudes().size());
        Assertions.assertEquals(PosibleEstadoSolicitud.ACEPTADO, s.getSolicitudes().get(0).getEstadoSolicitud().getPosibleEstadoSolicitud());
        Assertions.assertTrue(s.getTrabajadores().contains(t));
        Assertions.assertTrue(t.getSectores().contains(s));

    }

    @Test
    @DisplayName("Se agrega una solicitud y se rechaza")
    public void solicitudRechazada(){
        Organizacion organizacion = new Organizacion(null, null,new ArrayList<>(),null,null);
        Sector s = new Sector(organizacion,"sistemas",new ArrayList<>());
        organizacion.agregarNuevoSector(s);
        Trabajador t = new Trabajador();

        t.solicitarVinculacion(organizacion,s);

        s.getSolicitudes().get(0).rechazarSolicitud();

        Assertions.assertEquals(1,s.getSolicitudes().size());
        Assertions.assertEquals(1,t.getListaDeSolicitudes().size());
        Assertions.assertEquals(PosibleEstadoSolicitud.RECHAZADO, s.getSolicitudes().get(0).getEstadoSolicitud().getPosibleEstadoSolicitud());
        Assertions.assertFalse(s.getTrabajadores().contains(t));
        Assertions.assertFalse(t.getSectores().contains(s));

    }
}
