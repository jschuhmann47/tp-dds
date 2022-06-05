package domain.organizaciones;

import domain.geoDDS.Direccion;
import java.util.List;

public class Organizacion{
    private String clasificacionOrg;
    private List<Trabajador> miembros;
    private String razonSocial;
    private List<Sector> sectores;
    private TipoOrganizacion tipoOrganizacion;
    private Direccion ubicacion;


    public void cargarDatos(){
        //TODO lo del excel
    }
}