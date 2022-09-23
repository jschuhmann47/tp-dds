package models.entities.notificacion;

import models.entities.organizaciones.contacto.Contacto;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class Ejecutor implements Job {
    private final List<Contacto> listaDeContactos;
    private final String contenido;

    public Ejecutor(List<Contacto> listaDeContactos, String contenido) {
        this.listaDeContactos = listaDeContactos;
        this.contenido = contenido;
    }


    public void execute(JobExecutionContext jec) throws JobExecutionException{

        listaDeContactos.forEach(c-> {
            c.notificar(contenido);
        });
            //contacto.notificar("https://github.com/dds-utn/2022-ma-ma-mama-grupo-03");

    }


}







