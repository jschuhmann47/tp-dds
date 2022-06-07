package domain.CargaDeDatosAdapter.adapters;

import domain.CargaDeDatosAdapter.entidades.Consumo;
import domain.CargaDeDatosAdapter.entidades.FormularioDA;
import domain.CargaDeDatosAdapter.entidades.TipoDeConsumo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;


public class CargaDeDatosApachePOIAdapter implements CargaDeDatosAdapter{


    public CargaDeDatosApachePOIAdapter(String path){
        this.file=path;
    }

    List<FormularioDA> formulariosDa = new ArrayList<>();

    public String file;

    //La idea es leer fila por fila , ya que no se puede leer por columnas

    public  void  leerArchivoDA(String file) throws IOException {

        HSSFSheet hojaALeer = obtenerHoja(file);

        LeerActividad(hojaALeer);
        LeerTipoDeConsumo(hojaALeer);
        LeerTipoDeConsumoUnidad(hojaALeer);
        LeerConsumoValor(hojaALeer);
        LeerConsumoPeriodicidad(hojaALeer);
        LeerPeriodoDeImputacion(hojaALeer);
    }

    public HSSFSheet obtenerHoja(String file) throws IOException {
        InputStream myFile = Files.newInputStream(new File(file).toPath());
        //Creo una nueva instalacia de un archivo excel
        HSSFWorkbook wb = new HSSFWorkbook(myFile);
        //Defino que voy a leer la primera pagina
        // Creo una celda y un objeto fila
        return wb.getSheetAt(0);
    }

    public void LeerActividad(HSSFSheet hojaALeer){
        HSSFCell cell;
        HSSFRow row;

        //getLastRowNum() me reotorna el indice de la ultima fila
        for (int i = 0; i < hojaALeer.getLastRowNum() + 1; i++) {
            // Empiezo a contar desde la segunda  fila
            row = hojaALeer.getRow(i + 2);
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

            //-----------------------------Prueba -----------------------
            //System.out.println("Actividad: " + this.formulariosDa.get(i).actividad);
        }

    }

    public void LeerTipoDeConsumo(HSSFSheet hojaALeer)  {

        HSSFCell cell;
        HSSFRow row;

        for (int i = 0; i < hojaALeer.getLastRowNum() + 1; i++) {
            row = hojaALeer.getRow(i+2);
            if (row == null){
                break;
            }
            cell = row.getCell(2);
            TipoDeConsumo tCon = new TipoDeConsumo();
            tCon.tipoDeConsumo = cell.toString();
            this.formulariosDa.get(i).tipoDeConsumo = tCon;
            //System.out.println("TipoDeConsumo: " + this.formulariosDa.get(i).tipoDeConsumo.tipoDeConsumo);
        }

    }

    public void LeerTipoDeConsumoUnidad(HSSFSheet hojaALeer)  {

        HSSFCell cell;
        HSSFRow row;
        for (int i = 0; i < hojaALeer.getLastRowNum() + 1; i++) {
            row = hojaALeer.getRow(i+2);
            if (row == null){
                break;
            }
            cell = row.getCell(4);
            this.formulariosDa.get(i).tipoDeConsumo.unidad= cell.toString();
            //System.out.println("Unidad: " + this.formulariosDa.get(i).tipoDeConsumo.unidad );
        }

    }

    public void LeerConsumoValor(HSSFSheet hojaALeer) {

        HSSFCell cell;
        HSSFRow row;

        for (int i = 0; i < hojaALeer.getLastRowNum() + 1; i++) {
            row = hojaALeer.getRow(i+2);
            if (row == null){
                break;
            }
            cell = row.getCell(5);
            Consumo con = new Consumo();
            con.valor = parseDouble(cell.toString());
            this.formulariosDa.get(i).consumo = con;
            //System.out.println("Consumo - Valor: " +  this.formulariosDa.get(i).consumo.valor);
        }
    }

    public void LeerConsumoPeriodicidad(HSSFSheet hojaALeer)  {

        HSSFCell cell;
        HSSFRow row;

        for (int i = 0; i < hojaALeer.getLastRowNum() + 1; i++) {
            row = hojaALeer.getRow(i+2);
            if (row == null){
                break;
            }
            cell = row.getCell(6);
            this.formulariosDa.get(i).consumo.periocidad = cell.toString();
            //System.out.println("Consumo - Periodicidad: " + cell.toString());
        }

    }

    public void LeerPeriodoDeImputacion(HSSFSheet hojaALeer)  {

        HSSFCell cell;
        HSSFRow row;

        for (int i = 0; i < hojaALeer.getLastRowNum() + 1; i++) {
            row = hojaALeer.getRow(i+2);
            if (row == null){
                break;
            }
            cell = row.getCell(6);
            this.formulariosDa.get(i).periodoDeImputacion = cell.toString();
            //System.out.println("Periodo de Imputacion: " + cell.toString());
        }

    }
}
