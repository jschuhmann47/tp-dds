package domain.CargaDeDatosAdapter;

import domain.CargaDeDatosAdapter.adapters.CargaDeDatosAdapter;
import domain.CargaDeDatosAdapter.entidades.ActividadDA;
import domain.organizaciones.Organizacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CargaDeDatos {
    Organizacion organizacion;



    List<ActividadDA> listaDeActividades=new ArrayList<>();
    CargaDeDatosAdapter adapter;

    public void cargarDatos(String path) throws IOException {
        listaDeActividades.addAll(adapter.leerArchivoDA(path));
    }
    public List<ActividadDA> getListaDeActividades() {
        return listaDeActividades;
    }
}
