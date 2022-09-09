package test.domain.reportes;

import domain.geoDDS.entidades.Municipio;
import domain.reportes.GeneradorReporte;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GeneradorReporteTest {

    @Test
    @DisplayName("Se genera el HC total de un sector territorial")
    public void HCTotalSector(){
        Municipio municipio;
        Assertions.assertEquals(100,GeneradorReporte.HCTotalPorSectorTerritorial(municipio).getValor());
    }

    @Test
    @DisplayName("Se genera el HC total por la clasificacion")
    public void HCTotalSector(){
        String clasificacion;
        Assertions.assertEquals(100,GeneradorReporte.HCTotalPorClasificacion(clasificacion).getValor());
    }
}
