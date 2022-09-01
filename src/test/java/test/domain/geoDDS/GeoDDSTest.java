package test.domain.geoDDS;

import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.adapters.ServicioGeoDDSAdapter;
import domain.geoDDS.entidades.*;
import domain.geoDDS.Direccion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeoDDSTest {

    ServicioGeoDDSAdapter adapterMock;


    @BeforeEach
    public void init() {
        this.adapterMock = mock(ServicioGeoDDSAdapter.class);
        ServicioCalcularDistancia.setAdapter(adapterMock);
    }

    private Pais argentina = new Pais(9,"ARGENTINA");

    private Provincia buenosAires = new Provincia(168,"BUENOS AIRES",argentina);
    private Provincia cordoba = new Provincia(172,"CORDOBA",argentina);

    private Municipio gralLavalle = new Municipio(379,"GENERAL LAVALLE",buenosAires);
    private Municipio gralPaz = new Municipio(380,"GENERAL PAZ",buenosAires);

    private Localidad ranchos = new Localidad(3680,"RANCHOS",1987,gralPaz);
    private Localidad pavon = new Localidad(3678,"PAVON",7103,gralLavalle);

    Direccion direccionPrueba1 = new Direccion(100,"maipu",pavon,gralLavalle, buenosAires);
    Direccion direccionPrueba2 = new Direccion(450,"san martin",ranchos,gralPaz, buenosAires);

    @Test
    @DisplayName("Se obtiene la distancia entre dos direcciones")
    public void obtenerDistancia() throws Exception {
        Distancia distanciaMock = new Distancia(50.147,"KM");
        when(this.adapterMock.distanciaEntre(direccionPrueba1,direccionPrueba2)).thenReturn(distanciaMock);
        Distancia distancia = ServicioCalcularDistancia.distanciaEntre(direccionPrueba1,direccionPrueba2);
        Assertions.assertEquals(50.147,distancia.valor);
        Assertions.assertEquals("KM", distancia.unidad);
    }
}
