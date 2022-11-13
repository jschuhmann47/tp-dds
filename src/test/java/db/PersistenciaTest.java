package db;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
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
import models.entities.transporte.TipoCombustible;
import models.entities.transporte.privado.TipoVehiculo;
import models.entities.transporte.privado.TransportePrivado;
import models.entities.transporte.publico.Linea;
import models.entities.transporte.publico.Parada;
import models.entities.transporte.publico.TransportePublico;
import models.entities.trayectos.Frecuencia;
import models.entities.trayectos.Tramo;
import models.entities.trayectos.Trayecto;
import models.helpers.GsonHelper;
import models.helpers.PersistenciaHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersistenciaTest {

    @Test
    @DisplayName("Se persisten datos del dominio")
    public void organizacionPersistir() throws Exception {

        ParametroFE autoFE = new ParametroFE(TipoVehiculo.AUTO.toString(),40.0);
        ParametroFE gas = new ParametroFE(TipoDeConsumo.GAS_NATURAL.toString(),0.6);
        ParametroFE colectivoFE = new ParametroFE(TipoVehiculo.COLECTIVO.toString(),25.0);
        ParametroFE nafta = new ParametroFE(TipoDeConsumo.NAFTA.toString(),1.2);
        ParametroFE diesel = new ParametroFE(TipoDeConsumo.DIESEL.toString(),0.8);
        ParametroFE prodTransportado = new ParametroFE(TipoDeConsumo.PRODUCTO_TRANSPORTADO.toString(),1.0);
        ParametroFE kerosene = new ParametroFE(TipoDeConsumo.KEROSENE.toString(),2.5);
        ParametroFE lenia = new ParametroFE(TipoDeConsumo.LENIA.toString(),2.0);
        ParametroFE camion = new ParametroFE(TipoDeConsumo.CAMION_CARGA.toString(),1.0);

        List<ParametroFE> parametrosFE = new ArrayList<>();
        parametrosFE.add(autoFE);
        parametrosFE.add(gas);
        parametrosFE.add(colectivoFE);
        parametrosFE.add(nafta);
        parametrosFE.add(diesel);
        parametrosFE.add(prodTransportado);
        parametrosFE.add(kerosene);
        parametrosFE.add(lenia);
        parametrosFE.add(camion);

        CalculoHC.setFactoresEmisionFE(parametrosFE);
        CalculoHC.setUnidadPorDefecto(UnidadHC.GRAMO_EQ);

        List<Sector> sectores = new ArrayList<>();
        Sector marketing = new Sector();
        marketing.setNombreSector("Marketing");
        sectores.add(marketing);
        List<String> clasificaciones = new ArrayList<>();
        clasificaciones.add("Videojuegos");
        clasificaciones.add("Desarrollador");

        Trabajador juan = new Trabajador("Fernandez","Juan", new TipoDeDocumento(PosibleTipoDocumento.DNI), 12345678);

        marketing.agregarTrabajador(juan);
        juan.agregarSector(marketing);


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

        Organizacion organizacion = new Organizacion(clasificaciones,
                "Valve Corporation S.A",sectores, TipoOrganizacion.EMPRESA,direccion1);

        marketing.setOrganizacion(organizacion);
        Solicitud sol = new Solicitud(marketing,juan);
        //marketing.aceptarSolicitud(sol);

        List<Organizacion> organizacionList = new ArrayList<>();
        organizacionList.add(organizacion);

        AgenteSectorial agenteSectorial = new AgenteSectorial("Perez","Catalina",municipio,organizacionList);

        TransportePrivado auto = new TransportePrivado(TipoVehiculo.AUTO, TipoCombustible.NAFTA);
        auto.setNombre("Auto de Juan");

        Linea linea7 = new Linea("Linea 7", paradaTest1,paradaTest2);
        TransportePublico colectivoTest = new TransportePublico(linea7,TipoVehiculo.COLECTIVO,TipoCombustible.NAFTA);
        colectivoTest.setNombre("Colectivo Linea 7");

        ServicioGeoDDSAdapter adapterMock = mock(ServicioGeoDDSAdapter.class);
        ServicioCalcularDistancia.setAdapter(adapterMock);

        auto.agregarTrabajadorATramoCompartido(juan);

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




        Trayecto trayectoTest = new Trayecto(direccion1,direccion3,listaTramos,frecuencia);

        juan.agregarTrayectos(trayectoTest);

        trayectoTest.registrarViajesEnMesYAnio(7,2021,5);

        juan.calcularHC(new Periodo(7,2021));



        Actividad actividadTest = new Actividad(TipoActividad.COMBUSTION_FIJA, TipoDeConsumo.GAS_NATURAL, Unidad.M3,
                new Periodo(7,2021), Periodicidad.MENSUAL,34.0);
        Actividad actividadTest2 = new Actividad(TipoActividad.COMBUSTION_FIJA, TipoDeConsumo.GAS_NATURAL, Unidad.M3,
                new Periodo(6,2021), Periodicidad.MENSUAL,18.0);
        CalculoHC.calcularHCDeActividad(actividadTest);
        CalculoHC.calcularHCDeActividad(actividadTest2);
        organizacion.setListaDeActividades(Arrays.asList(actividadTest,actividadTest2));

//        TipoDeDocumento dni = new TipoDeDocumento(PosibleTipoDocumento.DNI);
        TipoDeDocumento enrolamiento = new TipoDeDocumento(PosibleTipoDocumento.LIBRETA_ENROLAMIENTO);
        TipoDeDocumento civil = new TipoDeDocumento(PosibleTipoDocumento.LIBRETA_CIVIL);
        TipoDeDocumento cedula = new TipoDeDocumento(PosibleTipoDocumento.CEDULA);


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

        for (ParametroFE p : parametrosFE){
            EntityManagerHelper.getEntityManager().persist(p);
        }

        EntityManagerHelper.getEntityManager().persist(trayectoTest);
//        EntityManagerHelper.getEntityManager().persist(dni);
        EntityManagerHelper.getEntityManager().persist(enrolamiento);
        EntityManagerHelper.getEntityManager().persist(civil);
        EntityManagerHelper.getEntityManager().persist(cedula);


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
        Assertions.assertEquals("Rivadavia", org.getDireccion().getCalle());
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

        Usuario user = new Usuario("juancito","11",Rol.BASICO,org.getId(),
                TipoRecurso.ORGANIZACION,Permiso.VER_ORGANIZACION);
        Usuario user2 = new Usuario("tomas","123",Rol.BASICO,juan.getId(),
                TipoRecurso.TRABAJADOR,Permiso.VER_TRABAJADOR);
        Usuario user3 = new Usuario("agente","1234",Rol.BASICO,agenteSectorial.getId(),
                TipoRecurso.AGENTE,Permiso.VER_AGENTESECTORIAL);
        Usuario admin = new Usuario("admin","admin",Rol.ADMINISTRADOR, org.getId(),
                TipoRecurso.ORGANIZACION,Permiso.VER_ORGANIZACION);
        EntityManagerHelper.beginTransaction();
        EntityManagerHelper.persist(user);
        EntityManagerHelper.persist(user2);
        EntityManagerHelper.persist(user3);
        EntityManagerHelper.persist(admin);
        EntityManagerHelper.commit();
    }

    @Test
    @DisplayName("Se persisten localidades de la API")
    public void localidades() throws IOException {
        String ruta = "/src/test/java/db/jsons/";
        String jsonPaises = Files.asCharSource(new File(System.getProperty("user.dir") + ruta + "paises.json"), Charsets.UTF_8).read();
        String jsonProvincias = Files.asCharSource(new File(System.getProperty("user.dir") + ruta + "provincias.json"), Charsets.UTF_8).read();
        String jsonMunicipios = Files.asCharSource(new File(System.getProperty("user.dir") + ruta + "municipios.json"), Charsets.UTF_8).read();
        String jsonLocalidades = Files.asCharSource(new File(System.getProperty("user.dir") + ruta + "localidades.json"), Charsets.UTF_8).read();

        List<Pais> paises = GsonHelper.generarPaises(jsonPaises);
        List<Provincia> provincias = GsonHelper.generarProvincias(jsonProvincias);
        List<Municipio> municipios = GsonHelper.generarMunicipios(jsonMunicipios);
        List<Localidad> localidades = GsonHelper.generarLocalidades(jsonLocalidades);

        //TODO no anda (colisiones)
        EntityManagerHelper.beginTransaction();
        localidades.forEach(l -> EntityManagerHelper.getEntityManager().persist(l));
        municipios.forEach(l -> EntityManagerHelper.getEntityManager().persist(l));
        provincias.forEach(l -> EntityManagerHelper.getEntityManager().persist(l));
        paises.forEach(l -> EntityManagerHelper.getEntityManager().persist(l));
        EntityManagerHelper.commit();

    }

    @AfterAll
    public static void close(){
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }



}
