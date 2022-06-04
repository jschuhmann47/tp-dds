package domain.geoDDS.entidades;

public class Provincia {

    public int id;
    public String nombre;
    public Pais pais;

    public Provincia(){

    }
    public Provincia(int idP, String nombreP, Pais paisP){
        this.id = idP;
        this.nombre = nombreP;
        this.pais=paisP;
    }

    public int getId() {
        return id;
    }
}
