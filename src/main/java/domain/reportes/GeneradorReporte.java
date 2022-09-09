package domain.reportes;

import com.sun.org.apache.xpath.internal.operations.Or;
import domain.geoDDS.entidades.Municipio;
import domain.geoDDS.entidades.Pais;
import domain.geoDDS.entidades.Provincia;
import domain.organizaciones.Organizacion;

import java.util.ArrayList;
import java.util.List;

public class GeneradorReporte {

    public static Double HCTotalPorSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio){
        return organizaciones.stream().filter(o->o.getDireccion().getMunicipio() == municipio).mapToDouble(Organizacion::calcularHCTotal).sum();
    }

    public static Double HCTotalPorProvincia(List<Organizacion> organizaciones, Provincia provincia){
        return organizaciones.stream().filter(o->o.getDireccion().getProvincia() == provincia).mapToDouble(Organizacion::calcularHCTotal).sum();
    }

    public static Double HCTotalPorClasificacion(List<Organizacion> organizaciones, String clasificacion){
        return organizaciones.stream().filter(o->o.getClasificacionOrg().contains(clasificacion)).mapToDouble(Organizacion::calcularHCTotal).sum();
    }

    public static List<Composicion> ComposicionHCTotalPorSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio){


        Double porActividad = GeneradorReporte.HCTotalPorActividadSectorTerritorial(organizaciones, municipio);
        Double porTrabajador = GeneradorReporte.HCTotalPorTrabajadorSectorTerritorial(organizaciones, municipio);
        return GeneradorReporte.generarListaComposicion(porActividad,porTrabajador);
    }

    public static List<Composicion> ComposicionHCTotalDeUnaOrganizacion(Organizacion organizacion){
        Double porActividad = organizacion.calcularHCTotalActividades();
        Double porTrabajador = organizacion.calcularHCTotalTrabajadores();
        return GeneradorReporte.generarListaComposicion(porActividad,porTrabajador);
    }

    public static List<Composicion> ComposicionHCTotalPorProvincias(List<Organizacion> organizaciones, List<Provincia> provincias){
        List<Composicion> composicionesPorProv = new ArrayList<>();
        for(Provincia provincia : provincias){
            Double porActividad = GeneradorReporte.HCTotalPorProvincia(organizaciones, provincia);
            Double porTrabajador = GeneradorReporte.HCTotalPorProvincia(organizaciones, provincia);
            composicionesPorProv.addAll(GeneradorReporte.generarListaComposicionConProvincia(porActividad,porTrabajador,provincia));
        }
        return composicionesPorProv;
    }

    public static Double HCTotalPorActividadSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio){
        return organizaciones.stream().filter(o->o.getDireccion().getMunicipio() == municipio).mapToDouble(Organizacion::calcularHCTotalActividades).sum();
    }

    public static Double HCTotalPorTrabajadorSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio){
        return organizaciones.stream().filter(o->o.getDireccion().getMunicipio() == municipio).mapToDouble(Organizacion::calcularHCTotalTrabajadores).sum();
    }

    public static Double porcentaje(Double valor,Double total){
        return valor/(total)*100;
    }

    private static List<Composicion> generarListaComposicion(Double valorActividades, Double valorTrabajadores){
        List<Composicion> composicionList = new ArrayList<>();
        Composicion compActividades = new Composicion("Actividades",GeneradorReporte.porcentaje(valorActividades,valorActividades+valorTrabajadores));
        Composicion compTrabajadores = new Composicion("Trabajadores",GeneradorReporte.porcentaje(valorTrabajadores,valorActividades+valorTrabajadores));
        composicionList.add(compActividades);
        composicionList.add(compTrabajadores);
        return composicionList;
    }
    private static List<Composicion> generarListaComposicionConProvincia(Double valorActividades, Double valorTrabajadores, Provincia provincia){
        List<Composicion> composicionList = new ArrayList<>();
        Composicion compActividades = new Composicion("Actividades",GeneradorReporte.porcentaje(valorActividades,valorActividades+valorTrabajadores));
        Composicion compTrabajadores = new Composicion("Trabajadores",GeneradorReporte.porcentaje(valorTrabajadores,valorActividades+valorTrabajadores));
        composicionList.add(compActividades);
        composicionList.add(compTrabajadores);
        for (Composicion c : composicionList){
            c.setProvincia(provincia);
        }
        return composicionList;
    }
}
