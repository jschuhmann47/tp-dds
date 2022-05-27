package domain.locaciones;

public class Direccion {
    private Integer altura;
    private String calle;
    private String localidad;

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

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

}

