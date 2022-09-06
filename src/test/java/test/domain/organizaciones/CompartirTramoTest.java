package test.domain.organizaciones;

import domain.organizaciones.*;
import domain.trayectos.TramoCompartido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

public class CompartirTramoTest {

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


    @BeforeEach
    public void init () throws Exception {
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

        tramoCompartido.agregarTrabajadorATramoCompartido(juan); //juan va a estar subido

    }

    @Test
    @DisplayName("Pueden compartir tramo dos personas de la misma empresa")
    public void comparten() throws Exception {
        tramoCompartido.agregarTrabajadorATramoCompartido(pepe);
    }

    @Test
    @DisplayName("Pueden compartir tramo dos personas que comparten una empresa")
    public void comparten2() throws Exception {
        tramoCompartido.agregarTrabajadorATramoCompartido(luis);
    }

    @Test
    @DisplayName("No puede compartir tramo una persona que no comparte empresa con las personas ya cargadas en el tramo")
    public void noComparten() throws Exception {
        Assertions.assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                tramoCompartido.agregarTrabajadorATramoCompartido(carlos);
            }
        });

    }


}
