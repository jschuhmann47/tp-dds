package domain.CargaDeDatos;

import domain.CargaDeDatos.adapters.CargaDeDatosAdapter;
import domain.CargaDeDatos.entidades.ActividadDA;
import domain.organizaciones.Organizacion;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//suprimir?
public class CargaDeDatos {
    @Setter
    Organizacion organizacion;


    public void agregarActividades(ActividadDA ... actividades) { //para test
        this.listaDeActividades.addAll(Arrays.asList(actividades));
    }

    List<ActividadDA> listaDeActividades=new ArrayList<>();
    CargaDeDatosAdapter adapter;

    public void cargarDatos(String path) throws IOException {
        listaDeActividades.addAll(adapter.leerArchivo());
    }
    public List<ActividadDA> getListaDeActividades() {
        return listaDeActividades;
    }


}
