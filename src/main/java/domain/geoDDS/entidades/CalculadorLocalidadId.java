package domain.geoDDS.entidades;

import domain.geoDDS.ServicioCalcularDistancia;
import domain.locaciones.Direccion;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CalculadorLocalidadId{

    public static int calcularId(Direccion direccion, int idMunicipio) throws Exception {
        List<Localidad> localidades = ServicioCalcularDistancia.getInstance().obtenerLocalidadesDeMunicipio(idMunicipio);

        Optional<Localidad> hayLocalidad=localidades.stream().
                filter(l-> Objects.equals(l.nombre,direccion.getLocalidad())).findFirst();
        if(hayLocalidad.isPresent()){
            return hayLocalidad.get().id;
        }
        else{
            throw new Exception("No hay localidad");
        }
    }
}
