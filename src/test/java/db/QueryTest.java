package db;

import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Pais;
import models.entities.geoDDS.entidades.Provincia;
import models.entities.organizaciones.entidades.AgenteSectorial;
import models.entities.organizaciones.entidades.Organizacion;
import models.entities.organizaciones.entidades.Trabajador;
import models.entities.parametros.ParametroFE;
import models.entities.seguridad.cuentas.Permiso;
import models.entities.seguridad.cuentas.Usuario;
import models.repositories.*;
import models.repositories.factories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class QueryTest {

    RepositorioDeOrganizaciones repoOrg = FactoryRepositorioDeOrganizaciones.get();
    RepositorioDeMunicipios repoMun = FactoryRepositorioDeMunicipios.get();
    RepositorioDeTrabajadores repoTrabajadores = FactoryRepositorioDeTrabajadores.get();
    RepositorioDeAgentesSectoriales repoAgentes = FactoryRepositorioDeAgentesSectoriales.get();
    RepositorioDeParametrosFE repoParametrosFE = FactoryRepositorioDeParametrosFE.get();
    RepositorioDeUsuarios repoUsuarios = FactoryRepositorioDeUsuarios.get();

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
    @DisplayName("Se busca organizacion dada razon social")
    public void orgRazonSocial(){
        Organizacion org = repoOrg.buscarPorRazonSocial("Valve Corporation S.A");

        Assertions.assertEquals("Valve Corporation S.A",org.getRazonSocial());
        Assertions.assertEquals(1,org.getId());
        Assertions.assertEquals("Rivadavia",org.getDireccion().getCalle());
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


    @Test
    @DisplayName("Se busca municipio por nombre y por su provincia")
    public void municipioNombreYProvincia(){
        Municipio municipio = repoMun.buscarNombre("CABA","BUENOS AIRES");

        Assertions.assertEquals("CABA",municipio.getNombre());
        Assertions.assertEquals("BUENOS AIRES",municipio.getProvincia().getNombre());
        Assertions.assertEquals(1,municipio.getId());
    }

    @Test
    @DisplayName("Se busca trabajador")
    public void trabajador1(){
        Trabajador trabajador = repoTrabajadores.buscar(1);
        Assertions.assertEquals("Fernandez",trabajador.getApellido());
        Assertions.assertEquals(12345678,trabajador.getNroDoc());
    }

    @Test
    @DisplayName("Se busca agente sectorial")
    public void agente(){
        AgenteSectorial agente = repoAgentes.buscar(1);
        Assertions.assertEquals("Catalina",agente.getNombre());
        Assertions.assertEquals("Perez",agente.getApellido());
    }

    @Test
    @DisplayName("Se encuentra parametro por nombre")
    public void parametros(){
        ParametroFE auto = repoParametrosFE.buscarNombre("Auto");
        Assertions.assertEquals(0.2,auto.getValor());
        Assertions.assertEquals("Auto",auto.getNombre());
    }

    @Test
    @DisplayName("Se encuentra usuario por username y password")
    public void usuario(){
        Usuario usuario = repoUsuarios.buscar("juancito", "15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225");
        Assertions.assertEquals("juancito",usuario.getNombreDeUsuario());
        Assertions.assertEquals("15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225",usuario.getContrasenia());
        Assertions.assertTrue(usuario.tienePermiso(Permiso.VER_ORGANIZACION));
    }
}
