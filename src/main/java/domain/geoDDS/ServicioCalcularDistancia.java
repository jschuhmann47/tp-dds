package domain.geoDDS;

import domain.geoDDS.adapters.ServicioGeoDDSAdapter;
import domain.geoDDS.entidades.*;
import domain.locaciones.Direccion;

import java.io.IOException;
import java.util.List;

public class ServicioCalcularDistancia {
    static ServicioCalcularDistancia instancia = null;
    //private Retrofit retrofit;
    ServicioGeoDDSAdapter adapter;

    public void setAdapter(ServicioGeoDDSAdapter adapter) {
        this.adapter = adapter;
    }

    public ServicioGeoDDSAdapter getAdapter() {
        return adapter;
    }

    public static ServicioCalcularDistancia getInstance(){
        if (instancia==null){
            instancia=new ServicioCalcularDistancia();
        }
        return instancia;
    }

    public Distancia distanciaEntre(Direccion direccionOrigen, Direccion direccionDestino) throws Exception {
        return adapter.distanciaEntre(direccionOrigen,direccionDestino);
    }

    public static int obtenerIdLocalidad(Direccion direccion) throws Exception {
        int idProvincia = Calculador.calcularProvinciaId(direccion);
        int idMunicipio = Calculador.calcularMunicipioId(direccion,idProvincia);
        return Calculador.calcularLocalidadId(direccion, idMunicipio);
    }

    public List<ProvinciaGeo> listadoDeProvincias() throws IOException {

        return adapter.listadoDeProvincias();
    }

    public List<Localidad> obtenerLocalidadesDeMunicipio(int municipioId) throws IOException {

        return adapter.obtenerLocalidadesDeMunicipio(municipioId);
    }

    public List<Localidad> listadoDeLocalidades() throws IOException {

        return adapter.listadoDeLocalidades();
    }

    public List<Municipio> listadoDeMunicipios() throws IOException {

        return adapter.listadoDeMunicipios();
    }

    public List<Municipio> obtenerMunicipiosDeProvincia(int idProvincia) throws IOException {

        return adapter.obtenerMunicipiosDeProvincia(idProvincia);
    }


}
