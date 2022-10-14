package db;

import models.controllers.ReporteController;
import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Pais;
import models.entities.geoDDS.entidades.Provincia;
import models.entities.organizaciones.entidades.Organizacion;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ReporteTest {

    RepositorioDeOrganizaciones repoOrg = FactoryRepositorioDeOrganizaciones.get();

    @Test
    @DisplayName("Se encuentra una organizacion dado la clasificacion")
    public void clasificacion(){
        List<Organizacion> orgs = repoOrg.buscarTodosClasificacion("Videojuegos");
        Assertions.assertEquals("Valve Corporation S.A",orgs.get(0).getRazonSocial());
    }

    @Test
    @DisplayName("Se encuentra una organizacion dado el municipio")
    public void municipio(){
        Pais pais = new Pais(1,"ARGENTINA");
        Provincia provincia = new Provincia(1,"BUENOS AIRES",pais);
        Municipio municipio = new Municipio(1,"CABA",provincia);

        List<Organizacion> orgs = repoOrg.buscarTodosDeMunicipio(municipio);
        Assertions.assertEquals("Valve Corporation S.A",orgs.get(0).getRazonSocial());
    }

    @Test
    @DisplayName("No se encuentra ninguna organizacion si no tiene la clasificacion")
    public void clasificacionNula(){
        List<Organizacion> orgs = repoOrg.buscarTodosClasificacion("Arte abstracto");
        Assertions.assertTrue(orgs.isEmpty());
    }

    @Test
    @DisplayName("No se encuentra ninguna organizacion de un municipio sin organizaciones")
    public void municipioNulo(){
        Pais pais = new Pais(1,"ARGENTINA");
        Provincia provincia = new Provincia(99,"MISIONES",pais);
        Municipio municipio = new Municipio(99,"CATARATAS DEL IGUAZU",provincia);
        List<Organizacion> orgs = repoOrg.buscarTodosDeMunicipio(municipio); //compara por id
        Assertions.assertTrue(orgs.isEmpty());
    }
}
