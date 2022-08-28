package domain.organizaciones;

import domain.CargaDeDatos.entidades.Periodo;
import domain.trayectos.Trayecto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "trabajador")
public class Trabajador {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "apellido")
    private String apellido;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo_doc")
    private TipoDoc tipoDoc;
    @Column(name = "nro_doc")
    private Integer nroDoc;

    @Transient
    private List<Trayecto> listaTrayectos = new ArrayList<>();

    @Transient
    public List<Sector> sectores; //cambiar en el uml si queda asi

    public int getId() {
        return id;
    }

    public void solicitarVinculacion(Organizacion organizacion,Sector sector){
        organizacion.solicitudDeVinculacion(this, sector);
    }

    public void solicitudAceptada(Sector sector){
        sectores.add(sector);
    };

    public List<Trayecto> getListaTrayectos() {
        return listaTrayectos;
    }

    public void setListaTrayectos(List<Trayecto> listaTrayectos) {
        this.listaTrayectos = listaTrayectos;
    }

    public void agregarTrayectos(Trayecto... trayectos){
        listaTrayectos.addAll(Arrays.asList(trayectos));
    }

    public Double calcularHC(Periodo periodo) throws Exception{
        return this.listaTrayectos.stream().mapToDouble(t-> {
            try {
                return t.calcularHC(periodo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }



}
