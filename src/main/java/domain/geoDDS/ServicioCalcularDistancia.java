package domain.geoDDS;

import domain.geoDDS.adapters.ServicioGeoDDSAdapter;
import domain.geoDDS.entidades.*;

import java.io.IOException;
import java.util.List;

public class ServicioCalcularDistancia {
    static ServicioCalcularDistancia instancia = null;
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

    public Distancia distanciaEntre(Direccion direccionOrigen, Direccion direccionDestino) throws IOException {
        return adapter.distanciaEntre(direccionOrigen,direccionDestino);
    }

    public int obtenerLocalidadId(Direccion direccion) throws IOException { //nukear
        int idProvincia = this.obtenerProvinciaId(direccion);
        int idMunicipio = this.obtenerMunicipioId(direccion,idProvincia);
        return Calculador.calcularLocalidadId(direccion, idMunicipio);
    }

    public int obtenerProvinciaId(Direccion direccion) throws IOException {
        return Calculador.calcularProvinciaId(direccion);
    }

    public int obtenerMunicipioId(Direccion direccion, int idProvincia) throws IOException {
        return Calculador.calcularMunicipioId(direccion,idProvincia);
    }

    public List<Provincia> listadoDeProvincias() throws IOException {
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
