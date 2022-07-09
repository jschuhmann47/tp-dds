package test.domain.CargaDeDatosAdapter;

import domain.CargaDeDatosAdapter.adapters.CargaDeDatosApachePOIAdapter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CargaDeDatosApachePOIAdapterTest {
    CargaDeDatosApachePOIAdapter adapterTest = new CargaDeDatosApachePOIAdapter("src/main/java/domain/CargaDeDatosAdapter/actividad.xls");
    HSSFSheet hojaLeida;



    @BeforeEach
    public void init() throws IOException {
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

}
