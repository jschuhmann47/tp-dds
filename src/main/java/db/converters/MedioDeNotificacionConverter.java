package db.converters;

import models.entities.organizaciones.contacto.EMedioNotificacion;
import models.entities.organizaciones.contacto.MandarMail;
import models.entities.organizaciones.contacto.MandarWhatsapp;
import models.entities.organizaciones.contacto.MedioNotificacion;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class MedioDeNotificacionConverter implements AttributeConverter<MedioNotificacion, EMedioNotificacion> {

    @Override
    public EMedioNotificacion convertToDatabaseColumn(MedioNotificacion medioNotificacion) {
        if(medioNotificacion instanceof MandarMail){
            return EMedioNotificacion.MAIL;
        }
        if(medioNotificacion instanceof MandarWhatsapp){ //creo que no hace falta ete converter, todo probar
            return EMedioNotificacion.WHATSAPP;
        }
        return null;
    }

    @Override
    public MedioNotificacion convertToEntityAttribute(EMedioNotificacion eMedioNotificacion) {
        switch (eMedioNotificacion){

            case MAIL:
                return new MandarMail();
            case WHATSAPP:
                return new MandarWhatsapp();
        }
        return null;
    }
}
