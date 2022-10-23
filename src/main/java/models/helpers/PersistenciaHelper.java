package models.helpers;

import db.EntityManagerHelper;

public class PersistenciaHelper {

    public static void persistir(Object ... entidadesAPersistir){
        EntityManagerHelper.beginTransaction();
        for(Object o : entidadesAPersistir){
            EntityManagerHelper.getEntityManager().persist(o); //el getEntityManager() crea uno si no existia
        }
        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }
}
