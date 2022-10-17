package models.helpers.deserializers;

import com.google.gson.*;
import models.entities.transporte.MedioTransporte;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MedioTransporteDeserializer implements JsonDeserializer<MedioTransporte> {
    private String nombreAtributo;
    private Gson gson;
    private Map<String, Class<? extends MedioTransporte>> medioTransporteType;

    public MedioTransporteDeserializer(String nombreAtributo) {
        this.nombreAtributo = nombreAtributo;
        this.gson = new Gson();
        this.medioTransporteType = new HashMap<>();
    }

    public void agregarMedioTransporte(String medioNombre, Class<? extends MedioTransporte> medioClase) {
        this.medioTransporteType.put(medioNombre, medioClase);
    }

    public MedioTransporte deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        JsonObject medioObject = json.getAsJsonObject();
        JsonElement medioElement = medioObject.get(nombreAtributo);

        Class<? extends MedioTransporte> medio = medioTransporteType.get(medioElement.getAsString());
        return gson.fromJson(medioObject, medio);
    }
}
