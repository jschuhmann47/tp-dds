package domain.CargaDeDatos.adapters;

import domain.CargaDeDatos.entidades.ActividadDA;

import java.io.IOException;
import java.util.List;

public interface CargaDeDatosAdapter {

    List<ActividadDA> leerArchivo() throws IOException;

}
