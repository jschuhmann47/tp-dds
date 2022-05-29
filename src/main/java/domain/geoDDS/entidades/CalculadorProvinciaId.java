package domain.geoDDS.entidades;


import domain.geoDDS.ServicioCalcularDistancia;
import domain.locaciones.Direccion;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CalculadorProvinciaId {

    public static int calcularId(Direccion direccion) throws Exception {
        List<ProvinciaGeo> provincias = ServicioCalcularDistancia.getInstance().listadoDeProvincias();
        Optional<ProvinciaGeo> hayProvincia;
        hayProvincia=provincias.stream().filter(p-> Objects.equals(p.nombre, direccion.
                getProvinciaString())).findFirst();
        if(hayProvincia.isPresent()){
            return hayProvincia.get().id;

        }else{
            throw new Exception("No hay provincia");
        }
    }
}
