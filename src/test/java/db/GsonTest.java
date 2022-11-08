package db;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.entities.geoDDS.Direccion;
import models.entities.geoDDS.ServicioCalcularDistancia;
import models.entities.geoDDS.adapters.ServicioGeoDDSAdapter;
import models.entities.geoDDS.entidades.Distancia;
import models.entities.geoDDS.entidades.Provincia;
import models.entities.transporte.MedioTransporte;
import models.entities.transporte.TipoCombustible;
import models.entities.transporte.privado.TipoVehiculo;
import models.entities.transporte.privado.TransportePrivado;
import models.entities.trayectos.Tramo;
import models.entities.trayectos.TramoCompartido;
import models.helpers.deserializers.MedioTransporteDeserializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GsonTest {
    @Test
    @DisplayName("Se pasa una clase a JSON")
    public void json() throws Exception {

        ServicioGeoDDSAdapter adapterMock;
        adapterMock = mock(ServicioGeoDDSAdapter.class);
        ServicioCalcularDistancia.setAdapter(adapterMock);
        Direccion direccion1 = new Direccion(1,"hola",null);
        Direccion direccion2 = new Direccion(2,"hola",null);

        when(adapterMock.distanciaEntre(direccion1,direccion2)).thenReturn(new Distancia(1.0,"KM"));
        Gson gson = new Gson();
        Tramo tramo = new Tramo
                (new TransportePrivado(TipoVehiculo.AUTO, TipoCombustible.NAFTA,new TramoCompartido())
                        ,direccion1
                        ,direccion2);
        Assertions.assertEquals("{\"id\":0,\"medioTransporte\":{\"tramoCompartido\":{\"id\":0,\"orgPosibles\":[],\"personasABordo\":[]}," +
                "\"id\":0,\"tipo\":\"AUTO\",\"tipoCombustible\":\"NAFTA\"},\"puntoInicio\":{\"altura\":1," +
                "\"calle\":\"hola\"},\"puntoFinal\":{\"altura\":2,\"calle\":\"hola\"}," +
                "\"distanciaTramo\":{\"valor\":1.0,\"unidad\":\"KM\"}}",gson.toJson(tramo));
    }

    @Test
    @DisplayName("Se instancia un objeto dado un JSON")
    public void instanciar(){
        Type tipoTramo = new TypeToken<Tramo>() {}.getType();
        //Abstract classes can't be instantiated! Register an InstanceCreator or a TypeAdapter for this type.

        MedioTransporteDeserializer medioDeserializer = new MedioTransporteDeserializer("tipo");
        medioDeserializer.agregarMedioTransporte("AUTO", TransportePrivado.class);

        Gson gson = new GsonBuilder().registerTypeAdapter(MedioTransporte.class, medioDeserializer).create();

        Tramo tramo = gson.fromJson("{\"id\":0,\"medioTransporte\":{\"tramoCompartido\":{\"id\":0,\"orgPosibles\":[],\"personasABordo\":[]}," +
                "\"id\":0,\"tipo\":\"AUTO\",\"tipoCombustible\":\"NAFTA\"},\"puntoInicio\":{\"altura\":1," +
                "\"calle\":\"hola\"},\"puntoFinal\":{\"altura\":2,\"calle\":\"hola\"}," +
                "\"distanciaTramo\":{\"valor\":1.0,\"unidad\":\"KM\"}}", tipoTramo);

        Assertions.assertEquals(TipoVehiculo.AUTO,tramo.getMedioTransporte().getTipo());
        Assertions.assertEquals(1.0,tramo.getDistancia().valor);
    }

}
