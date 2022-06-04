package domain.geoDDS.entidades;


import domain.geoDDS.ServicioCalcularDistancia;
import domain.locaciones.Direccion;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CalculadorProvinciaId {

    public static int calcularId(Direccion direccion) throws IOException {
        Optional<Provincia> posibleProvincia = provinciaDeNombre(direccion.getProvinciaString());
        if(posibleProvincia.isPresent()){
            return posibleProvincia.get().getId();
        }
        else{
            throw new IOException();
        }
    }

    static Optional<Provincia> provinciaDeNombre(String nombre) throws IOException {
        List<Provincia> provincias = ServicioCalcularDistancia.getInstance().listadoDeProvincias();
        return provincias.stream()
                .filter(p -> Objects.equals(p.nombre, nombre))
                .findFirst();
    }
}
