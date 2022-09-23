package test.domain.geoDDS;

import models.entities.geoDDS.ServicioCalcularDistancia;
import models.entities.geoDDS.adapters.ServicioGeoDDSAdapter;
import models.entities.geoDDS.Direccion;

import models.entities.geoDDS.entidades.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeoDDSTest {

    ServicioGeoDDSAdapter adapterMock;


    @BeforeEach
    public void init() {
        this.adapterMock = mock(ServicioGeoDDSAdapter.class);
        ServicioCalcularDistancia.setAdapter(adapterMock);
    }

    private final Pais argentina = new Pais(9,"ARGENTINA");

    private final Provincia buenosAires = new Provincia(168,"BUENOS AIRES",argentina);
    private final Provincia cordoba = new Provincia(172,"CORDOBA",argentina);

    private final Municipio gralLavalle = new Municipio(379,"GENERAL LAVALLE",buenosAires);
    private final Municipio gralPaz = new Municipio(380,"GENERAL PAZ",buenosAires);

    private final Localidad ranchos = new Localidad(3680,"RANCHOS",1987,gralPaz);
    private final Localidad pavon = new Localidad(3678,"PAVON",7103,gralLavalle);

    Direccion direccionPrueba1 = new Direccion(100,"maipu",pavon);
    Direccion direccionPrueba2 = new Direccion(450,"san martin",ranchos);

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
