package domain.seguridad;

public abstract class Chequeos {
    public static boolean chequear(String contrasenia){
        char ch;
        boolean cumpleCondicion = false;
        for (int i = 0; i < contrasenia.length(); i++) {
            ch = contrasenia.charAt(i);
            if (caracterEs(ch)) {
                cumpleCondicion = true;
            }
        }
        return cumpleCondicion;
    }

    protected static boolean caracterEs(char ch) {
        return false;
    }
}

