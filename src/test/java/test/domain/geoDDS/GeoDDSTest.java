package test.domain.geoDDS;

import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.adapters.ServicioGeoDDSAdapter;
import domain.geoDDS.entidades.*;
import domain.locaciones.Direccion;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeoDDSTest {
    ServicioCalcularDistancia servicioDistanciaTest;
    ServicioGeoDDSAdapter adapterMock;
    CalculadorProvinciaId asd;


    @BeforeEach
    public void init() {
        this.adapterMock = mock(ServicioGeoDDSAdapter.class);
        this.servicioDistanciaTest = ServicioCalcularDistancia.getInstance();
        this.servicioDistanciaTest.setAdapter(this.adapterMock);
    }

    private Pais argentina = new Pais(9,"ARGENTINA");
    private Provincia buenosAires = new Provincia(168,"BUENOS AIRES",argentina);
    private Provincia cordoba = new Provincia(172,"CORDOBA",argentina);

    private List<Provincia> provinciasMock(){
        List<Provincia> provinciasMock = new ArrayList<>();
        provinciasMock.add(buenosAires);
        provinciasMock.add(cordoba);
        return  provinciasMock;
    }

    private Municipio gralLavalle = new Municipio(379,"GENERAL LAVALLE",buenosAires);
    private Municipio gralPaz = new Municipio(380,"GENERAL PAZ",buenosAires);

    private List<Municipio> municipiosMock(){
        List<Municipio> municipiosMock = new ArrayList<>();
        municipiosMock.add(gralLavalle);
        municipiosMock.add(gralPaz);

        return municipiosMock;
    }

    private Localidad ranchos = new Localidad(3680,"RANCHOS",1987,gralPaz);
    private Localidad pavon = new Localidad(3678,"PAVON",7103,gralLavalle);

    private List<Localidad> localidadesMock(){
        List<Localidad> localidadesMock = new ArrayList<>();
        localidadesMock.add(ranchos);
        localidadesMock.add(pavon);

        return localidadesMock;
    }

    Direccion direccionPrueba1 = new Direccion(100,"maipu","PAVON","GENERAL LAVALLE", buenosAires);
    Direccion direccionPrueba2 = new Direccion(450,"san martin","RANCHOS","GENERAL PAZ", buenosAires);


    @Test
    @DisplayName("Se obtiene el ID de una provincia dada la direccion")
    public void idProvincia() throws Exception {
        List<Provincia> provinciasMock = this.provinciasMock();
        when(this.adapterMock.listadoDeProvincias()).thenReturn(provinciasMock);

        Assertions.assertEquals(168, servicioDistanciaTest.obtenerProvinciaId(direccionPrueba2));
    }

    @Test
    @DisplayName("Se obtiene el ID de un municipio dada la direccion y el ID de la provincia")
    public void idMunicipio() throws Exception {
        List<Municipio> municipiosMock = this.municipiosMock();
        when(this.adapterMock.obtenerMunicipiosDeProvincia(168)).thenReturn(municipiosMock);
        Assertions.assertEquals(380, servicioDistanciaTest.obtenerMunicipioId(direccionPrueba2,168));
    }

    @Test
    @DisplayName("Se obtiene el ID de una localidad dada la direccion y el ID del municipio")
    public void idLocalidad() throws Exception {
        List<Provincia> provinciasMock = this.provinciasMock();
        List<Municipio> municipiosMock = this.municipiosMock();
        List<Localidad> localidadesMock = this.localidadesMock();

        when(this.adapterMock.listadoDeProvincias()).thenReturn(provinciasMock);
        when(this.adapterMock.obtenerLocalidadesDeMunicipio(380)).thenReturn(localidadesMock);
        when(this.adapterMock.obtenerMunicipiosDeProvincia(168)).thenReturn(municipiosMock);

        Assertions.assertEquals(3680, servicioDistanciaTest.obtenerLocalidadId(direccionPrueba2));
    }

    @Test
    @DisplayName("Se obtiene la distancia entre dos direcciones")
    public void obtenerDistancia() throws Exception {
        Distancia distanciaMock = new Distancia("50.147","KM");
        when(this.adapterMock.distanciaEntre(direccionPrueba1,direccionPrueba2)).thenReturn(distanciaMock);
        Distancia distancia = servicioDistanciaTest.distanciaEntre(direccionPrueba1,direccionPrueba2);
        Assertions.assertEquals("50.147",distancia.valor);
        Assertions.assertEquals("KM", distancia.unidad);
    }
}
