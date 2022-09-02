package domain.calculoHC;

import domain.CargaDeActividades.entidades.Actividad;
import domain.CargaDeActividades.entidades.Periodo;
import domain.CargaDeActividades.entidades.TipoDeConsumo;
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
    static UnidadHC unidadPorDefecto;

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


    public static void calcularHCDeActividad(Actividad actividad){
        Double valorAct = actividad.valor * getFactoresEmision().get(actividad.tipoDeConsumo);
        actividad.setHuellaCarbono(CalculoHC.unidadPorDefecto, valorAct);
    }

    public static Double calcularHCDeListaDeActividades(List<Actividad> actividades, Periodo periodo) {

        List<Actividad> listaHC = actividades.stream()
                .filter(a-> Objects.equals(a.periodo.getAnio(), periodo.getAnio())).collect(Collectors.toList());
        if(periodo.getMes()!=null){
            listaHC=listaHC.stream().filter(a->Objects.equals(a.periodo.getMes(), periodo.getMes())).collect(Collectors.toList());
        }
        return sumarListaHC(listaHC.stream().map(a->a.getHuellaCarbono().getValor()).collect(Collectors.toList()));
    }


    private static Double sumarListaHC(List<Double> listaHC){
        return listaHC.stream().mapToDouble(t->t).sum();
    }
}
