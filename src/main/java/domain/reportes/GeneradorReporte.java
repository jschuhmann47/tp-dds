package domain.reportes;

import domain.CargaDeActividades.entidades.Periodo;
import domain.geoDDS.entidades.Municipio;
import domain.geoDDS.entidades.Provincia;
import domain.organizaciones.entidades.Organizacion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneradorReporte {

    public static List<Organizacion> organizacionesEnMunicipio(List<Organizacion> organizaciones, Municipio municipio){
        return organizaciones.stream()
                .filter(o->o.getDireccion().getMunicipio().getId() == municipio.getId())
                .collect(Collectors.toList());
    }
    public static List<Organizacion> organizacionesEnProvinicia(List<Organizacion> organizaciones, Provincia provincia){
        return organizaciones.stream()
                .filter(o->o.getDireccion().getProvincia().getId() == provincia.getId()).collect(Collectors.toList());
    }


    public static Double HCTotalPorSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio){
        return GeneradorReporte.organizacionesEnMunicipio(organizaciones,municipio).stream()
                .mapToDouble(Organizacion::calcularHCTotal)
                .sum();
    }


    public static Double HCTotalPorSectorTerritorialEnPeriodo(List<Organizacion> organizaciones, Municipio municipio, Periodo periodo){
        return GeneradorReporte.organizacionesEnMunicipio(organizaciones,municipio).stream()
                .mapToDouble(organizacion -> {
                    try {
                        return organizacion.calcularHCEnPeriodo(periodo);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).sum();
    }

    public static Double HCTotalPorProvincia(List<Organizacion> organizaciones, Provincia provincia){
        return GeneradorReporte.organizacionesEnProvinicia(organizaciones,provincia).stream()
                .mapToDouble(Organizacion::calcularHCTotal).sum();
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
            Double porActividad = GeneradorReporte.HCTotalPorActividadProvincia(organizaciones, provincia);
            Double porTrabajador = GeneradorReporte.HCTotalPorTrabajadorProvincia(organizaciones, provincia);
            composicionesPorProv.addAll(GeneradorReporte.generarListaComposicionConProvincia(porActividad,porTrabajador,provincia));
        }
        return composicionesPorProv;
    }

    public static Double HCTotalPorActividadProvincia(List<Organizacion> organizaciones, Provincia provincia){
        return GeneradorReporte.organizacionesEnProvinicia(organizaciones,provincia).stream()
                .mapToDouble(Organizacion::calcularHCTotalActividades).sum();
    }
    public static Double HCTotalPorTrabajadorProvincia(List<Organizacion> organizaciones, Provincia provincia){
        return GeneradorReporte.organizacionesEnProvinicia(organizaciones,provincia).stream()
                .mapToDouble(Organizacion::calcularHCTotalTrabajadores).sum();
    }


    public static Double HCTotalPorActividadSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio){
        return GeneradorReporte.organizacionesEnMunicipio(organizaciones,municipio).stream()
                .mapToDouble(Organizacion::calcularHCTotalActividades).sum();
    }

    public static Double HCTotalPorTrabajadorSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio){
        return GeneradorReporte.organizacionesEnMunicipio(organizaciones,municipio).stream()
                .mapToDouble(Organizacion::calcularHCTotalTrabajadores).sum();
    }

    public static Double porcentaje(Double valor,Double total){
        return valor/(total)*100;
    }

    private static List<Composicion> generarListaComposicion(Double valorActividades, Double valorTrabajadores){
        List<Composicion> composicionList = new ArrayList<>();
        Composicion compActividades =
                new Composicion("Actividades",GeneradorReporte.porcentaje(valorActividades,valorActividades+valorTrabajadores));
        Composicion compTrabajadores =
                new Composicion("Trabajadores",GeneradorReporte.porcentaje(valorTrabajadores,valorActividades+valorTrabajadores));
        composicionList.add(compActividades);
        composicionList.add(compTrabajadores);
        return composicionList;
    }


    private static List<Composicion> generarListaComposicionConProvincia(Double valorActividades, Double valorTrabajadores, Provincia provincia){
        List<Composicion> composicionList = GeneradorReporte.generarListaComposicion(valorActividades,valorTrabajadores);
        for (Composicion c : composicionList){
            c.setProvincia(provincia);
        }
        return composicionList;
    }


    public static Double evolucionHCTotalSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio, Periodo periodo){

        Double HCMesAnterior = GeneradorReporte.HCTotalPorSectorTerritorialEnPeriodo(organizaciones,municipio,periodo.obtenerPeriodoAnterior());
        Double HCMesActual = GeneradorReporte.HCTotalPorSectorTerritorialEnPeriodo(organizaciones,municipio,periodo);

        return GeneradorReporte.evolucionPorcuentual(HCMesAnterior, HCMesActual);
    }


    public static Double evolucionHCTotalOrganizacion(Organizacion organizacion, Periodo periodo) throws Exception {
        Double HCMesAnterior = organizacion.calcularHCEnPeriodo(periodo.obtenerPeriodoAnterior());
        Double HCMesActual = organizacion.calcularHCEnPeriodo(periodo);

        return GeneradorReporte.evolucionPorcuentual(HCMesAnterior, HCMesActual);
    }


    private static Double evolucionPorcuentual(Double valorAnterior,Double valorActual){
        return (valorActual-valorAnterior)/valorAnterior * 100;
    }
}
