package domain.CargaDeDatosAdapter;

import domain.CargaDeDatosAdapter.adapters.CargaDeDatosAdapter;
import domain.CargaDeDatosAdapter.entidades.ActividadDA;
import domain.organizaciones.Organizacion;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CargaDeDatos {
    @Setter
    Organizacion organizacion;


    public void agregarActividades(ActividadDA ... actividades) { //para test
        this.listaDeActividades.addAll(Arrays.asList(actividades));
    }

    List<ActividadDA> listaDeActividades=new ArrayList<>();
    CargaDeDatosAdapter adapter;

    public void cargarDatos(String path) throws IOException {
        listaDeActividades.addAll(adapter.leerArchivoDA());
    }
    public List<ActividadDA> getListaDeActividades() {
        return listaDeActividades;
    }


}
