package domain.CargaDeDatosAdapter.adapters;

import domain.CargaDeDatosAdapter.entidades.ActividadDA;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.io.IOException;
import java.util.List;

public interface CargaDeDatosAdapter {

    List<ActividadDA> leerArchivoDA() throws IOException;

}
