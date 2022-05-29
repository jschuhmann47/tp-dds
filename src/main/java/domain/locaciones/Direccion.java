package domain.locaciones;

public class Direccion {
    private Integer altura;
    private String calle;
    private String localidad;

    public Direccion(Integer altura, String calle, String localidad, String municipio, Provincia provincia) {
        this.altura = altura;
        this.calle = calle;
        this.localidad = localidad;
        this.municipio = municipio;
        this.provincia = provincia;
    }


    public String getMunicipio() {
        return municipio;
    }

    private String municipio;
    Provincia provincia;


    public String getProvinciaString() {
        return provincia.toString().replace("_"," ");
    }



    public Integer getAltura() {
        return altura;
    }



    public String getCalle() {
        return calle;
    }

    public String getLocalidad() {
        return localidad;
    }


}

