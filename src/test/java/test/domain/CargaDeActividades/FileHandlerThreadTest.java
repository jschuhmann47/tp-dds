package test.domain.CargaDeActividades;

import models.entities.CargaDeActividades.CargaDeActividades;
import models.entities.CargaDeActividades.adapters.CargaDeActividadesApachePOIAdapter;
import models.entities.CargaDeActividades.entidades.TipoDeConsumo;
import models.entities.calculoHC.CalculoHC;
import models.entities.calculoHC.UnidadHC;
import models.entities.parametros.ParametroFE;
import models.helpers.threads.FileHandlerThread;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FileHandlerThreadTest {

    @BeforeAll
    public static void init(){
        ParametroFE keroseneFE = new ParametroFE(TipoDeConsumo.KEROSENE.toString(),0.2);
        ParametroFE leniaFE = new ParametroFE(TipoDeConsumo.LENIA.toString(),0.2);
        ParametroFE gasFE = new ParametroFE(TipoDeConsumo.GAS_NATURAL.toString(),0.6);
        ParametroFE camionCarga = new ParametroFE(TipoDeConsumo.CAMION_CARGA.toString(),2.0);
        ParametroFE prodTransportado = new ParametroFE(TipoDeConsumo.PRODUCTO_TRANSPORTADO.toString(),7.0);
        List<ParametroFE> parametrosFE = new ArrayList<>();
        parametrosFE.add(keroseneFE);
        parametrosFE.add(leniaFE);
        parametrosFE.add(gasFE);
        parametrosFE.add(camionCarga);
        parametrosFE.add(prodTransportado);

        CalculoHC.setFactoresEmisionFE(parametrosFE);
        CalculoHC.setUnidadPorDefecto(UnidadHC.GRAMO_EQ);
    }


    @Test
    @DisplayName("Se genera el hilo y las actividades")
    public void thread(){
//        CargaDeActividades.setAdapter(new CargaDeActividadesApachePOIAdapter());
        FileHandlerThread thread =
                new FileHandlerThread("src/test/java/test/domain/CargaDeActividades/actividad.xls",1,new CargaDeActividadesApachePOIAdapter());
        thread.start();
    }
}
