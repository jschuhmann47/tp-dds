package domain.geoDDS.adapters;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TokenInterceptor implements Interceptor {

    public Response intercept(Chain chain) throws IOException {
        String token = "3b0C7dVdMqSb9KxPBvI8xQOPnDvGvC6h5c0sLEDbqOs="; //traer de config que no este en github
        Request newRequest=chain.request().newBuilder()
                .header("Authorization","Bearer " + token)
                .build();

        return chain.proceed(newRequest);
    }
}
