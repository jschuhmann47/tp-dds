package domain.organizaciones;

import domain.locaciones.Direccion;
import java.util.ArrayList;

public class Organizacion{
    private String clasificacionOrg;
    private ArrayList<Trabajador> miembros;
    private String razonSocial;
    private ArrayList<Sector> sectores;
    private TipoOrganizacion tipoOrganizacion;
    private Direccion ubicacion;


}