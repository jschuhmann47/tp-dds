package domain.ModuloImportar;

public class FormularioDA {

    public String actividad;
    public TipoDeConsumo tipoDeConsumo;
    public Consumo consumo;
    public String periodoDeImputacion;

    public  FormularioDA(String actividad, TipoDeConsumo tipoDeConsumo,
                         Consumo consumo, String periodoDeImputacioon)
    {
        this.actividad = actividad;
        this.tipoDeConsumo = tipoDeConsumo;
        this.consumo = consumo;
        this.periodoDeImputacion = periodoDeImputacioon;
    }

}
