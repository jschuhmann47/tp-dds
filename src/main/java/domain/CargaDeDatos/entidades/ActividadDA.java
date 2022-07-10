package domain.CargaDeDatos.entidades;

public class ActividadDA {

    public Actividad actividad;
    public TipoDeConsumo tipoDeConsumo;
    public Unidad unidad;
    public Periodicidad periodicidad;
    public Double valor;
    public Integer mes; //si es anual va null
    public Integer anio;



    public ActividadDA() {

    }

    public ActividadDA(Actividad actividad, TipoDeConsumo tipoDeConsumo, Unidad unidad, Periodicidad periodicidad, Double valor, Integer mes, Integer anio) {
        this.actividad = actividad;
        this.tipoDeConsumo = tipoDeConsumo;
        this.unidad = unidad;
        this.periodicidad = periodicidad;
        this.valor = valor;
        this.mes = mes;
        this.anio = anio;
    }
}
