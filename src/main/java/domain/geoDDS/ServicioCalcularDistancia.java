package domain.geoDDS;

import domain.geoDDS.entidades.*;
import domain.locaciones.Direccion;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public class ServicioCalcularDistancia {
    public static ServicioCalcularDistancia instancia = null;
    private static final String urlApi = "https://ddstpa.com.ar/api/";
    private Retrofit retrofit;

    TokenInterceptor interceptorDeToken = new TokenInterceptor();

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptorDeToken).build();

    public ServicioCalcularDistancia() {
        this.retrofit = new Retrofit.Builder()
                .client(client)
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
    public Distancia distanciaEntre(Direccion direccionOrigen, Direccion direccionDestino) throws Exception {
        ServiceGeoDDS geoDdsService = this.retrofit.create(ServiceGeoDDS.class);
        Call<Distancia> requestDistancia = geoDdsService.distancia(this.obtenerIdLocalidad(direccionOrigen),direccionOrigen.getCalle(),
                direccionOrigen.getAltura(),this.obtenerIdLocalidad(direccionDestino),direccionDestino.getCalle(),direccionDestino.getAltura());
        Response<Distancia> responseDistancia = requestDistancia.execute();
        return responseDistancia.body();
    }

    private int obtenerIdLocalidad(Direccion direccion) throws Exception {
        int idProvincia = Calculador.calcularProvinciaId(direccion);
        int idMunicipio = Calculador.calcularMunicipioId(direccion,idProvincia);
        return Calculador.calcularLocalidadId(direccion, idMunicipio);
    }


    public List<ProvinciaGeo> listadoDeProvincias() throws IOException {
        ServiceGeoDDS geoDdsService = this.retrofit.create(ServiceGeoDDS.class);
        Call<List<ProvinciaGeo>> requestListadoProvincias = geoDdsService.provincias(1,9);
        Response<List<ProvinciaGeo>> responseListadoProvincias = requestListadoProvincias.execute();
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
        Call<List<Municipio>> requestListadoMunicipios = geoDdsService.municipios(2,idProvincia);
        Response<List<Municipio>> responseListadoMunicipios = requestListadoMunicipios.execute();
        return responseListadoMunicipios.body();
    }


}
