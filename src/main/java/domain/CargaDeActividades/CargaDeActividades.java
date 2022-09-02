package domain.CargaDeActividades;

import domain.CargaDeActividades.adapters.CargaDeActividadesAdapter;
import domain.CargaDeActividades.entidades.Actividad;

import java.io.IOException;
import java.util.List;

public class CargaDeActividades {

    static CargaDeActividadesAdapter adapter;

    public static void cargarActividades(List<Actividad> actividades, String path) throws IOException {
        actividades.addAll(adapter.leerArchivo(path));
    }

}
