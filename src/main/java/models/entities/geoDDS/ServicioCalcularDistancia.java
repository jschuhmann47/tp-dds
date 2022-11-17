package models.entities.geoDDS;

import models.entities.geoDDS.adapters.ServicioGeoDDSAdapter;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.geoDDS.entidades.Localidad;
import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Provincia;

import java.io.IOException;
import java.util.List;

public class ServicioCalcularDistancia {

    static ServicioGeoDDSAdapter adapter;

    public static void setAdapter(ServicioGeoDDSAdapter adapter) {
        ServicioCalcularDistancia.adapter = adapter;
    }

    public static ServicioGeoDDSAdapter getAdapter() {
        return adapter;
    }


    public static Distancia distanciaEntre(Direccion direccionOrigen, Direccion direccionDestino) throws IOException {
        return adapter.distanciaEntre(direccionOrigen,direccionDestino);
    }

    public static int obtenerLocalidadId(Direccion direccion) {
        return direccion.getLocalidad().getId();
    }

    public static List<Provincia> obtenerProvincias() throws IOException {
        return ServicioCalcularDistancia.getAdapter().obtenerProvincias();
    }

    public static List<Municipio> municipiosDeProvincia(Integer provinciaId) throws IOException {
        return ServicioCalcularDistancia.getAdapter().municipiosDeProvincia(provinciaId);
    }

    public static List<Localidad> localidadesDeMunicipio(Integer municipioId) throws IOException {
        return ServicioCalcularDistancia.getAdapter().localidadesDeMunicipio(municipioId);
    }


}
