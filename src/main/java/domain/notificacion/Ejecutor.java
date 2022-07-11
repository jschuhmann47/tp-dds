package domain.notificacion;

import domain.organizaciones.Contacto;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;

public class Ejecutor implements Job {

    public Ejecutor() {
    }

    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        Contacto notificador = new Contacto();
        try {
            notificador.notificar("https://github.com/dds-utn/2022-ma-ma-mama-grupo-03");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
