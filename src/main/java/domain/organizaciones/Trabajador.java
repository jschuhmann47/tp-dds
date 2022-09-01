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
    @Enumerated(value = EnumType.STRING)
    private TipoDoc tipoDoc;
    @Column(name = "nro_doc")
    private Integer nroDoc;

    @OneToMany
    @JoinColumn(name = "trabajador_id",referencedColumnName = "id")
    private List<Trayecto> listaTrayectos = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "trabajador_sector",joinColumns = @JoinColumn(name = "trabajador_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "sector_id",referencedColumnName = "id"))
    public List<Sector> sectores;

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
