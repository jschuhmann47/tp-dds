package test.domain.CargaDeDatos;

import domain.CargaDeDatos.adapters.CargaDeDatosApachePOIAdapter;
import domain.CargaDeDatos.entidades.*;
import domain.calculoHC.CalculoHC;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CargaDeDatosApachePOIAdapterTest {
    CargaDeDatosApachePOIAdapter adapterTest;
    HSSFSheet hojaLeida;
    ActividadDA actividadTest, actividadTestCompuesta;



    @BeforeEach
    public void init() throws IOException {
        CalculoHC.cargarFactoresDeEmision("src/main/java/domain/calculoHC/factorEmision.properties");
        adapterTest = new CargaDeDatosApachePOIAdapter("src/main/java/domain/CargaDeDatos/actividad.xls");
        actividadTest =
                new ActividadDA(Actividad.COMBUSTION_FIJA, TipoDeConsumo.GAS_NATURAL, Unidad.M3, Periodicidad.MENSUAL,34.0,4,2022);
        actividadTestCompuesta =
                new ActividadDA(Actividad.LOGISTICA_PRODUCTOS_RESIDUOS, TipoDeConsumo.PRODUCTO_TRANSPORTADO, Unidad.U, Periodicidad.MENSUAL,2.0,5,2021); //no va esa unidad
        hojaLeida = adapterTest.obtenerHoja(0);
    }


    @Test
    @DisplayName("Excel: se lee una fila")
    public void excel() {

        CargaDeDatosApachePOIAdapter.LineaLeida linea = adapterTest.leerFila(hojaLeida,1);
        Assertions.assertEquals("COMBUSTION_FIJA",linea.actividad);
        Assertions.assertEquals("GAS_NATURAL",linea.tipoDeConsumo);
        Assertions.assertEquals("M3",linea.unidad);
        Assertions.assertEquals(34.0,linea.valor);
        Assertions.assertEquals("MENSUAL",linea.periodicidad);
        Assertions.assertEquals("04/2022",linea.periodoImputacion);

    }

    @Test
    @DisplayName("Excel: se lee una fila de campo compuesto") //la de categoria
    public void excelCompuesto() {

        CargaDeDatosApachePOIAdapter.LineaLeida linea = adapterTest.leerFila(hojaLeida,5);
        Assertions.assertEquals("LOGISTICA_PRODUCTOS_RESIDUOS",linea.actividad);
        Assertions.assertEquals("CATEGORIA",linea.tipoDeConsumo);
        Assertions.assertEquals("-",linea.unidad);
        Assertions.assertEquals("MATERIA_PRIMA",linea.valorString);
        Assertions.assertEquals("MENSUAL",linea.periodicidad);
        Assertions.assertEquals("05/2021",linea.periodoImputacion);

    }

    @Test
    @DisplayName("Se parsea la fecha")
    public void fecha() {

        Assertions.assertEquals(4,adapterTest.obtenerMes("04/2022"));
        Assertions.assertEquals(2022,adapterTest.obtenerAnio("04/2022"));
        Assertions.assertNull(adapterTest.obtenerMes("2025"));
        Assertions.assertEquals(2025,adapterTest.obtenerAnio("2025"));

    }

    @Test
    @DisplayName("Se crea una actividad correctamente")
    public void actividad() {

        CargaDeDatosApachePOIAdapter.LineaLeida linea = adapterTest.leerEntrada(hojaLeida,1);
        ActividadDA actividadLeida = adapterTest.crearActividad(linea);
        Assertions.assertEquals(actividadTest.actividad,actividadLeida.actividad);
        Assertions.assertEquals(actividadTest.anio,actividadLeida.anio);
        Assertions.assertEquals(actividadTest.mes,actividadLeida.mes);
        Assertions.assertEquals(actividadTest.valor,actividadLeida.valor);
        Assertions.assertEquals(actividadTest.tipoDeConsumo,actividadLeida.tipoDeConsumo);
        Assertions.assertEquals(actividadTest.periodicidad,actividadLeida.periodicidad);
        Assertions.assertEquals(actividadTest.unidad,actividadLeida.unidad);
    }

    @Test
    @DisplayName("Se crea una actividad compuesta correctamente")
    public void actividadCompuesta() {

        CargaDeDatosApachePOIAdapter.LineaLeida linea = adapterTest.leerEntrada(hojaLeida,5);
        ActividadDA actividadLeida = adapterTest.crearActividad(linea);
        Assertions.assertEquals(actividadTestCompuesta.actividad,actividadLeida.actividad);
        Assertions.assertEquals(actividadTestCompuesta.anio,actividadLeida.anio);
        Assertions.assertEquals(actividadTestCompuesta.mes,actividadLeida.mes);
        Assertions.assertEquals(actividadTestCompuesta.valor,actividadLeida.valor);
        Assertions.assertEquals(actividadTestCompuesta.tipoDeConsumo,actividadLeida.tipoDeConsumo);
        Assertions.assertEquals(actividadTestCompuesta.periodicidad,actividadLeida.periodicidad);
        Assertions.assertEquals(actividadTestCompuesta.unidad,actividadLeida.unidad);

    }

}
