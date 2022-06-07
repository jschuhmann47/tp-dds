package test.domain.CargaDeDatosAdapter;

import domain.CargaDeDatosAdapter.adapters.CargaDeDatosApachePOIAdapter;
import domain.CargaDeDatosAdapter.entidades.ActividadDA;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CargaDeDatosApachePOIAdapterTest {
    CargaDeDatosApachePOIAdapter adapterTest = new CargaDeDatosApachePOIAdapter("src/main/java/domain/CargaDeDatosAdapter/actividad.xls");
    List<ActividadDA> formulariosDa=new ArrayList<>();
    HSSFSheet hojaLeida;

    @BeforeEach
    public void init() throws IOException {
        hojaLeida = adapterTest.obtenerHoja(adapterTest.file);
    }


    @Test
    @DisplayName("Excel: se lee una celda")
    public void excel() throws IOException {

        HSSFCell cell;
        HSSFRow row;

        row = hojaLeida.getRow(2);
        cell = row.getCell(0);

        ActividadDA form = new ActividadDA();
        form.actividad=cell.toString();
        this.formulariosDa.add(form);
        //System.out.println(cell.toString());

        //-----------------------------Prueba -----------------------
        Assertions.assertEquals("Combustión Fija",formulariosDa.get(0).actividad);

    }

}
