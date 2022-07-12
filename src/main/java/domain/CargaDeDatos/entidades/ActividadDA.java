package domain.CargaDeDatos.entidades;

public class ActividadDA {

    public Actividad actividad;
    public TipoDeConsumo tipoDeConsumo;
    public Unidad unidad;
    public Periodo periodo;
    public Periodicidad periodicidad;
    public Double valor;




    public ActividadDA() {

    }


    public ActividadDA(Actividad actividad, TipoDeConsumo tipoDeConsumo, Unidad unidad, Periodo periodo, Periodicidad periodicidad, Double valor) {
        this.actividad = actividad;
        this.tipoDeConsumo = tipoDeConsumo;
        this.unidad = unidad;
        this.periodo = periodo;
        this.periodicidad = periodicidad;
        this.valor = valor;
    }
}
