package models.entities.geoDDS.adapters;

import models.entities.geoDDS.entidades.Distancia;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceGeoDDS {

    @GET("distancia")
    Call<Distancia>
    distancia(@Query("localidadOrigenId") int localidadOrigenId,@Query("calleOrigen") String calleOrigen,
                  @Query("alturaOrigen") int alturaOrigen,@Query("localidadDestinoId") int localidadDestinoId,
                  @Query("calleDestino") String calleDestino,@Query("alturaDestino") int alturaDestino);
}
