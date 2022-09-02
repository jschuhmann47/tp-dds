package db;

import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.organizaciones.TipoOrganizacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersistenciaTest {

    @Test
    @DisplayName("Se persiste una organizacion")
    public void organizacionPersistir(){
        List<Sector> sectores = new ArrayList<>();
        Sector marketing = new Sector();
        marketing.nombreSector = "Marketing";
        sectores.add(marketing);
        Organizacion organizacion = new Organizacion("Osde",Collections.singletonList("Salud"),null,
                "razonSocial",sectores, TipoOrganizacion.EMPRESA,null);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(organizacion);
        EntityManagerHelper.commit();
    }

    @Test
    @DisplayName("Se recupera una organizacion")
    public void organizacionRecuperar(){
        Organizacion org = (Organizacion) EntityManagerHelper.createQuery("from Organizacion where nombre = 'Osde'").getSingleResult();
        Assertions.assertEquals("Osde", org.getNombre());
    }


}
