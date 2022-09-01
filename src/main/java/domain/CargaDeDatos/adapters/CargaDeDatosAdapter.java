package domain.CargaDeDatos.adapters;

import domain.CargaDeDatos.entidades.Actividad;

import java.io.IOException;
import java.util.List;

public interface CargaDeDatosAdapter {

    List<Actividad> leerArchivo(String path) throws IOException;

}
