package models.entities.geoDDS.adapters;

import models.entities.geoDDS.entidades.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface ServiceGeoDDS {

    @GET("distancia")
    Call<Distancia>
    distancia(@Query("localidadOrigenId") int localidadOrigenId,@Query("calleOrigen") String calleOrigen,
                  @Query("alturaOrigen") int alturaOrigen,@Query("localidadDestinoId") int localidadDestinoId,
                  @Query("calleDestino") String calleDestino,@Query("alturaDestino") int alturaDestino);


    @GET("paises")
    Call<List<Pais>> paises(@Query("offset") int offset);

    @GET("provincias")
    Call<List<Provincia>> provincias(@Query("offset") int offset);

    @GET("municipios")
    Call<List<Municipio>> municipios(@Query("offset") int offset, @Query("provinciaId") int provinciaId);

    @GET("localidades")
    Call<List<Localidad>> localidades(@Query("offset") int offset, @Query("municipioId") int provinciaId);
}
