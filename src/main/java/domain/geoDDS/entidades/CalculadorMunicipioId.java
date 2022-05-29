package domain.geoDDS.entidades;

import domain.geoDDS.ServicioCalcularDistancia;
import domain.locaciones.Direccion;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CalculadorMunicipioId{
    public static int calcularId(Direccion direccion, int idProvincia) throws Exception {

        List<Municipio> listaMunicipios= ServicioCalcularDistancia.getInstance().obtenerMunicipiosDeProvincia(idProvincia);
        Optional<Municipio> hayMunicipio=listaMunicipios.stream().
                filter(m-> Objects.equals(m.nombre,direccion.getMunicipio())).findFirst();
        if(hayMunicipio.isPresent()){
            return hayMunicipio.get().id;
        }else{
            throw new Exception("No hay municipio");
        }
    }
}
