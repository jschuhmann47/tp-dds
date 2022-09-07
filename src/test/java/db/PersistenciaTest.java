package db;

import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.organizaciones.TipoOrganizacion;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static db.EntityManagerHelper.entityManager;
import static db.EntityManagerHelper.withTransaction;

public class PersistenciaTest {

    @Test
    @DisplayName("Se persiste una organizacion")



    public void organizacionPersistir(){
        List<Sector> sectores = new ArrayList<>();
        Sector marketing = new Sector();
        marketing.nombreSector = "Marketing";
        sectores.add(marketing);
        Organizacion organizacion = new Organizacion(null,null,
                "Valve Corporation S.A",sectores, TipoOrganizacion.EMPRESA,null);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(organizacion);
        EntityManagerHelper.commit();
    }

    @Test
    @DisplayName("Se recupera una organizacion")
    public void organizacionRecuperar(){
        Organizacion org = (Organizacion) EntityManagerHelper
                .createQuery("FROM Organizacion WHERE razon_social = 'Valve Corporation S.A'").getSingleResult();
        Assertions.assertEquals("Valve Corporation S.A", org.getRazonSocial());
    }


}
