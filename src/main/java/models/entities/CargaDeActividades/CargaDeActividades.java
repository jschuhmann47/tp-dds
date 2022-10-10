package models.entities.CargaDeActividades;

import models.entities.CargaDeActividades.adapters.CargaDeActividadesAdapter;
import models.entities.CargaDeActividades.entidades.Actividad;
import models.entities.calculoHC.CalculoHC;

import java.io.IOException;
import java.util.List;

public class CargaDeActividades {

    static CargaDeActividadesAdapter adapter;

    public static void cargarActividadesDeArchivo(List<Actividad> actividades, String path) throws IOException {
        actividades.addAll(adapter.leerArchivo(path));
        actividades.forEach(CalculoHC::calcularHCDeActividad);
    }

    public static void cargarActividad(List<Actividad> actividades, Actividad actividad) {
        CalculoHC.calcularHCDeActividad(actividad);
        actividades.add(actividad);
    }

}
