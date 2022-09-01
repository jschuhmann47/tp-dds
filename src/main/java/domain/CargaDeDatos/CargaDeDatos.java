package domain.CargaDeDatos;

import domain.CargaDeDatos.adapters.CargaDeDatosAdapter;
import domain.CargaDeDatos.entidades.Actividad;
import domain.organizaciones.Organizacion;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CargaDeDatos {

    static CargaDeDatosAdapter adapter;

    public static void cargarDatos(List<Actividad> actividades, String path) throws IOException {
        actividades.addAll(adapter.leerArchivo(path));
    }

}
