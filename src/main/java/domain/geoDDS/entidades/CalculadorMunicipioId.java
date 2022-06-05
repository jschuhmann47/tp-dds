package domain.geoDDS.entidades;

import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.Direccion;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CalculadorMunicipioId{
    public static int calcularId(Direccion direccion, int idProvincia) throws IOException {
        Optional<Municipio> posibleMuncipio = municipioDeNombre(direccion.getMunicipio(), idProvincia);
        if(posibleMuncipio.isPresent()){
            return posibleMuncipio.get().getId();
        }
        else{
            throw new IOException();
        }
    }

    static Optional<Municipio> municipioDeNombre(String nombre, int idProvincia) throws IOException {
        List<Municipio> listaMunicipios= ServicioCalcularDistancia.getInstance().obtenerMunicipiosDeProvincia(idProvincia);
        return listaMunicipios.stream().
                filter(m-> Objects.equals(m.nombre,nombre)).findFirst();
    }
}
