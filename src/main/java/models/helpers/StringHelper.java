package models.helpers;

import models.entities.organizaciones.entidades.Trabajador;

import java.util.List;

public class StringHelper {

    /**
     * Devuelve el String mandado por parametro, con la primera letra mayuscula, sin modificar
     * el resto.<br>
     * Ejemplo:<br>
     * <code>StringHelper.capitalize("hola que tal") -> "Hola que tal"</code> <br>
     * <code>StringHelper.capitalize("Hola Que Tal") -> "Hola Que Tal"</code> <br>
     * <code>StringHelper.capitalize("hola Que Tal") -> "Hola Que Tal"</code> <br>
     * @return String
     */
    public static String capitalize(String text){
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }


    /**
     * Devuelve el String mandado por parametro, pasandolo a mayuscula y cambiando los espacios por guiones bajos
     * <br>
     * Ejemplo:<br>
     * <code>StringHelper.toEnumFormat("hola que tal") -> "HOLA_QUE_TAL"</code> <br>
     * @return String
     */
    public static String toEnumFormat(String text){
        return text.toUpperCase().replace(" ","_");
    }

    public static String formatearListaEmpleados(List<Trabajador> lista){
        StringBuilder sb = new StringBuilder();
        lista.forEach(t -> {
           sb.append(t.getApellido()).append(", ").append(t.getNombre()).append(" - ");
        });
        return sb.substring(0,sb.toString().length()-3);
    }
}
