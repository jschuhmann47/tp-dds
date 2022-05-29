package test.domain.geoDDS;

import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.entidades.CalculadorLocalidadId;
import domain.geoDDS.entidades.CalculadorMunicipioId;
import domain.geoDDS.entidades.CalculadorProvinciaId;
import domain.geoDDS.entidades.Distancia;
import domain.locaciones.Direccion;
import domain.locaciones.Provincia;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.function.Executable;

import java.util.Objects;

public class GeoDDSTest {
    ServicioCalcularDistancia servicioDistanciaTest = new ServicioCalcularDistancia();


    Direccion direccionPrueba1 = new Direccion(100,"maipu","PAVON","GENERAL LAVALLE", Provincia.BUENOS_AIRES);
    Direccion direccionPrueba2 = new Direccion(450,"san martin","RANCHOS","GENERAL PAZ",Provincia.BUENOS_AIRES);

    @Test
    @DisplayName("Se obtiene el ID de una provincia dada la direccion")
    public void idProvincia() throws Exception {
        Assertions.assertEquals(168, CalculadorProvinciaId.calcularId(direccionPrueba2));
    }

    @Test
    @DisplayName("Se obtiene el ID de un municipio dada la direccion y el ID de la provincia")
    public void idMunicipio() throws Exception {
        Assertions.assertEquals(380, CalculadorMunicipioId.calcularId(direccionPrueba2,168));
    }

    @Test
    @DisplayName("Se obtiene el ID de una localidad dada la direccion y el ID del municipio")
    public void idLocalidad() throws Exception {
        Assertions.assertEquals(3680, CalculadorLocalidadId.calcularId(direccionPrueba2,380));
    }

    @Test
    @DisplayName("Se obtiene la distancia entre dos direcciones")
    public void obtenerIdLocalidad() throws Exception {
        Distancia distancia = servicioDistanciaTest.distanciaEntre(direccionPrueba1,direccionPrueba2);
        double value = Double.parseDouble(distancia.valor);
        Assertions.assertTrue(value > 0); //no es deterministico, por lo que no se puede contrastar con un valor concreto
        Assertions.assertEquals("KM", distancia.unidad);
    }
}
