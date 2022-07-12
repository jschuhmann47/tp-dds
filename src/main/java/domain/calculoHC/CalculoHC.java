package domain.calculoHC;

import domain.CargaDeDatos.entidades.ActividadDA;
import domain.CargaDeDatos.entidades.Periodo;
import domain.CargaDeDatos.entidades.TipoDeConsumo;
import lombok.Getter;
import lombok.Setter;

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

    @Getter
    @Setter
    static HashMap<TipoDeConsumo, Double> factoresEmision = new HashMap<>();

    public static void cargarFactoresDeEmision(String nombreArchivo){
        Properties FEconfigs = new Properties();
        try{
            InputStream input = Files.newInputStream(new File(nombreArchivo).toPath());
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

    public static Double getFactorEmision(TipoDeConsumo tc){
        return factoresEmision.get(tc);
    }


    public static Double calcularHCDeActividad(ActividadDA actividadDA){
        return actividadDA.valor * getFactoresEmision().get(actividadDA.tipoDeConsumo);
    }

    public static Double calcularHCDeListaDeActividades(List<ActividadDA> actividadesDA, Periodo periodo) {

        List<ActividadDA> listaHC = actividadesDA.stream()
                .filter(a-> Objects.equals(a.periodo.getAnio(), periodo.getAnio())).collect(Collectors.toList());
        if(periodo.getMes()!=null){
            listaHC=listaHC.stream().filter(a->Objects.equals(a.periodo.getMes(), periodo.getMes())).collect(Collectors.toList());
        }
        return sumarListaHC(listaHC.stream().map(CalculoHC::calcularHCDeActividad).collect(Collectors.toList()));
    }


    private static Double sumarListaHC(List<Double> listaHC){
        return listaHC.stream().mapToDouble(t->t).sum();
    }
}
