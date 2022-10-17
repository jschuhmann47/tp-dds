package models.entities.calculoHC;

import lombok.Getter;
import lombok.Setter;
import models.entities.CargaDeActividades.entidades.Actividad;
import models.entities.parametros.ParametroFE;
import models.entities.transporte.privado.TipoVehiculo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public class CalcularHCTransporte {
//    public static HashMap<TipoVehiculo, Double> getConsumosPorKm() {
//        return consumosPorKm;
//    }
//
//    public static HashMap<TipoVehiculo,Double> consumosPorKm;
//
    @Getter
    @Setter
    public static List<ParametroFE> factoresEmisionTransporte;

//    public static void cargarConsumosPorKm(String nombreArchivo){
//        consumosPorKm = new HashMap<>();
//        Properties FEconfigs = new Properties();
//        try{
//            InputStream input = Files.newInputStream(new File(nombreArchivo).toPath());
//            FEconfigs.load(input);
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//        for (String key : FEconfigs.stringPropertyNames()) {
//            Double value = Double.valueOf(FEconfigs.getProperty(key));
//            TipoVehiculo tc = TipoVehiculo.valueOf(key);
//            consumosPorKm.put(tc, value);
//        }
//    }
}
