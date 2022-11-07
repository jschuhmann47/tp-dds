package models.helpers.threads;

import models.entities.CargaDeActividades.CargaDeActividades;
import models.entities.CargaDeActividades.adapters.CargaDeActividadesAdapter;
import models.entities.CargaDeActividades.adapters.CargaDeActividadesApachePOIAdapter;
import models.entities.organizaciones.entidades.Organizacion;
import models.helpers.PersistenciaHelper;
import models.repositories.RepositorioDeOrganizaciones;
import models.repositories.factories.FactoryRepositorioDeOrganizaciones;

import java.io.IOException;

public class FileHandlerThread extends Thread{
    private final String filePath;
    private final Integer organizacionId;
    private final CargaDeActividadesAdapter adapter;
    RepositorioDeOrganizaciones repoOrg = FactoryRepositorioDeOrganizaciones.get();

    public FileHandlerThread(String filePath, Integer organizacionId, CargaDeActividadesAdapter adapter) {
        this.filePath = filePath;
        this.organizacionId = organizacionId;
        this.adapter = adapter;
    }

    @Override
    public void start() {
        Organizacion org = this.repoOrg.buscar(organizacionId);
        try {
            CargaDeActividades.setAdapter(adapter);
            CargaDeActividades.cargarActividadesDeArchivo(org.getListaDeActividades(),this.filePath);
            PersistenciaHelper.persistir(org);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //throw new RuntimeException("Error al cargar actividades de la organizacion: " + org.getRazonSocial());
        }
    }
}
