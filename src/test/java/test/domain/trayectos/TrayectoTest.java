package test.domain.trayectos;

import models.entities.CargaDeActividades.entidades.Periodicidad;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.ServicioCalcularDistancia;
import models.entities.geoDDS.adapters.ServicioGeoDDSAdapter;
import models.entities.geoDDS.entidades.*;
import models.entities.transporte.TipoCombustible;
import models.entities.transporte.privado.TipoVehiculo;
import models.entities.transporte.privado.TransportePrivado;
import models.entities.transporte.publico.Linea;
import models.entities.transporte.publico.Parada;
import models.entities.transporte.publico.TransportePublico;
import models.entities.trayectos.Frecuencia;
import models.entities.trayectos.Tramo;
import models.entities.trayectos.Trayecto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrayectoTest {
    ServicioGeoDDSAdapter adapterMock;

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



    Trayecto trayectoTest;


    public TrayectoTest() throws Exception {
    }

    @BeforeEach
    public void init() throws Exception {
        this.adapterMock = mock(ServicioGeoDDSAdapter.class);
        ServicioCalcularDistancia.setAdapter(this.adapterMock);

        when(this.adapterMock.distanciaEntre(direccion1,direccion2)).thenReturn(distancia1);

        when(this.adapterMock.distanciaEntre(direccion2,direccion3)).thenReturn(distancia2);

        paradaTest1.setParadaSiguiente(paradaTest2);
        paradaTest2.setParadaSiguiente(null);

        List<Tramo> tramosTest = new ArrayList<>();
        Tramo tramoAuto = new Tramo(auto,direccion1,direccion2);
        Tramo tramoColectivo = new Tramo(colectivoTest,direccion2,direccion3);
        tramosTest.add(tramoAuto);
        tramosTest.add(tramoColectivo);
        Frecuencia frecuencia = new Frecuencia(Periodicidad.MENSUAL,5);
        trayectoTest = new Trayecto(direccion1,direccion3,tramosTest,frecuencia);
    }

    @Test
    @DisplayName("Se calcula la distancia total de un trayecto como la suma de la distancia de cada tramo")
    public void test(){
        Assertions.assertEquals(22.0,trayectoTest.distanciaTrayecto().valor);
    }
}
