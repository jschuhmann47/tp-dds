package domain.organizaciones.contacto.adapters;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class EnvioNotificacionUltraWppAdapter implements EnvioNotificacionWhatsappAdapter {
    String apiUrl;
    String token;

    public EnvioNotificacionUltraWppAdapter(String properties, String apiUrl){
        this.apiUrl = apiUrl;
        Properties FEconfigs = new Properties();
        try{
            InputStream input = Files.newInputStream(new File(properties).toPath());
            FEconfigs.load(input);
        } catch(IOException e){
            e.printStackTrace();
        }
        token = FEconfigs.getProperty("TOKEN");
    }

    public void notificar(String contenido,String nroTelefono) throws IOException {
        OkHttpClient cliente = new OkHttpClient();

        String numero = "token=" + this.token + "&to=+54" + nroTelefono + "&body=" + contenido + "&priority=1&referenceId=";
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody cuerpo = RequestBody.create(numero,mediaType);
        Request request = new Request.Builder()
                .url(apiUrl)
                .post(cuerpo)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        String response = this.ejecutarRequest(cliente,request);
    }

    public String ejecutarRequest(OkHttpClient cliente, Request request) throws IOException {
        return cliente.newCall(request).execute().body().string();
    }
}
