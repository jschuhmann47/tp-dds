package domain.organizaciones;

import domain.trayectos.Trayecto;
import java.util.List;


public class Trabajador {
    private String apellido;
    private String nombre;
    private TipoDoc tipoDoc;
    private Integer nroDoc;
    private List<Trayecto> listaTrayectos;
    public List<Sector> sectores;


}
