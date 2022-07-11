package domain.organizaciones.contacto;

public interface EnvioNotificacionMailAdapter {

    void notificar(String contenido, String direccionCorreo);
}
