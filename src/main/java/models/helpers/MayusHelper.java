package models.helpers;

public class MayusHelper {

    /**
     * Devuelve el String mandado por parametro, con la primera letra mayuscula, sin modificar
     * el resto.<br>
     * Ejemplo:<br>
     * <code>MayusHelper.capitalize("hola que tal") -> "Hola que tal"</code> <br>
     * <code>MayusHelper.capitalize("Hola Que Tal") -> "Hola Que Tal"</code> <br>
     * <code>MayusHelper.capitalize("hola Que Tal") -> "Hola Que Tal"</code> <br>
     * @return String
     */
    public static String capitalize(String text){
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}
