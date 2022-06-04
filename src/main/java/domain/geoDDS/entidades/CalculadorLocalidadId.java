package domain.geoDDS.entidades;

import domain.geoDDS.ServicioCalcularDistancia;
import domain.locaciones.Direccion;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CalculadorLocalidadId{

    public static int calcularId(Direccion direccion, int idMunicipio) throws IOException {
        Optional<Localidad> posibleLocalidad = localidadDeNombre(direccion.getLocalidad(), idMunicipio);
        if(posibleLocalidad.isPresent()){
            return posibleLocalidad.get().getId();
        }
        else {
            throw new IOException();
        }
    }

    static Optional<Localidad> localidadDeNombre(String nombre, int idMunicipio) throws IOException {
        List<Localidad> localidades = ServicioCalcularDistancia.getInstance().obtenerLocalidadesDeMunicipio(idMunicipio);
        return localidades.stream().
                filter(l-> Objects.equals(l.nombre,nombre)).findFirst();
    }

}
