package domain.reportes;

import domain.CargaDeActividades.entidades.Periodo;
import domain.geoDDS.entidades.Municipio;
import domain.geoDDS.entidades.Provincia;
import domain.organizaciones.entidades.Organizacion;

import java.util.ArrayList;
import java.util.Arrays;
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


    public static Reporte HCTotalPorSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio){
        Double valor = GeneradorReporte.organizacionesEnMunicipio(organizaciones,municipio).stream()
                .mapToDouble(Organizacion::calcularHCTotal)
                .sum();
        return new Reporte("Huella de carbono del Municipio " + municipio.getNombre(),valor);
    }


    public static Reporte HCTotalPorSectorTerritorialEnPeriodo(List<Organizacion> organizaciones, Municipio municipio, Periodo periodo){
        Double valor = GeneradorReporte.organizacionesEnMunicipio(organizaciones,municipio).stream()
                .mapToDouble(organizacion -> {
                    try {
                        return organizacion.calcularHCEnPeriodo(periodo);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).sum();
        return new Reporte("Huella de carbono del Municipio en el periodo: " + periodo.generarLeyenda(),valor);
    }

    public static Reporte HCTotalPorProvincia(List<Organizacion> organizaciones, Provincia provincia){
        Double valor = GeneradorReporte.organizacionesEnProvinicia(organizaciones,provincia).stream()
                .mapToDouble(Organizacion::calcularHCTotal).sum();
        return new Reporte("Huella de carbono de la provincia " + provincia.getNombre(),valor);
    }

    public static Reporte HCTotalPorClasificacion(List<Organizacion> organizaciones, String clasificacion){
        Double valor =  organizaciones.stream()
                .filter(o->o.getClasificacionOrg().contains(clasificacion))
                .mapToDouble(Organizacion::calcularHCTotal).sum();
        return new Reporte("Huella de carbono de organizaciones con la clasificacion "
                + "\"" + clasificacion + "\"",valor); //TODO hacer tabla de clasificacion para normalizacion?

    }

    public static List<Reporte> ComposicionHCTotalPorSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio){

        Reporte porActividad = GeneradorReporte.HCTotalPorActividadSectorTerritorial(organizaciones, municipio);
        Reporte porTrabajador = GeneradorReporte.HCTotalPorTrabajadorSectorTerritorial(organizaciones, municipio);
        return GeneradorReporte.calcularPorcentajes(porActividad,porTrabajador,"municipio: " + municipio.getNombre());
    }

    public static List<Reporte> ComposicionHCTotalDeUnaOrganizacion(Organizacion organizacion){
        return GeneradorReporte
                .calcularPorcentajes(GeneradorReporte.ComposicionHCTotalDeUnaOrganizacionPorActividad(organizacion),
                                    GeneradorReporte.ComposicionHCTotalDeUnaOrganizacionPorTrabajador(organizacion),
                                    "organizacion: " + organizacion.getRazonSocial());
    }

    public static Reporte ComposicionHCTotalDeUnaOrganizacionPorActividad(Organizacion organizacion){
        return new Reporte("Huella de carbono por actividades de la organizacion "
                + organizacion.getRazonSocial(),organizacion.calcularHCTotalActividades());
    }

    public static Reporte ComposicionHCTotalDeUnaOrganizacionPorTrabajador(Organizacion organizacion){
        return new Reporte("Huella de carbono por actividades de la organizacion "
                + organizacion.getRazonSocial(),organizacion.calcularHCTotalTrabajadores());
    }

    public static List<Reporte> ComposicionHCTotalPorProvincias(List<Organizacion> organizaciones, List<Provincia> provincias){
        List<Reporte> reportesList = new ArrayList<>();
        for(Provincia provincia : provincias){
            Reporte porActividad = GeneradorReporte.HCTotalPorActividadProvincia(organizaciones, provincia);
            Reporte porTrabajador = GeneradorReporte.HCTotalPorTrabajadorProvincia(organizaciones, provincia);
            reportesList.addAll(GeneradorReporte.calcularPorcentajes(porActividad,porTrabajador,"provincia: " + provincia.getNombre()));
        }
        return reportesList;
    }

    public static Reporte HCTotalPorActividadProvincia(List<Organizacion> organizaciones, Provincia provincia){
        Double valor = GeneradorReporte.organizacionesEnProvinicia(organizaciones,provincia).stream()
                .mapToDouble(Organizacion::calcularHCTotalActividades).sum();
        return new Reporte("Huella de carbono por actividades en la provincia " + provincia.getNombre(),valor);
    }
    public static Reporte HCTotalPorTrabajadorProvincia(List<Organizacion> organizaciones, Provincia provincia){
        Double valor = GeneradorReporte.organizacionesEnProvinicia(organizaciones,provincia).stream()
                .mapToDouble(Organizacion::calcularHCTotalTrabajadores).sum();
        return new Reporte("Huella de carbono por trabajadores en la provincia " + provincia.getNombre(),valor);
    }


    public static Reporte HCTotalPorActividadSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio){
        Double valor = GeneradorReporte.organizacionesEnMunicipio(organizaciones,municipio).stream()
                .mapToDouble(Organizacion::calcularHCTotalActividades).sum();
        return new Reporte("Huella de carbono por actividades en el municipio " + municipio.getNombre(),valor);
    }

    public static Reporte HCTotalPorTrabajadorSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio){
        Double valor = GeneradorReporte.organizacionesEnMunicipio(organizaciones,municipio).stream()
                .mapToDouble(Organizacion::calcularHCTotalTrabajadores).sum();
        return new Reporte("Huella de carbono por trabajadores en el municipio " + municipio.getNombre(),valor);
    }

    public static Double porcentaje(Double valor,Double total){
        return valor/(total)*100;
    }

    private static List<Reporte> generarListaReportes(Reporte ... reportes){
        return new ArrayList<>(Arrays.asList(reportes));
    }

    private static List<Reporte> calcularPorcentajes(Reporte actividades, Reporte trabajadores,String detalle){ //todo refactor
        Double actividadesP, trabajadoresP, total = actividades.getValor()+trabajadores.getValor();
        actividadesP = GeneradorReporte.porcentaje(actividades.getValor(),total);
        trabajadoresP = GeneradorReporte.porcentaje(trabajadores.getValor(),total);
        Reporte actividadesR = new Reporte("Porcentaje de la huella de carbono de actividades de " + detalle,actividadesP);
        Reporte trabajadoresR = new Reporte("Porcentaje de la huella de carbono de trabajadores de " + detalle,trabajadoresP);
        return GeneradorReporte.generarListaReportes(actividadesR,trabajadoresR);

    }

    public static Reporte evolucionHCTotalSectorTerritorial(List<Organizacion> organizaciones, Municipio municipio, Periodo periodo){

        Reporte HCMesAnterior = GeneradorReporte.HCTotalPorSectorTerritorialEnPeriodo(organizaciones,municipio,periodo.obtenerPeriodoAnterior());
        Reporte HCMesActual = GeneradorReporte.HCTotalPorSectorTerritorialEnPeriodo(organizaciones,municipio,periodo);

        return GeneradorReporte.evolucionPorcuentual(HCMesAnterior, HCMesActual,periodo,"municipio " + municipio.getNombre());
    }


    public static Reporte evolucionHCTotalOrganizacion(Organizacion organizacion, Periodo periodo) throws Exception {
        Reporte HCMesAnterior = new Reporte("_",organizacion.calcularHCEnPeriodo(periodo.obtenerPeriodoAnterior()));
        Reporte HCMesActual = new Reporte("_",organizacion.calcularHCEnPeriodo(periodo));

        return GeneradorReporte.evolucionPorcuentual(HCMesAnterior, HCMesActual,periodo,"organizacion " + organizacion.getRazonSocial());
    }


    private static Reporte evolucionPorcuentual(Reporte reporteAnterior,Reporte reporteActual,Periodo periodo, String detalle){
        return new Reporte("Evolucion porcuentual del periodo: " + periodo.generarLeyenda() + " de: " + detalle,
                (reporteActual.getValor()-reporteAnterior.getValor()) / reporteAnterior.getValor() * 100);
    }
}
