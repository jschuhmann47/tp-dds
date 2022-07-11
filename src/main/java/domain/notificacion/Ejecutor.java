package domain.notificacion;

import domain.organizaciones.contacto.Contacto;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.util.List;

public class Ejecutor implements Job {
    private List<Contacto> listaDeContactos;
    private String contenido;

    public Ejecutor(List<Contacto> listaDeContactos, String contenido) {
        this.listaDeContactos = listaDeContactos;
        this.contenido = contenido;
    }


    public void execute(JobExecutionContext jec) throws JobExecutionException{

        listaDeContactos.forEach(c-> {
            try {
                c.notificar(contenido);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
            //contacto.notificar("https://github.com/dds-utn/2022-ma-ma-mama-grupo-03");

    }


}







