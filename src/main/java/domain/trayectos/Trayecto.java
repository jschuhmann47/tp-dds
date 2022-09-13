package domain.trayectos;

import domain.CargaDeActividades.entidades.Periodo;
import domain.geoDDS.Direccion;
import domain.geoDDS.entidades.Distancia;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "trayecto")
public class Trayecto {
    @Id
    @GeneratedValue
    private int id;

    @Embedded
    @AssociationOverride(name = "localidad",joinColumns = @JoinColumn(name = "localidad_inicio_id"))
    @AttributeOverrides({@AttributeOverride(name="altura",column = @Column(name = "altura_inicio")),
                        @AttributeOverride(name = "calle",column = @Column(name = "calle_inicio"))})
    private Direccion puntoDeSalida;
    @Embedded
    @AssociationOverride(name = "localidad",joinColumns = @JoinColumn(name = "localidad_fin_id"))
    @AttributeOverrides({@AttributeOverride(name="altura",column = @Column(name = "altura_fin")),
                        @AttributeOverride(name = "calle",column = @Column(name = "calle_fin"))})
    private Direccion puntoDeLlegada;
    @Embedded
    public Frecuencia frecuencia;

    @Getter
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "trayecto_id",referencedColumnName = "id")
    private List<ViajeRealizado> viajesRealizados;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "trayecto_id",referencedColumnName = "id")
    private List<Tramo> tramos;

    @Embedded
    private Distancia distanciaTrayecto;

    public Trayecto() {
    }

    public Trayecto(Direccion puntoDeSalida, Direccion puntoDeLlegada, List<Tramo> tramos, Frecuencia frecuencia) {
        this.puntoDeSalida = puntoDeSalida;
        this.puntoDeLlegada = puntoDeLlegada;
        this.tramos = tramos;
        this.frecuencia = frecuencia;
        distanciaTrayecto = this.distanciaTrayecto();
        this.viajesRealizados = new ArrayList<>();
    }

    public Distancia getDistanciaTrayecto() {
        return distanciaTrayecto;
    }

    public List<Tramo> getTramos() {
        return tramos;
    }



    public void cargarTramos(Tramo ... tramos){
        this.tramos.addAll(Arrays.asList(tramos));
    }

    public Distancia distanciaTrayecto(){

        double valor = tramos.stream().map(tramo -> {
                    try {
                        return tramo.getDistancia().valor;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    })
                    .mapToDouble(i->i).sum();
        return new Distancia(valor,"KM");
    }

    public Double calcularHC(Periodo periodo) {
        return this.tramos.stream()
                .mapToDouble(t-> {
                    try {
                        return t.calcularHC()*this.frecuencia.vecesPorMes(periodo);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
    }

    public void registrarViajesEnMesYAnio(Integer mes, Integer anio, int cantidadASumar){
        Optional<ViajeRealizado> posibleViaje =
                this.viajesRealizados
                        .stream()
                        .filter(v -> Objects.equals(v.getAnio(), anio) && Objects.equals(v.getMes(), mes))
                        .findFirst();
        if(posibleViaje.isPresent()){
            posibleViaje.get().sumarViajes(cantidadASumar);
        }
        else{
            ViajeRealizado viajeNuevo = new ViajeRealizado(mes,anio);
            viajeNuevo.sumarViajes(cantidadASumar);
            this.viajesRealizados.add(viajeNuevo);
        }
    }

    public Double calcularHCTotal(){
        return this.tramos.stream().mapToDouble(Tramo::calcularHC).sum() * this.vecesRealizadas();
    }

    public int vecesRealizadas(){
        return this.getViajesRealizados().stream().mapToInt(ViajeRealizado::getVecesRealizadaEnMes).sum();
    }


}
