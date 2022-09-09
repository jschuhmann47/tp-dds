package db;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Localidad;
import domain.geoDDS.entidades.Municipio;
import domain.geoDDS.entidades.Pais;
import domain.geoDDS.entidades.Provincia;
import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.Trabajador;
import org.junit.After;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class PersistenciaTest {

    @Test
    @DisplayName("Se persiste una organizacion")


    public void organizacionPersistir(){
        List<Sector> sectores = new ArrayList<>();
        Sector marketing = new Sector();
        marketing.nombreSector = "Marketing";
        sectores.add(marketing);
        List<String> clasificaciones = new ArrayList<>();
        clasificaciones.add("Videojuegos");
        clasificaciones.add("Desarrollador");

        Trabajador juan = new Trabajador();

        List<Trabajador> trabajadoresA = new ArrayList<>();
        trabajadoresA.add(juan);

        Pais pais = new Pais(1,"A");
        Provincia provincia = new Provincia(1,"A",pais);
        Municipio municipio = new Municipio(1,"A",provincia);

        Localidad localidad = new Localidad(1,"A",1,municipio);


        Direccion direccion1 = new Direccion(100,"Rivadavia",localidad);

        Organizacion organizacion = new Organizacion(clasificaciones,trabajadoresA,
                "Valve Corporation S.A",sectores, TipoOrganizacion.EMPRESA,direccion1);

        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(pais);
        EntityManagerHelper.getEntityManager().persist(provincia);
        EntityManagerHelper.getEntityManager().persist(municipio);
        EntityManagerHelper.getEntityManager().persist(localidad);
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
