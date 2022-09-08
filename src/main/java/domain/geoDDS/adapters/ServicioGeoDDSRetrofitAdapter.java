package domain.geoDDS.adapters;

import domain.geoDDS.Direccion;
import domain.geoDDS.ServicioCalcularDistancia;
import domain.geoDDS.entidades.Distancia;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioGeoDDSRetrofitAdapter implements ServicioGeoDDSAdapter {
    public static ServicioCalcularDistancia instancia = null;
    private static final String urlApi = "https://ddstpa.com.ar/api/";
    private final Retrofit retrofit;

    private final TokenInterceptor interceptorDeToken = new TokenInterceptor();

    private final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptorDeToken).build();

    public ServicioGeoDDSRetrofitAdapter() {
        this.retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(urlApi)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public Distancia distanciaEntre(Direccion direccionOrigen, Direccion direccionDestino) throws IOException {
        ServiceGeoDDS geoDdsService = this.retrofit.create(ServiceGeoDDS.class);
        Call<Distancia> requestDistancia = geoDdsService.distancia
                (ServicioCalcularDistancia.obtenerLocalidadId(direccionOrigen),direccionOrigen.getCalle(), direccionOrigen.getAltura(),
                ServicioCalcularDistancia.obtenerLocalidadId(direccionDestino),direccionDestino.getCalle(),direccionDestino.getAltura());
        Response<Distancia> responseDistancia = requestDistancia.execute();
        return responseDistancia.body();
    }
}
