package db;

import models.entities.CargaDeActividades.entidades.*;
import models.entities.calculoHC.CalculoHC;
import models.entities.calculoHC.UnidadHC;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.ServicioCalcularDistancia;
import models.entities.geoDDS.adapters.ServicioGeoDDSAdapter;
import models.entities.geoDDS.entidades.*;
import models.entities.organizaciones.entidades.*;
import models.entities.organizaciones.solicitudes.Solicitud;
import models.entities.parametros.ParametroFE;
import models.entities.seguridad.cuentas.Permiso;
import models.entities.seguridad.cuentas.Rol;
import models.entities.seguridad.cuentas.TipoRecurso;
import models.entities.seguridad.cuentas.Usuario;
import models.entities.calculoHC.CalcularHCTransporte;
import models.entities.transporte.TipoCombustible;
import models.entities.transporte.privado.TipoVehiculo;
import models.entities.transporte.privado.TransportePrivado;
import models.entities.transporte.publico.Linea;
import models.entities.transporte.publico.Parada;
import models.entities.transporte.publico.TransportePublico;
import models.entities.trayectos.Frecuencia;
import models.entities.trayectos.Tramo;
import models.entities.trayectos.Trayecto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersistenciaTest {

    @Test
    @DisplayName("Se persisten datos del dominio")
    public void organizacionPersistir() throws Exception {
        List<Sector> sectores = new ArrayList<>();
        Sector marketing = new Sector();
        marketing.setNombreSector("Marketing");
        sectores.add(marketing);
        List<String> clasificaciones = new ArrayList<>();
        clasificaciones.add("Videojuegos");
        clasificaciones.add("Desarrollador");

        Trabajador juan = new Trabajador("Fernandez","Juan", TipoDoc.DNI, 12345678);

        marketing.agregarTrabajador(juan);
        juan.agregarSector(marketing);

        List<Trabajador> trabajadoresA = new ArrayList<>();
        trabajadoresA.add(juan);

        Pais pais = new Pais(1,"ARGENTINA");
        Provincia provincia = new Provincia(1,"BUENOS AIRES",pais);
        Municipio municipio = new Municipio(1,"CABA",provincia);

        Localidad localidad = new Localidad(1,"ALMAGRO",1,municipio);


        Direccion direccion1 = new Direccion(100,"Rivadavia",localidad);
        Direccion direccion2 = new Direccion(4000,"Corrientes",localidad);
        Direccion direccion3 = new Direccion(2300,"Mozart",localidad);

        Distancia distancia1 = new Distancia(10.0,"KM");
        Distancia distancia2 = new Distancia(12.0,"KM");

        Parada paradaTest1 = new Parada(distancia1,distancia2,direccion2);
        Parada paradaTest2 = new Parada(distancia2,distancia1,direccion3);

        Organizacion organizacion = new Organizacion(clasificaciones,trabajadoresA,
                "Valve Corporation S.A",sectores, TipoOrganizacion.EMPRESA,direccion1);

        marketing.setOrganizacion(organizacion);
        Solicitud sol = new Solicitud(marketing,juan);
        //marketing.aceptarSolicitud(sol);

        List<Organizacion> organizacionList = new ArrayList<>();
        organizacionList.add(organizacion);

        AgenteSectorial agenteSectorial = new AgenteSectorial("Perez","Catalina",municipio,organizacionList);

        TransportePrivado auto = new TransportePrivado(TipoVehiculo.AUTO, TipoCombustible.NAFTA);
        Linea linea7 = new Linea("Linea 7", paradaTest1,paradaTest2);
        TransportePublico colectivoTest = new TransportePublico(linea7,TipoVehiculo.COLECTIVO,TipoCombustible.NAFTA);

        ServicioGeoDDSAdapter adapterMock = mock(ServicioGeoDDSAdapter.class);
        ServicioCalcularDistancia.setAdapter(adapterMock);

        when(adapterMock.distanciaEntre(direccion1,direccion2)).thenReturn(distancia1);
        Tramo tramoAuto = new Tramo(auto,direccion1,direccion2);

        when(adapterMock.distanciaEntre(direccion2,direccion3)).thenReturn(distancia2);
        Tramo tramoColectivo = new Tramo(colectivoTest,direccion2,direccion3);

        List<Tramo> listaTramos = new ArrayList<>();
        listaTramos.add(tramoAuto);
        listaTramos.add(tramoColectivo);

        Frecuencia frecuencia = new Frecuencia(Periodicidad.MENSUAL,8);

        paradaTest1.setParadaSiguiente(paradaTest2);
        paradaTest2.setParadaSiguiente(null);


        auto.agregarTrabajadorATramoCompartido(juan);

        Trayecto trayectoTest = new Trayecto(direccion1,direccion3,listaTramos,frecuencia);

        trayectoTest.cargarTramos(tramoAuto,tramoColectivo);

        juan.agregarTrayectos(trayectoTest);


        ParametroFE autoFE = new ParametroFE(TipoVehiculo.AUTO.toString(),0.2);
        ParametroFE gas = new ParametroFE(TipoDeConsumo.GAS_NATURAL.toString(),0.6);
        List<ParametroFE> parametrosFE = new ArrayList<>();
        parametrosFE.add(autoFE);
        parametrosFE.add(gas);

        CalculoHC.setFactoresEmisionFE(parametrosFE);
        CalculoHC.setUnidadPorDefecto(UnidadHC.GRAMO_EQ);

        Actividad actividadTest = new Actividad(TipoActividad.COMBUSTION_FIJA, TipoDeConsumo.GAS_NATURAL, Unidad.M3,
                new Periodo(7,2021), Periodicidad.MENSUAL,34.0);
        CalculoHC.calcularHCDeActividad(actividadTest);
        organizacion.setListaDeActividades(Arrays.asList(actividadTest));


        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.getEntityManager().persist(pais);
        EntityManagerHelper.getEntityManager().persist(provincia);
        EntityManagerHelper.getEntityManager().persist(municipio);
        EntityManagerHelper.getEntityManager().persist(localidad);

        EntityManagerHelper.getEntityManager().persist(marketing);
        EntityManagerHelper.getEntityManager().persist(auto);
        EntityManagerHelper.getEntityManager().persist(colectivoTest);
        EntityManagerHelper.getEntityManager().persist(linea7);
        EntityManagerHelper.getEntityManager().persist(juan);
        EntityManagerHelper.getEntityManager().persist(sol);
        EntityManagerHelper.getEntityManager().persist(autoFE);
        EntityManagerHelper.getEntityManager().persist(gas);

//        EntityManagerHelper.getEntityManager().persist(actividadTest);
//        EntityManagerHelper.getEntityManager().persist(linea7);
//        EntityManagerHelper.getEntityManager().persist(tramoAuto);
//        EntityManagerHelper.getEntityManager().persist(tramoColectivo);
//        EntityManagerHelper.getEntityManager().persist(paradaTest1);
//        EntityManagerHelper.getEntityManager().persist(paradaTest2);


        EntityManagerHelper.getEntityManager().persist(agenteSectorial);
        EntityManagerHelper.getEntityManager().persist(organizacion);
        EntityManagerHelper.commit();
    }

    @Test
    @DisplayName("Se recupera una organizacion")
    public void organizacionRecuperar(){
        Organizacion org = (Organizacion) EntityManagerHelper
                .createQuery("FROM Organizacion WHERE razon_social = 'Valve Corporation S.A'").getSingleResult(); //HQL = Hibernate Query Language (pseudo SQL)
                //importa el nombre de la clase y de los atributos, no me importa el nombre de la columna
        Assertions.assertEquals("Valve Corporation S.A", org.getRazonSocial());
    }

    //para listas .getResultList(). count: getMaxResult()
    //find(Servicio.class, 1) -> el id

    @Test
    @DisplayName("Se persisten usuarios")
    public void administradorPersistir(){
        Organizacion org = (Organizacion) EntityManagerHelper
                .createQuery("FROM Organizacion WHERE razon_social = 'Valve Corporation S.A'")
                .getSingleResult();
        Trabajador juan = (Trabajador) EntityManagerHelper
                .createQuery("FROM Trabajador WHERE nro_doc=12345678")
                .getSingleResult();
        AgenteSectorial agenteSectorial = (AgenteSectorial)
                EntityManagerHelper
                        .createQuery("FROM AgenteSectorial WHERE municipio_id=1")
                        .getSingleResult();

        Usuario user = new Usuario("juancito","123456789",Rol.BASICO,org.getId(),
                TipoRecurso.ORGANIZACION,Permiso.VER_ORGANIZACION);
        Usuario user2 = new Usuario("tomas","123",Rol.BASICO,juan.getId(),
                TipoRecurso.TRABAJADOR,Permiso.VER_TRABAJADOR);
        Usuario user3 = new Usuario("agente","1234",Rol.BASICO,agenteSectorial.getId(),
                TipoRecurso.AGENTE_SECTORIAL,Permiso.VER_AGENTESECTORIAL);
        Usuario admin = new Usuario("admin","admin",Rol.ADMINISTRADOR, org.getId(),
                TipoRecurso.ORGANIZACION,Permiso.VER_ORGANIZACION);
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.persist(user);
        EntityManagerHelper.persist(user2);
        EntityManagerHelper.persist(user3);
        EntityManagerHelper.persist(admin);
        EntityManagerHelper.commit();
    }

    @AfterAll
    public static void close(){
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }



}
