package domain.geoDDS;

import domain.geoDDS.entidades.Distancia;
import domain.geoDDS.entidades.Localidad;
import domain.geoDDS.entidades.Municipio;
import domain.geoDDS.entidades.ProvinciaGeo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface ServiceGeoDDS {
    @GET("provincias")
    Call<List<ProvinciaGeo>> provincias(@Query("offset") int offset, @Query("paisId") int paisId);

    @GET("localidades")
    Call<List<Localidad>> localidades(@Query("offset") int offset,@Query("municipioId") int municipioId);
    @GET("localidades")
    Call<List<Localidad>> localidades(@Query("offset") int offset);

    @GET("municipios")
    Call<List<Municipio>> municipios(@Query("offset") int offset,@Query("provinciaId") int provinciaId);
    @GET("municipios")
    Call<List<Municipio>> municipios(@Query("offset") int offset);

    @GET("distancia")
    Call<Distancia>
    distancia(@Query("localidadOrigenId") int localidadOrigenId,@Query("calleOrigen") String calleOrigen,
                  @Query("alturaOrigen") int alturaOrigen,@Query("localidadDestinoId") int localidadDestinoId,
                  @Query("calleDestino") String calleDestino,@Query("alturaDestino") int alturaDestino);
}
