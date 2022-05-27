package domain.geoDDS;

import domain.geoDDS.entidades.*;
import domain.locaciones.Direccion;
import domain.locaciones.Provincia;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Distancia distanciaEntre(Direccion direccionOrigen, Direccion direccionDestino) throws Exception {
        ServiceGeoDDS geoDdsService = this.retrofit.create(ServiceGeoDDS.class);
        int idProvincia;
        List<ProvinciaGeo> provincias = listadoDeProvincias();
        Optional<ProvinciaGeo> hayProvincia;
        hayProvincia=provincias.stream().filter(p-> Objects.equals(p.nombre, direccionOrigen.
                    getProvinciaString())).findFirst();
        if(hayProvincia.isPresent()){
            idProvincia = hayProvincia.get().id;

        }else{
            throw new Exception("No hay provincia");
        }
        int idMunicipio;
        List<Municipio> listaMunicipios=obtenerMunicipiosDeProvincia(idProvincia);
        Optional<Municipio> hayMunicipio=listaMunicipios.stream().
                filter(m->Objects.equals(m.nombre,direccionOrigen.getMunicipio())).findFirst();
        if(hayMunicipio.isPresent()){
            idMunicipio = hayMunicipio.get().id;
        }else{
            throw new Exception("No hay municipio");
        }
        int idLocalidadOrigen;
        List<Localidad> localidades = obtenerLocalidadesDeMunicipio(idMunicipio);
        Optional<Localidad> hayLocalidad=localidades.stream().
                filter(l->Objects.equals(l.nombre,direccionOrigen.getMunicipio())).findFirst();
        if(hayLocalidad.isPresent()){
            idLocalidadOrigen = hayLocalidad.get().id;
        }
        else{
            throw new Exception("No hay localidad");
        }

        int localidadDestino = 2; //TODO
        Call<Distancia> requestDistancia = geoDdsService.distancia(idLocalidadOrigen,direccionOrigen.getCalle(),
                direccionOrigen.getAltura(),localidadDestino,direccionDestino.getCalle(),direccionDestino.getAltura());
        Response<Distancia> responseDistancia = requestDistancia.execute();
        return responseDistancia.body();
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
        Call<List<Municipio>> requestListadoMunicipios = geoDdsService.municipios(2);
        Response<List<Municipio>> responseListadoMunicipios = requestListadoMunicipios.execute();
        return responseListadoMunicipios.body();
    }


}
