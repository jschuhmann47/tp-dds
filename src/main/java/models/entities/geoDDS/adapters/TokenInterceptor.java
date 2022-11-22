package models.entities.geoDDS.adapters;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class TokenInterceptor implements Interceptor {

    public Response intercept(Chain chain) throws IOException {
        String path = "src/main/java/models/entities/geoDDS/adapters/";
        Properties configToken = new Properties();
        try{
            InputStream input = Files.newInputStream(new File(path + "token.properties").toPath());
            configToken.load(input);
        } catch(IOException e){
            e.printStackTrace();
        }
        String token = configToken.getProperty("TOKEN");
        Request newRequest=chain.request().newBuilder()
                .header("Authorization","Bearer " + token)
                .build();

        return chain.proceed(newRequest);
    }
}
