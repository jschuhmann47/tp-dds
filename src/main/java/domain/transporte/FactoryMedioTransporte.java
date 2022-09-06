package domain.transporte;

import domain.transporte.privado.ServicioContratado;
import domain.transporte.privado.ServicioEcologico;
import domain.transporte.privado.TransportePrivado;
import domain.transporte.publico.TransportePublico;

public class FactoryMedioTransporte {
    public static MedioTransporte obtenerMedioTransporte(EMedioTransporte medio){
        switch (medio){

            case SERVICIO_CONTRATADO:
                return new ServicioContratado();
            case SERVICIO_ECOLOGICO:
                return new ServicioEcologico();
            case TRANSPORTE_PRIVADO:
                return new TransportePrivado();
            case TRANSPORTE_PUBLICO:
                return new TransportePublico();
        }
        return null;
    }
}
