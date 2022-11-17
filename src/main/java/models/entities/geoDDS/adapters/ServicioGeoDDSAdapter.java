package models.entities.geoDDS.adapters;

import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.geoDDS.entidades.Localidad;
import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Provincia;

import java.io.IOException;
import java.util.List;

public interface ServicioGeoDDSAdapter {
    Distancia distanciaEntre(Direccion direccionOrigen, Direccion direccionDestino) throws IOException;

    List<Provincia> obtenerProvincias() throws IOException;

    List<Municipio> municipiosDeProvincia(Integer provinciaId) throws IOException;

    List<Localidad> localidadesDeMunicipio(Integer municipioId) throws IOException;

}
