package domain.geoDDS;

import domain.geoDDS.entidades.Distancia;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioCalcularDistancia {
    private static ServicioCalcularDistancia instancia = null;
    private static final String urlApi = "https://ddstpa.com.ar/api/";
    private Retrofit retrofit;

    private ServicioCalcularDistancia() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioCalcularDistancia getInstance(){
        if (instancia==null){
            instancia=new ServicioCalcularDistancia();
        }
        return instancia;
    }

    /*
    * valor="36.56"
    * unidad="KM"
    *
    * */
    public Distancia distanciaEntre(Ubicacion unaUbicacion,Ubicacion otraUbicacion) throws IOException {
        ServiceGeoDDS geoDdsService = this.retrofit.create(ServiceGeoDDS.class);
        Call<Distancia> requestDistancia = geoDdsService.distanciaEntre(Ubicacion unaUbicacion,Ubicacion otraUbicacion);
        Response<Distancia> responseDistancia = requestDistancia.execute();
        return responseDistancia.body();
    }

    public ListadoDeMunicipios listadoDeMunicipiosDeProvincia(Provincia provincia) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestListadoDeMunicipios = georefService.municipios(provincia.id, "id, nombre", maximaCantidadRegistrosDefault);
        Response<ListadoDeMunicipios> responseListadoDeMunicipios = requestListadoDeMunicipios.execute();
        return responseListadoDeMunicipios.body();
    }


}
