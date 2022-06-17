package domain.calculoHC;

import domain.CargaDeDatosAdapter.entidades.ActividadDA;
import domain.CargaDeDatosAdapter.entidades.TipoDeConsumo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public class CalculoHC {
    HashMap<TipoDeConsumo, Double> factoresEmision = new HashMap<>();

    public CalculoHC(){
        Properties FEconfigs = new Properties();
        try{
            InputStream input = Files.newInputStream(new File("factorEmision.properties").toPath());
            FEconfigs.load(input);
        } catch(IOException e){
            e.printStackTrace();
        }
        for (String key : FEconfigs.stringPropertyNames()) {
            Double value = Double.valueOf(FEconfigs.getProperty(key));
            TipoDeConsumo tc = TipoDeConsumo.valueOf(key);
            factoresEmision.put(tc, value);
        }

    }

    public HuellaCarbono calcularHCDeActividad(ActividadDA actividadDA){
        //todo unidad
        return null;
    }

    public HuellaCarbono calcularHCDeListaDeActividadesEnMes(List<ActividadDA> actividadesDA,Integer mes) throws Exception {
        if(actividadesDA.stream().anyMatch(a->a.mes==null)){
            throw new Exception("No se puede calcular las HC ya que al menos una actividad es anual");
        }
        List<HuellaCarbono> listaHC = actividadesDA.stream().filter(a-> Objects.equals(a.mes, mes)).map(this::calcularHCDeActividad).collect(Collectors.toList());
        return sumarListaHC(listaHC);

    }

    private HuellaCarbono sumarListaHC(List<HuellaCarbono> listaHC){
        //todo
        //ver el tema de las unidades
        return null;
    }
}
