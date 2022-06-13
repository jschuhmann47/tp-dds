package domain.CargaDeDatosAdapter.adapters;

import domain.CargaDeDatosAdapter.entidades.ActividadDA;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.io.IOException;
import java.util.List;

public interface CargaDeDatosAdapter {

    List<ActividadDA> leerArchivoDA(String file) throws IOException;

    HSSFSheet obtenerHoja(String file) throws IOException;

    void LeerActividad(HSSFSheet hojaALeer);

    void LeerTipoDeConsumo(HSSFSheet hojaALeer);

    void LeerTipoDeConsumoUnidad(HSSFSheet hojaALeer);

    void LeerConsumoValor(HSSFSheet hojaALeer);

    void LeerConsumoPeriodicidad(HSSFSheet hojaALeer);

    void LeerPeriodoDeImputacion(HSSFSheet hojaALeer);



}
