package models.helpers;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.entities.geoDDS.entidades.Localidad;
import models.entities.geoDDS.entidades.Municipio;
import models.entities.geoDDS.entidades.Pais;
import models.entities.geoDDS.entidades.Provincia;

import java.io.File;
import java.util.List;

public class GsonHelper {

    public static List<Provincia> generarProvincias(String json){
//        String json = Files.asCharSource(new File(System.getProperty("user.dir") + "/src/test/java/db/jsons/provincias.json"), Charsets.UTF_8).read();
        return new Gson().fromJson(json, new TypeToken<List<Provincia>>() {}.getType());

    }
    public static List<Localidad> generarLocalidades(String json){
        return new Gson().fromJson(json, new TypeToken<List<Localidad>>() {}.getType());

    }
    public static List<Municipio> generarMunicipios(String json){
        return new Gson().fromJson(json, new TypeToken<List<Municipio>>() {}.getType());

    }
    public static List<Pais> generarPaises(String json){
        return new Gson().fromJson(json, new TypeToken<List<Pais>>() {}.getType());

    }

}
