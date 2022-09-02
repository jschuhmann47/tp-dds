package test.domain.CalculoHC;

import domain.CargaDeDatos.CargaDeDatos;
import domain.CargaDeDatos.entidades.*;
import domain.calculoHC.CalculoHC;
import domain.geoDDS.Direccion;
import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.adapters.ServicioGeoDDSAdapter;
import domain.geoDDS.entidades.*;
import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.organizaciones.Trabajador;
import domain.organizaciones.TramoCompartido;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculoHCTest {

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

    Direccion direccion1 = new Direccion(100,"Rivadavia",localidad,municipio,provincia);
    Direccion direccion2 = new Direccion(4000,"Corrientes",localidad,municipio,provincia);
    Direccion direccion3 = new Direccion(2300,"Mozart",localidad,municipio,provincia);

    Distancia distancia1 = new Distancia(10.0,"KM");
    Distancia distancia2 = new Distancia(12.0,"KM");

    Parada paradaTest1 = new Parada(distancia1,distancia2,direccion2);
    Parada paradaTest2 = new Parada(distancia2,distancia1,direccion3);


    TransportePrivado auto = new TransportePrivado(TipoVehiculo.AUTO, TipoCombustible.NAFTA);
    Linea linea7 = new Linea("Linea 7", paradaTest1,paradaTest2);
    TransportePublico colectivoTest = new TransportePublico(linea7,TipoVehiculo.COLECTIVO,TipoCombustible.NAFTA);






    public CalculoHCTest() throws Exception {
    }


    @BeforeEach
    public void init() throws Exception {
        CalculoHC.cargarFactoresDeEmision("src/main/java/domain/calculoHC/factorEmision.properties");
        CalcularHCTransporte.cargarConsumosPorKm("src/main/java/domain/transporte/litrosConsumidosPorKm.properties");

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

        organizacionA = new Organizacion(trabajadoresA, sectoresA);
        organizacionB = new Organizacion(trabajadoresB, sectoresB);

        marketing.organizacion = organizacionA;
        rrhh.organizacion = organizacionB;



        auto.agregarTrabajadorATramoCompartido(juan);

        Trayecto trayectoTest = new Trayecto(direccion1,direccion3,listaTramos,frecuencia);


        trayectoTest.cargarTramos(tramoAuto,tramoColectivo);

        juan.agregarTrayectos(trayectoTest, trayectoTest);
        Periodo periodo = new Periodo(1,2021);
        List<Actividad> actividades = new ArrayList<>();
        Actividad gas = new Actividad(TipoActividad.COMBUSTION_FIJA,TipoDeConsumo.DIESEL,Unidad.M3,
                                periodo,Periodicidad.MENSUAL,1.0);
        actividades.add(gas);
        organizacionA.setListaDeActividades(actividades);
    }


    @Test
    @DisplayName("Se calcula la HC de una organizacion en un año")
    public void orgAnual() throws Exception {
        Periodo periodo = new Periodo(null,2021);
        Assertions.assertEquals(268800.8,organizacionA.calcularHC(periodo));
    }

    @Test
    @DisplayName("Se calcula la HC de una organizacion en un mes")
    public void orgMensual() throws Exception {
        Periodo periodo = new Periodo(7,2021);
        Assertions.assertEquals(22400.0,organizacionA.calcularHC(periodo));
    }

    @Test
    @DisplayName("Se calcula la HC de un empleado")
    public void empl() throws Exception {
        Periodo periodo = new Periodo(null,2021);
        Assertions.assertEquals(268800.0, juan.calcularHC(periodo));

    }

    @Test
    @DisplayName("Se obtiene una coleccion con los detalles de cada sector")
    public void sectores(){
        Periodo periodo = new Periodo(7,2021);
        List<String> detalles = organizacionA.huellaCarbonoPorCadaSector(periodo);
        Assertions.assertEquals("Sector: Marketing - Huella de carbono: 22400.0",detalles.get(0));
    }
}
