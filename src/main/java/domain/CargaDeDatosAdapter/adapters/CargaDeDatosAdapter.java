package domain.CargaDeDatosAdapter.adapters;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.io.IOException;

public interface CargaDeDatosAdapter {

    void leerArchivoDA(String file) throws IOException;

    HSSFSheet obtenerHoja(String file) throws IOException;

    void LeerActividad(HSSFSheet hojaALeer);

    void LeerTipoDeConsumo(HSSFSheet hojaALeer);

    void LeerTipoDeConsumoUnidad(HSSFSheet hojaALeer);

    void LeerConsumoValor(HSSFSheet hojaALeer);

    void LeerConsumoPeriodicidad(HSSFSheet hojaALeer);

    void LeerPeriodoDeImputacion(HSSFSheet hojaALeer);



}
