package test.domain.transporte;

import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.*;
import domain.transporte.TipoCombustible;
import domain.transporte.privado.TipoVehiculo;
import domain.transporte.publico.Linea;
import domain.transporte.publico.Parada;
import domain.transporte.publico.TransportePublico;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DistanciaParadasTest {

    Pais pais = new Pais(1,"A");
    Provincia provincia = new Provincia(1,"A",pais);
    Municipio municipio = new Municipio(1,"A",provincia);

    Localidad localidad = new Localidad(1,"A",1,municipio);

    Direccion direccion1 = new Direccion(100,"Rivadavia",localidad);
    Direccion direccion2 = new Direccion(4000,"Corrientes",localidad);

    Distancia distancia1 = new Distancia(10.0,"KM");
    Distancia distancia2 = new Distancia(12.0,"KM");

    Parada paradaTest1 = new Parada(distancia1,distancia2,direccion1);
    Parada paradaTest2 = new Parada(distancia2,distancia1,direccion2);





    Linea linea7 = new Linea("Linea 7", paradaTest1,paradaTest2);
    TransportePublico colectivoTest = new TransportePublico(linea7, TipoVehiculo.COLECTIVO, TipoCombustible.NAFTA);

    public DistanciaParadasTest() throws IOException {
    }


    @BeforeEach
    public void init(){
        paradaTest1.setParadaSiguiente(paradaTest2);
    }


    @Test
    @DisplayName("Se calcula la distancia entre una parada que esta antes de otra")
    public void paradaAntes() throws Exception {
        Assertions.assertEquals(12.0,colectivoTest.calcularDistancia(direccion1,direccion2).valor);
    }

}
