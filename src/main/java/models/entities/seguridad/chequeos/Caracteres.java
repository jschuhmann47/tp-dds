package models.entities.seguridad.chequeos;

public abstract class Caracteres extends Chequeo{
    public boolean chequear(String contrasenia){
        char ch;
        boolean cumpleCondicion = false;
        for (int i = 0; i < contrasenia.length(); i++) {
            ch = contrasenia.charAt(i);
            if (this.caracterEs(ch)) {
                cumpleCondicion = true;
            }
        }
        return cumpleCondicion;
    }

    public abstract boolean caracterEs(char ch);
}
