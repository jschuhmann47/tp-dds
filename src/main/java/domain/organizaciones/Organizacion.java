package domain.organizaciones;

import domain.CargaDeDatosAdapter.adapters.CargaDeDatosAdapter;
import domain.geoDDS.Direccion;

import java.io.IOException;
import java.util.List;

public class Organizacion{
    private String clasificacionOrg;
    private List<Trabajador> miembros;
    private String razonSocial;
    private List<Sector> sectores;
    private TipoOrganizacion tipoOrganizacion;
    private Direccion ubicacion;
    private CargaDeDatosAdapter adapter;


    public CargaDeDatosAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CargaDeDatosAdapter adapter) {
        this.adapter = adapter;
    }


    public void cargarDatos(String archivo) throws IOException {
        adapter.leerArchivoDA(archivo);
    }
}