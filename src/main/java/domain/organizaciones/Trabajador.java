package domain.organizaciones;

import domain.trayectos.Trayecto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Trabajador {
    private String apellido;
    private String nombre;
    private TipoDoc tipoDoc;
    private Integer nroDoc;

    private List<Trayecto> listaTrayectos = new ArrayList<>();
    public List<Sector> sectores;

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

    public Double calcularHCAnual() throws Exception{
        return this.listaTrayectos.stream().mapToDouble(t-> {
            try {
                return t.calcularHCAnual();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }
    public Double calcularHCMensual() throws Exception{
        return this.listaTrayectos.stream().mapToDouble(t-> {
            try {
                return t.calcularHCMensual();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }


}
