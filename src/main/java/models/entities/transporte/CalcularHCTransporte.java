package models.entities.transporte;

import models.entities.transporte.privado.TipoVehiculo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Properties;

public class CalcularHCTransporte {
    public static HashMap<TipoVehiculo, Double> getConsumosPorKm() {
        return consumosPorKm;
    }

    public static HashMap<TipoVehiculo,Double> consumosPorKm;


    public static void cargarConsumosPorKm(String nombreArchivo){
        consumosPorKm = new HashMap<>();
        Properties FEconfigs = new Properties();
        try{
            InputStream input = Files.newInputStream(new File(nombreArchivo).toPath());
            FEconfigs.load(input);
        } catch(IOException e){
            e.printStackTrace();
        }
        for (String key : FEconfigs.stringPropertyNames()) {
            Double value = Double.valueOf(FEconfigs.getProperty(key));
            TipoVehiculo tc = TipoVehiculo.valueOf(key);
            consumosPorKm.put(tc, value);
        }
    }
}
