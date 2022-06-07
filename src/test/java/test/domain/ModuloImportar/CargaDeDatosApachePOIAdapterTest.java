package test.domain.ModuloImportar;

import domain.CargaDeDatosAdapter.entidades.FormularioDA;
import domain.CargaDeDatosAdapter.entidades.TipoDeConsumo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CargaDeDatosApachePOIAdapterTest {

    List<FormularioDA> formulariosDa=new ArrayList<>();

    @BeforeEach
    public void init() throws IOException {
//        InputStream myFile = new FileInputStream(new File(fileName));
//        //Creo una nueva instalacia de un archivo excel
//        HSSFWorkbook wb = new HSSFWorkbook(myFile);
//        //Defino que voy a leer la primera pagina
//        HSSFSheet sheet = wb.getSheetAt(0);
//        // Creo una celda y un objeto fila
//        HSSFCell cell;
//        HSSFRow row;
    }


    @Test
    @DisplayName("Excel")
    public void asd() throws IOException {
        InputStream myFile = Files.newInputStream(new
                File("src/main/java/domain/organizaciones/cargaDeDatos/actividad.xls").toPath());
        //Creo una nueva instalacia de un archivo excel
        HSSFWorkbook wb = new HSSFWorkbook(myFile);
        //Defino que voy a leer la primera pagina
        HSSFSheet sheet = wb.getSheetAt(0);
        // Creo una celda y un objeto fila
        HSSFCell cell;
        HSSFRow row;
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            // Empiezo a contar desde la segunda  fila
            row = sheet.getRow(i + 2);
            if (row == null){
                break;
            }

            // SOlo voy a retornar todos los elementos de la primera columna a partir de la segunda fila
            cell = row.getCell(0);

            // Copio el valor del la celda a al atributo
            FormularioDA form = new FormularioDA();
            form.actividad=cell.toString();
            this.formulariosDa.add(form);
            System.out.println(cell.toString());
            //this.formulariosDa.get(i).actividad = cell.toString();

            //-----------------------------Prueba -----------------------
            System.out.println("Actividad: " + this.formulariosDa.get(i).actividad);
        }
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            row = sheet.getRow(i+2);
            cell = row.getCell(2);
            TipoDeConsumo tipoDeConsum = new TipoDeConsumo();
            tipoDeConsum.tipoDeConsumo = cell.toString();
            this.formulariosDa.get(i).tipoDeConsumo = tipoDeConsum;
            System.out.println("TipoDeConsumo: " + this.formulariosDa.get(i).tipoDeConsumo.tipoDeConsumo);

        }
    }

}
