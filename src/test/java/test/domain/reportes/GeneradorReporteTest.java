package test.domain.reportes;

import domain.CargaDeActividades.entidades.*;
import domain.calculoHC.CalculoHC;
import domain.geoDDS.Direccion;
import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.adapters.ServicioGeoDDSAdapter;
import domain.geoDDS.entidades.*;
import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.Trabajador;
import domain.reportes.Composicion;
import domain.reportes.GeneradorReporte;
import domain.transporte.CalcularHCTransporte;
import domain.transporte.TipoCombustible;
import domain.transporte.privado.TipoVehiculo;
import domain.transporte.privado.TransportePrivado;
import domain.transporte.publico.Linea;
import domain.transporte.publico.Parada;
import domain.transporte.publico.TransportePublico;
import domain.trayectos.Frecuencia;
import domain.trayectos.Tramo;
import domain.trayectos.Trayecto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeneradorReporteTest {

    ServicioGeoDDSAdapter adapterMock;
    Trabajador juan = new Trabajador();
    Trabajador pepe = new Trabajador();
    Trabajador carlos = new Trabajador();
    Trabajador luis = new Trabajador();

    Sector marketing = new Sector();
    Sector rrhh = new Sector();

    List<Trabajador> trabajadoresA = new ArrayList<>();
    List<Trabajador> trabajadoresB = new ArrayList<>();

    List<Sector> sectoresA = new ArrayList<>();
    List<Sector> sectoresB = new ArrayList<>();

    Organizacion organizacionA;
    Organizacion organizacionB;

    Pais pais = new Pais(1,"A");
    Provincia provincia = new Provincia(1,"A",pais);
    Municipio municipio = new Municipio(1,"A",provincia);

    Localidad localidad = new Localidad(1,"A",1,municipio);

    Direccion direccion1 = new Direccion(100,"Rivadavia",localidad);
    Direccion direccion2 = new Direccion(4000,"Corrientes",localidad);
    Direccion direccion3 = new Direccion(2300,"Mozart",localidad);

    Distancia distancia1 = new Distancia(10.0,"KM");
    Distancia distancia2 = new Distancia(12.0,"KM");

    Parada paradaTest1 = new Parada(distancia1,distancia2,direccion2);
    Parada paradaTest2 = new Parada(distancia2,distancia1,direccion3);


    TransportePrivado auto = new TransportePrivado(TipoVehiculo.AUTO, TipoCombustible.NAFTA);
    Linea linea7 = new Linea("Linea 7", paradaTest1,paradaTest2);
    TransportePublico colectivoTest = new TransportePublico(linea7,TipoVehiculo.COLECTIVO,TipoCombustible.NAFTA);

    List<Organizacion> organizaciones;

    public GeneradorReporteTest() throws IOException {
    }

    @BeforeEach
    public void init() throws Exception {
        CalculoHC.cargarFactoresDeEmision("src/test/java/test/domain/CalculoHC/factorEmision.properties");
        CalcularHCTransporte.cargarConsumosPorKm("src/test/java/test/domain/CalculoHC/litrosConsumidosPorKm.properties");

        this.adapterMock = mock(ServicioGeoDDSAdapter.class);
        ServicioCalcularDistancia.setAdapter(this.adapterMock);

        when(this.adapterMock.distanciaEntre(direccion1,direccion2)).thenReturn(distancia1);
        Tramo tramoAuto = new Tramo(auto,direccion1,direccion2);

        when(this.adapterMock.distanciaEntre(direccion2,direccion3)).thenReturn(distancia2);
        Tramo tramoColectivo = new Tramo(colectivoTest,direccion2,direccion3);

        List<Tramo> listaTramos = new ArrayList<>();
        listaTramos.add(tramoAuto);
        listaTramos.add(tramoColectivo);

        Frecuencia frecuencia = new Frecuencia(Periodicidad.MENSUAL,8);

        paradaTest1.setParadaSiguiente(paradaTest2);
        paradaTest2.setParadaSiguiente(null);

        sectoresA.add(marketing);
        marketing.nombreSector = "Marketing";
        sectoresB.add(rrhh);
        rrhh.nombreSector = "RR.HH";

        juan.sectores= new ArrayList<>();
        juan.sectores.add(marketing); //trabaja empresa A
        trabajadoresA.add(juan);

        pepe.sectores= new ArrayList<>();
        pepe.sectores.add(marketing); //trabaja empresa A
        trabajadoresA.add(pepe);

        carlos.sectores= new ArrayList<>();
        carlos.sectores.add(rrhh); //trabaja empresa B
        trabajadoresB.add(carlos);

        luis.sectores= new ArrayList<>();
        luis.sectores.add(marketing); //trabaja empresa A y B
        luis.sectores.add(rrhh);
        trabajadoresB.add(luis);
        trabajadoresA.add(luis);

        marketing.trabajadores = new ArrayList<>();
        marketing.trabajadores.add(juan);
        marketing.trabajadores.add(pepe);
        marketing.trabajadores.add(luis);

        rrhh.trabajadores = new ArrayList<>();
        rrhh.trabajadores.add(carlos);
        rrhh.trabajadores.add(luis);

        marketing.organizacion = organizacionA;
        rrhh.organizacion = organizacionB;

        List<String> clasificaciones = new ArrayList<>();
        clasificaciones.add("Videojuegos");
        organizacionA = new Organizacion(clasificaciones,trabajadoresA,
                "Valve Corporation S.A",sectoresA, TipoOrganizacion.EMPRESA,direccion1);
        organizacionB = new Organizacion(clasificaciones,trabajadoresB,
                "Respawn Entretainment S.A",sectoresB, TipoOrganizacion.EMPRESA,direccion2);


        auto.agregarTrabajadorATramoCompartido(juan);
        Trayecto trayectoTest = new Trayecto(direccion1,direccion3,listaTramos,frecuencia);

        trayectoTest.cargarTramos(tramoAuto,tramoColectivo);
        juan.agregarTrayectos(trayectoTest, trayectoTest);


        Periodo periodo = new Periodo(1,2021);
        List<Actividad> actividades = new ArrayList<>();
        Actividad gas = new Actividad(TipoActividad.COMBUSTION_FIJA, TipoDeConsumo.DIESEL,Unidad.M3,
                periodo,Periodicidad.MENSUAL,1.0);
        actividades.add(gas);
        organizacionA.setListaDeActividades(actividades);
        CalculoHC.calcularHCDeActividad(gas);

        organizaciones = new ArrayList<>();
        organizaciones.add(organizacionA);
        organizaciones.add(organizacionB);

        organizacionB.setListaDeActividades(new ArrayList<>());
        organizacionB.getListaDeActividades().add(gas);

    }


    @Test
    @DisplayName("Se genera el HC total de un sector territorial")
    public void HCTotalSectorMunicipio(){

        Assertions.assertEquals(1.6,GeneradorReporte.HCTotalPorSectorTerritorial(organizaciones,municipio));
    }

    @Test
    @DisplayName("Se genera el HC total por la clasificacion")
    public void HCTotalSector(){
        String clasificacion = "Videojuegos";
        Assertions.assertEquals(1.6,GeneradorReporte.HCTotalPorClasificacion(organizaciones,clasificacion));
    }

    @Test
    @DisplayName("Se genera la composicion de HC total de un sector territorial")
    public void composicionHCTotalSector(){
        Assertions.assertEquals(10.0,GeneradorReporte.ComposicionHCTotalPorSectorTerritorial(organizaciones,municipio).get(0).getPorcentaje());
    }

    @Test
    @DisplayName("Se genera la composicion de un pais")
    public void composicionHCTotalProvincias(){
        List<Provincia> provincias = new ArrayList<>();
        provincias.add(provincia);
        Assertions.assertEquals(10.0,GeneradorReporte.ComposicionHCTotalPorProvincias(organizaciones,provincias).get(0).getPorcentaje());
    }

    @Test
    @DisplayName("Se genera la composicion de una organizacion")
    public void composicionHCTotalOrganizacion(){
        Assertions.assertEquals(10.0,GeneradorReporte.ComposicionHCTotalDeUnaOrganizacion(organizacionA).get(0).getPorcentaje());
    }




}
