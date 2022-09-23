package models.entities.CargaDeActividades.adapters;

import models.entities.CargaDeActividades.entidades.Actividad;

import java.io.IOException;
import java.util.List;

public interface CargaDeActividadesAdapter {

    List<Actividad> leerArchivo(String path) throws IOException;

}
