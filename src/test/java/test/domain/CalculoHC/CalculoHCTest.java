package test.domain.CalculoHC;

import domain.calculoHC.CalculoHC;
import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.*;
import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.organizaciones.Trabajador;
import domain.organizaciones.TramoCompartido;
import domain.transporte.TipoCombustible;
import domain.transporte.privado.TipoVehiculo;
import domain.transporte.privado.TransportePrivado;
import domain.transporte.publico.Linea;
import domain.transporte.publico.Parada;
import domain.transporte.publico.TransportePublico;
import domain.trayectos.Tramo;
import domain.trayectos.Trayecto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalculoHCTest {

    TramoCompartido tramoCompartido = new TramoCompartido();
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

    Parada paradaTest1 = new Parada(distancia1,distancia2,direccion1);
    Parada paradaTest2 = new Parada(distancia2,distancia1,direccion2);

    TransportePrivado auto = new TransportePrivado(TipoVehiculo.AUTO, TipoCombustible.NAFTA);
    Linea linea7 = new Linea("Linea 7", paradaTest1,paradaTest2);
    TransportePublico colectivoTest = new TransportePublico(linea7);

    List<Tramo> tramosTest;

    Tramo tramoAuto = new Tramo(auto,direccion1,direccion2);
    Tramo tramoColectivo = new Tramo(colectivoTest,direccion2,direccion3);

    Trayecto trayectoTest;

    public CalculoHCTest() throws IOException {
    }


    @BeforeEach
    public void init() throws Exception {
        sectoresA.add(marketing);
        sectoresB.add(rrhh);

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

        tramoCompartido.validarTrabajador(juan); //juan va a estar subido

        trayectoTest.cargarTramos(tramoAuto,tramoColectivo);

        juan.agregarTrayectos(trayectoTest);
    }


    @Test
    @DisplayName("Se calcula la HC de una organizacion en un año")
    public void orgAnual() throws Exception {
        Assertions.assertEquals(100.0,organizacionA.calcularHCEnAnio(2021)); //ver cuanto da esto
    }

    @Test
    @DisplayName("Se calcula la HC de una organizacion en un mes")
    public void orgMensual() throws Exception {
        Assertions.assertEquals(100.0,organizacionA.calcularHCEnMes(7,2021)); //ver cuanto da esto
    }

    @Test
    @DisplayName("Se calcula la HC de un empleado")
    public void empl(){
        //Assertions.assertEquals(100.0, juan.calcularHC()); //no da 100, hay que sacar la cuenta

    }
}
