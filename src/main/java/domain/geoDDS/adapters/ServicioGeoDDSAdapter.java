package domain.geoDDS.adapters;

import domain.geoDDS.entidades.Distancia;
import domain.geoDDS.entidades.Localidad;
import domain.geoDDS.entidades.Municipio;
import domain.geoDDS.entidades.ProvinciaGeo;
import domain.locaciones.Direccion;

import java.io.IOException;
import java.util.List;

public interface ServicioGeoDDSAdapter {
    Distancia distanciaEntre(Direccion direccionOrigen, Direccion direccionDestino) throws Exception;
    List<ProvinciaGeo> listadoDeProvincias() throws IOException;
    List<Localidad> obtenerLocalidadesDeMunicipio(int municipioId) throws IOException;
    List<Localidad> listadoDeLocalidades() throws IOException;
    List<Municipio> listadoDeMunicipios() throws IOException;
    List<Municipio> obtenerMunicipiosDeProvincia(int idProvincia) throws IOException;
}
