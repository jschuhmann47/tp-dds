package domain.ModuloImportar;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static java.lang.Double.parseDouble;

public class ImportarArchivo {
    public class ImportarArchivo {

        List<FormularioDA> formulariosDa ;

        String file = "Path/actividad.xls";

        //La idea es leer fila por fila , ya que no se puede leer por columnas

        public  void  leerArchivoDA(String file){

            LeerActividad(file);
            LeerTipoDeConsumo(file);
            LeerTipoDeConsumoUnidad(file);
            LeerConsumoValor(file);
            LeerConsumoPeriodicidad(file);
            LeerPeriodoDeImputacion(file);
        }

        private  void LeerActividad(String fileName) {
            try {

                InputStream myFile = new FileInputStream(new File(fileName));
                //Creo una nueva instalacia de un archivo excel
                HSSFWorkbook wb = new HSSFWorkbook(myFile);
                //Defino que voy a leer la primera pagina
                HSSFSheet sheet = wb.getSheetAt(0);
                // Creo una celda y un objeto fila
                HSSFCell cell;
                HSSFRow row;


                //getLastRowNum() me reotorna el indice de la ultima fila
                for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                    // Empiezo a contar desde la segunda  fila
                    row = sheet.getRow(i + 2);

                    // SOlo voy a retornar todos los elementos de la primera columna a partir de la segunda fila
                    cell = row.getCell(0);

                    // Copio el valor del la celda a al atributo
                    this.formulariosDa.get(i).actividad = cell.toString();

                    //-----------------------------Prueba -----------------------
                    System.out.println("Actividad: " + this.formulariosDa.get(i).actividad);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        private  void LeerTipoDeConsumo(String fileName)  {
            try {
                InputStream myFile = new FileInputStream(new File(fileName));
                HSSFWorkbook wb = new HSSFWorkbook(myFile);
                HSSFSheet sheet = wb.getSheetAt(0);
                HSSFCell cell;
                HSSFRow row;
                System.out.println("" + sheet.getLastRowNum());
                for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                    row = sheet.getRow(i+2);
                    cell = row.getCell(2);
                    this.formulariosDa.get(i).tipoDeConsumo.tipoDeConsumo = cell.toString();
                    System.out.println("TipoDeConsumo: " + this.formulariosDa.get(i).tipoDeConsumo.tipoDeConsumo);

                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        private  void LeerTipoDeConsumoUnidad(String fileName)  {
            try {
                InputStream myFile = new FileInputStream(new File(fileName));
                HSSFWorkbook wb = new HSSFWorkbook(myFile);
                HSSFSheet sheet = wb.getSheetAt(0);
                HSSFCell cell;
                HSSFRow row;
                System.out.println("" + sheet.getLastRowNum());
                for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                    row = sheet.getRow(i+2);
                    cell = row.getCell(4);
                    this.formulariosDa.get(i).tipoDeConsumo.unidad= cell.toString();
                    System.out.println("Unidad: " + this.formulariosDa.get(i).tipoDeConsumo.unidad );
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        private  void LeerConsumoValor(String fileName) {
            try {
                InputStream myFile = new FileInputStream(new File(fileName));
                HSSFWorkbook wb = new HSSFWorkbook(myFile);
                HSSFSheet sheet = wb.getSheetAt(0);
                HSSFCell cell;
                HSSFRow row;

                for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                    row = sheet.getRow(i+2);
                    cell = row.getCell(5);
                    this.formulariosDa.get(i).consumo.valor = parseDouble(cell.toString());
                    System.out.println("Consumo - Valor: " +  this.formulariosDa.get(i).consumo.valor);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }

        private  void LeerConsumoPeriodicidad(String fileName)  {
            try {
                InputStream myFile = new FileInputStream(new File(fileName));
                HSSFWorkbook wb = new HSSFWorkbook(myFile);
                HSSFSheet sheet = wb.getSheetAt(0);
                HSSFCell cell;
                HSSFRow row;

                for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                    row = sheet.getRow(i+2);
                    cell = row.getCell(6);
                    this.formulariosDa.get(i).consumo.periocidad = cell.toString();
                    System.out.println("Consumo - Periodicidad: " + cell.toString());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        private  void LeerPeriodoDeImputacion(String fileName)  {
            try {
                InputStream myFile = new FileInputStream(new File(fileName));
                HSSFWorkbook wb = new HSSFWorkbook(myFile);
                HSSFSheet sheet = wb.getSheetAt(0);
                HSSFCell cell;
                HSSFRow row;

                for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                    row = sheet.getRow(i+2);
                    cell = row.getCell(6);
                    this.formulariosDa.get(i).periodoDeImputacion = cell.toString();
                    System.out.println("Periodo de Imputacion: " + cell.toString());

                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
}
