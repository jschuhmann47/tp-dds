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

    public static void eliminar(Object ... entidadesAEliminar){
        EntityManagerHelper.beginTransaction();
        for(Object o : entidadesAEliminar){
            EntityManagerHelper.getEntityManager().remove(o);
        }
        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }

    public static void mergear(Object ... entidadesAMergear){
        EntityManagerHelper.beginTransaction();
        for(Object o : entidadesAMergear){
            EntityManagerHelper.getEntityManager().merge(o);
        }
        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }
}
