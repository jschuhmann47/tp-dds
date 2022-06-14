package test.domain.trayectos;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.*;
import domain.transporte.TipoCombustible;
import domain.transporte.privado.TipoVehiculo;
import domain.transporte.privado.TransportePrivado;
import domain.transporte.publico.Linea;
import domain.transporte.publico.Parada;
import domain.transporte.publico.TransportePublico;
import domain.trayectos.Tramo;
import domain.trayectos.Trayecto;
import org.apache.poi.ss.formula.functions.T;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrayectoTest {

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


    public TrayectoTest() throws IOException {
    }

    @BeforeEach
    public void init(){
        List<Tramo> tramosTest = new ArrayList<>();
        tramosTest.add(tramoAuto);
        tramosTest.add(tramoColectivo);

        trayectoTest = new Trayecto(direccion1,direccion3,tramosTest);
    }

    @Test
    @DisplayName("Se calcula la distancia total de un trayecto como la suma de la distancia de cada tramo")
    public void test(){
        //TODO
        //mockear la distancia del auto
    }
}
