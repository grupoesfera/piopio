package ar.com.grupoesfera.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Transaction {
    
    private static final Log log = LogFactory.getLog(Transaction.class);

    public static <T> T run(Action<T> action) {

        EntityManager entities = App.instancia().obtenerEntityManager();
        EntityTransaction transaccion = entities.getTransaction();

        T returnedObject = null;
        
        try {

            transaccion.begin();

            returnedObject = action.execute(entities);

            transaccion.commit();

        } catch (Exception e) {

            log.error("Falló la transacción", e);
            transaccion.rollback();

        } finally {

            entities.close();
        }
        
        return returnedObject;
    }
}
