package domain.notificacion;

import domain.organizaciones.contacto.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;

public class Ejecutor implements Job {


    EnvioNotificacionUltraWppAdapter  wsp = new EnvioNotificacionUltraWppAdapter();
    EnvioNotificacionJavaxMailAdapter email = new EnvioNotificacionJavaxMailAdapter();

    public Ejecutor() {
    }
    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        Contacto notificador = new Contacto("1124551580","paulvasqueztecco@");
        try {
            notificador.notificar("https://github.com/dds-utn/2022-ma-ma-mama-grupo-03");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
