package test.domain.reportes;

import models.entities.CargaDeActividades.entidades.*;
import models.entities.calculoHC.CalculoHC;
import models.entities.calculoHC.UnidadHC;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.ServicioCalcularDistancia;
import models.entities.geoDDS.adapters.ServicioGeoDDSAdapter;
import models.entities.geoDDS.entidades.*;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Sector;
import models.entities.organizaciones.entidades.TipoOrganizacion;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.parametros.ParametroFE;
import models.entities.reportes.GeneradorReporte;
import models.entities.reportes.Reporte;
import models.entities.transporte.TipoCombustible;
import models.entities.transporte.privado.TipoVehiculo;
import models.entities.transporte.privado.TransportePrivado;
import models.entities.transporte.publico.Linea;
import models.entities.transporte.publico.Parada;
import models.entities.transporte.publico.TransportePublico;
import models.entities.trayectos.Frecuencia;
import models.entities.trayectos.Tramo;
import models.entities.trayectos.Trayecto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeneradorReporteTest {

    ServicioGeoDDSAdapter adapterMock;
    Trabajador juan = new Trabajador();
    Trabajador pepe = new Trabajador();
    Trabajador carlos = new Trabajador();
    Trabajador luis = new Trabajador();

    Sector marketing = new Sector();
    Sector rrhh = new Sector();

    List<Trabajador> trabajadoresA = new ArrayList<>();
    List<Trabajador> trabajadoresB = new ArrayList<>();

    List<Sector> sectoresA = new ArrayList<>();
    List<Sector> sectoresB = new ArrayList<>();

    Organizacion organizacionA;
    Organizacion organizacionB;
    Organizacion organizacionC;

    Pais pais = new Pais(1,"A");
    Provincia provincia = new Provincia(1,"A",pais);
    Provincia provincia2 = new Provincia(2,"AA",pais);
    Municipio municipio = new Municipio(1,"A",provincia);
    Municipio municipio2 = new Municipio(2,"AA",provincia2);

    Localidad localidad = new Localidad(1,"A",1,municipio);
    Localidad localidad2 = new Localidad(2,"AA",2,municipio2);

    Direccion direccion1 = new Direccion(100,"Rivadavia",localidad);
    Direccion direccion2 = new Direccion(4000,"Corrientes",localidad);
    Direccion direccion3 = new Direccion(2300,"Mozart",localidad);
    Direccion direccion4 = new Direccion(420,"Abril",localidad2);

    Distancia distancia1 = new Distancia(10.0,"KM");
    Distancia distancia2 = new Distancia(12.0,"KM");

    Parada paradaTest1 = new Parada(distancia1,distancia2,direccion2);
    Parada paradaTest2 = new Parada(distancia2,distancia1,direccion3);


    TransportePrivado auto = new TransportePrivado(TipoVehiculo.AUTO, TipoCombustible.NAFTA);
    Linea linea7 = new Linea("Linea 7", paradaTest1,paradaTest2);
    TransportePublico colectivoTest = new TransportePublico(linea7,TipoVehiculo.COLECTIVO,TipoCombustible.NAFTA);

    List<Organizacion> organizaciones;

    public GeneradorReporteTest() throws IOException {
    }

    @BeforeEach
    public void init() throws Exception {
        ParametroFE autoFE = new ParametroFE(TipoVehiculo.AUTO.toString(),40.0);
        ParametroFE gasFE = new ParametroFE(TipoDeConsumo.GAS_NATURAL.toString(),0.6);
        ParametroFE diesel = new ParametroFE(TipoDeConsumo.DIESEL.toString(),0.8);
        ParametroFE colectivo = new ParametroFE(TipoVehiculo.COLECTIVO.toString(),25.0);
        List<ParametroFE> parametrosFE = new ArrayList<>();
        parametrosFE.add(autoFE);
        parametrosFE.add(gasFE);
        parametrosFE.add(diesel);
        parametrosFE.add(colectivo);

        CalculoHC.setFactoresEmisionFE(parametrosFE);
        CalculoHC.setUnidadPorDefecto(UnidadHC.GRAMO_EQ);

        this.adapterMock = mock(ServicioGeoDDSAdapter.class);
        ServicioCalcularDistancia.setAdapter(this.adapterMock);

        when(this.adapterMock.distanciaEntre(direccion1,direccion2)).thenReturn(distancia1);
        Tramo tramoAuto = new Tramo(auto,direccion1,direccion2);

        when(this.adapterMock.distanciaEntre(direccion2,direccion3)).thenReturn(distancia2);
        Tramo tramoColectivo = new Tramo(colectivoTest,direccion2,direccion3);

        List<Tramo> listaTramos = new ArrayList<>();
        listaTramos.add(tramoAuto);
        listaTramos.add(tramoColectivo);

        Frecuencia frecuencia = new Frecuencia(Periodicidad.MENSUAL,8);

        paradaTest1.setParadaSiguiente(paradaTest2);
        paradaTest2.setParadaSiguiente(null);

        sectoresA.add(marketing);
        marketing.nombreSector = "Marketing";
        sectoresB.add(rrhh);
        rrhh.nombreSector = "RR.HH";

        juan.sectores= new ArrayList<>();
        juan.sectores.add(marketing); //trabaja empresa A
        trabajadoresA.add(juan);
        trabajadoresB.add(juan);

        pepe.sectores= new ArrayList<>();
        pepe.sectores.add(marketing); //trabaja empresa A
        trabajadoresA.add(pepe);

        carlos.sectores= new ArrayList<>();
        carlos.sectores.add(rrhh); //trabaja empresa B
        trabajadoresB.add(carlos);

        luis.sectores= new ArrayList<>();
        luis.sectores.add(marketing); //trabaja empresa A y B
        luis.sectores.add(rrhh);
        trabajadoresB.add(luis);
        trabajadoresA.add(luis);

        marketing.trabajadores = new ArrayList<>();
        marketing.trabajadores.add(juan);
        marketing.trabajadores.add(pepe);
        marketing.trabajadores.add(luis);

        rrhh.trabajadores = new ArrayList<>();
        rrhh.trabajadores.add(carlos);
        rrhh.trabajadores.add(luis);

        marketing.organizacion = organizacionA;
        rrhh.organizacion = organizacionB;

        List<String> clasificaciones = new ArrayList<>();
        clasificaciones.add("Videojuegos");
        organizacionA = new Organizacion(clasificaciones,
                "Valve Corporation S.A",sectoresA, TipoOrganizacion.EMPRESA,direccion1);
        organizacionB = new Organizacion(clasificaciones,
                "Respawn Entretainment S.A",sectoresB, TipoOrganizacion.EMPRESA,direccion2);
        organizacionC = new Organizacion(clasificaciones,
                "Renegade Corporation S.A",sectoresA, TipoOrganizacion.ONG,direccion4);


        auto.agregarTrabajadorATramoCompartido(juan);
        Trayecto trayectoTest = new Trayecto(direccion1,direccion3,listaTramos,frecuencia);

        trayectoTest.cargarTramos(tramoAuto,tramoColectivo);
        juan.agregarTrayectos(trayectoTest);
        trayectoTest.registrarViajesEnMesYAnio(12,2020,30);
        trayectoTest.registrarViajesEnMesYAnio(1,2021,30);
        List<Tramo> tramosB = new ArrayList<>();
        tramosB.add(tramoColectivo);
        Trayecto trayectoTestB = new Trayecto(direccion2,direccion3,tramosB,frecuencia);
        trayectoTestB.registrarViajesEnMesYAnio(12,2020,20);
        trayectoTestB.registrarViajesEnMesYAnio(1,2021,20);
        juan.agregarTrayectos(trayectoTestB);

        Periodo periodo = new Periodo(1,2021);
        List<Actividad> actividades = new ArrayList<>();

        Actividad gas = new Actividad(TipoActividad.COMBUSTION_FIJA, TipoDeConsumo.DIESEL, Unidad.M3,
                periodo,Periodicidad.MENSUAL,5000.0);
        Actividad carbono = new Actividad(TipoActividad.COMBUSTION_FIJA, TipoDeConsumo.DIESEL,Unidad.M3,
                periodo.obtenerPeriodoAnterior(),Periodicidad.MENSUAL,25.0);
        Actividad gasToxico = new Actividad(TipoActividad.COMBUSTION_FIJA, TipoDeConsumo.GAS_NATURAL,Unidad.M3,
                periodo,Periodicidad.MENSUAL,30000.0);
        CalculoHC.calcularHCDeActividad(gasToxico);
        actividades.add(gas);
        actividades.add(carbono);
        for(Actividad a : actividades){
            CalculoHC.calcularHCDeActividad(a);
        }

        organizacionA.setListaDeActividades(actividades);
        organizacionB.setListaDeActividades(new ArrayList<>());
        organizacionB.getListaDeActividades().add(gas);
        organizacionC.setListaDeActividades(new ArrayList<>());
        organizacionC.getListaDeActividades().add(gasToxico);

        organizaciones = new ArrayList<>();
        organizaciones.add(organizacionA);
        organizaciones.add(organizacionB);
        organizaciones.add(organizacionC);


    }


    @Test
    @DisplayName("Se genera el HC total de un sector territorial")
    public void HCTotalSectorMunicipio(){

        Assertions.assertEquals(104020.0,GeneradorReporte.HCTotalPorSectorTerritorial(organizaciones,municipio).getValor());
        //System.out.println(GeneradorReporte.HCTotalPorSectorTerritorial(organizaciones,municipio).generarLeyenda());
    }

    @Test
    @DisplayName("Se genera el HC total por la clasificacion")
    public void HCTotalSector(){
        String clasificacion = "Videojuegos";
        Assertions.assertEquals(218020.0,GeneradorReporte.HCTotalPorClasificacion(organizaciones,clasificacion).getValor());
    }

    @Test
    @DisplayName("Se genera la composicion de HC total de un sector territorial")
    public void composicionHCTotalSector(){
        List<Reporte> composicionList = GeneradorReporte.ComposicionHCTotalPorSectorTerritorial(organizaciones,municipio);
        Assertions.assertEquals(8.0,Math.round(composicionList.get(0).getValor()));
        Assertions.assertEquals(92.0,Math.round(composicionList.get(1).getValor()));
    }

    @Test
    @DisplayName("Se genera la composicion de un pais")
    public void composicionHCTotalProvincias(){
        List<Provincia> provincias = new ArrayList<>();
        provincias.add(provincia);
        provincias.add(provincia2);
        List<Reporte> composicionList = GeneradorReporte.ComposicionHCTotalPorProvincias(organizaciones,provincias);
        System.out.println(composicionList);
        //provincia1
        Assertions.assertEquals(8.0, Math.round(composicionList.get(0).getValor()));
        Assertions.assertEquals(92.0,Math.round(composicionList.get(1).getValor()));
        //provincia2
        Assertions.assertEquals(16.0,Math.round(composicionList.get(2).getValor()));
        Assertions.assertEquals(84.0,Math.round(composicionList.get(3).getValor()));
    }

    @Test
    @DisplayName("Se genera la composicion de una organizacion")
    public void composicionHCTotalOrganizacion(){
        List<Reporte> composicionList = GeneradorReporte.ComposicionHCTotalDeUnaOrganizacion(organizacionA);
        Assertions.assertEquals(4.02,Math.round(composicionList.get(0).getValor()*100.0)/100.0);
        Assertions.assertEquals(95.98,Math.round(composicionList.get(1).getValor()*100.0)/100.0);
    }

    @Test
    @DisplayName("Se genera la evolucion en un sector territorial")
    public void evolucionSectorT(){
        Assertions.assertEquals(59,
                Math.round(GeneradorReporte.evolucionHCTotalSectorTerritorial(organizaciones,municipio,new Periodo(1,2021)).getValor()));
    }

    @Test
    @DisplayName("Se genera la evolucion en una organizacion")
    public void evolucionOrganizacion() throws Exception {
        Assertions.assertEquals(29.22,
                Math.round(GeneradorReporte.evolucionHCTotalOrganizacion(organizacionA,new Periodo(1,2021)).getValor()*100.0)/100.0);
    }




}
