package domain.geoDDS.adapters;

import domain.geoDDS.entidades.Distancia;
import domain.geoDDS.entidades.Localidad;
import domain.geoDDS.entidades.Municipio;
import domain.geoDDS.entidades.ProvinciaGeo;
import domain.locaciones.Direccion;

import java.io.IOException;
import java.util.List;

public interface GeoDDSAdapter {
    public Distancia distanciaEntre(Direccion direccionOrigen, Direccion direccionDestino) throws Exception;
    int obtenerIdLocalidad(Direccion direccion) throws Exception;
    public List<ProvinciaGeo> listadoDeProvincias() throws IOException;
    public List<Localidad> obtenerLocalidadesDeMunicipio(int municipioId) throws IOException;
    public List<Localidad> listadoDeLocalidades() throws IOException;
    public List<Municipio> listadoDeMunicipios() throws IOException;
    public List<Municipio> obtenerMunicipiosDeProvincia(int idProvincia) throws IOException;
}
