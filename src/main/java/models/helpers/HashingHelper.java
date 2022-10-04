package models.helpers;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashingHelper {

    public static String hashear(String contrasenia){
        return Hashing.sha256()
                .hashString(contrasenia, StandardCharsets.UTF_8)
                .toString();

    }

}
