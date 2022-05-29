package domain.geoDDS.entidades;


import domain.locaciones.Direccion;


public abstract class Calculador {
    public static int calcularProvinciaId(Direccion direccion) throws Exception{
        return CalculadorProvinciaId.calcularId(direccion);
    }

    public static int calcularMunicipioId(Direccion direccion,int idProvincia) throws Exception {
        return CalculadorMunicipioId.calcularId(direccion,idProvincia);
    }

    public static int calcularLocalidadId(Direccion direccion,int idMunicipio) throws Exception {
        return CalculadorLocalidadId.calcularId(direccion, idMunicipio);
    }
}
