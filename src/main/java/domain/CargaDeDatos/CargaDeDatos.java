package domain.CargaDeDatos;

import domain.CargaDeDatos.adapters.CargaDeDatosAdapter;
import domain.CargaDeDatos.entidades.Actividad;
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


    public void agregarActividades(Actividad... actividades) { //para test
        this.listaDeActividades.addAll(Arrays.asList(actividades));
    }

    List<Actividad> listaDeActividades=new ArrayList<>();
    CargaDeDatosAdapter adapter;

    public void cargarDatos(String path) throws IOException {
        listaDeActividades.addAll(adapter.leerArchivo(path));
    }
    public List<Actividad> getListaDeActividades() {
        return listaDeActividades;
    }


}
