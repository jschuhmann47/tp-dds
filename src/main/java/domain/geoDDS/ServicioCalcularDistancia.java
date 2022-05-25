package domain.geoDDS;

import domain.geoDDS.entidades.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

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
    public Distancia distanciaEntre(Ubicacion unaUbicacion, Ubicacion otraUbicacion) throws IOException {
        ServiceGeoDDS geoDdsService = this.retrofit.create(ServiceGeoDDS.class);
        Call<Distancia> requestDistancia = geoDdsService.distancia(/*TODO*/);
        Response<Distancia> responseDistancia = requestDistancia.execute();
        return responseDistancia.body();
    }

    public List<Provincia> listadoDeProvincias() throws IOException {
        ServiceGeoDDS geoDdsService = this.retrofit.create(ServiceGeoDDS.class);
        Call<List<Provincia>> requestListadoProvincias = geoDdsService.provincias(1,9);
        Response<List<Provincia>> responseListadoProvincias = requestListadoProvincias.execute();
        return responseListadoProvincias.body();
    }

    public List<Localidad> obtenerLocalidadesDeMunicipio(int municipioId) throws IOException {
        ServiceGeoDDS geoDdsService = this.retrofit.create(ServiceGeoDDS.class);
        Call<List<Localidad>> requestLocalidades= geoDdsService.localidades(1,municipioId);
        Response<List<Localidad>> responseLocalidades = requestLocalidades.execute();
        return responseLocalidades.body();
    }

    public List<Localidad> listadoDeLocalidades() throws IOException {
        ServiceGeoDDS geoDdsService = this.retrofit.create(ServiceGeoDDS.class);
        Call<List<Localidad>> requestListadoLocalidades = geoDdsService.localidades(1);
        Response<List<Localidad>> responseListadoLocalidades = requestListadoLocalidades.execute();
        return responseListadoLocalidades.body();
    }

    public List<Municipio> listadoDeMunicipios() throws IOException {
        ServiceGeoDDS geoDdsService = this.retrofit.create(ServiceGeoDDS.class);
        Call<List<Municipio>> requestListadoMunicipios = geoDdsService.municipios(2);
        Response<List<Municipio>> responseListadoMunicipios = requestListadoMunicipios.execute();
        return responseListadoMunicipios.body();
    }

    public List<Municipio> obtenerMunicipiosDeProvincia(int idProvincia) throws IOException {
        ServiceGeoDDS geoDdsService = this.retrofit.create(ServiceGeoDDS.class);
        Call<List<Municipio>> requestListadoMunicipios = geoDdsService.municipios(2);
        Response<List<Municipio>> responseListadoMunicipios = requestListadoMunicipios.execute();
        return responseListadoMunicipios.body();
    }


}
