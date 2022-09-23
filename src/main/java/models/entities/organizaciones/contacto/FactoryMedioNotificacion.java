package models.entities.organizaciones.contacto;

public class FactoryMedioNotificacion {

    public static MedioNotificacion generarMedio(EMedioNotificacion medio){
        switch (medio){

            case MAIL:
                return new MandarMail(); //setear adapter?
            case WHATSAPP:
                return new MandarWhatsapp();
        }
        throw new RuntimeException("El medio de notificacion no existe");
    }
}
