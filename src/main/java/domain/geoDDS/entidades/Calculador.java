package domain.geoDDS.entidades;


import domain.locaciones.Direccion;

import java.io.IOException;


public abstract class Calculador {
    public static int calcularProvinciaId(Direccion direccion) throws IOException {
        return CalculadorProvinciaId.calcularId(direccion);
    }

    public static int calcularMunicipioId(Direccion direccion,int idProvincia) throws IOException {
        return CalculadorMunicipioId.calcularId(direccion,idProvincia);
    }

    public static int calcularLocalidadId(Direccion direccion,int idMunicipio) throws IOException {
        return CalculadorLocalidadId.calcularId(direccion, idMunicipio);
    }
}
