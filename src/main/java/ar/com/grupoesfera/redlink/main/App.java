package ar.com.grupoesfera.redlink.main;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.core.Application;

import ar.com.grupoesfera.redlink.piopio.rest.API;

public class App extends Application {

    private static final App instancia = new App();
    private static EntityManagerFactory proveedorPersistencia = Persistence.createEntityManagerFactory("piopio");

    private App() {

    }

    public static App instancia() {

        return instancia;
    }

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> classes = new HashSet<>();
        classes.add(API.class);
        return classes;
    }

    public EntityManager obtenerEntityManager() {

        return proveedorPersistencia.createEntityManager();
    }

    public void initData() {

        EntityManager entities = obtenerEntityManager();
        EntityTransaction transaccion = entities.getTransaction();

        try {

            transaccion.begin();

            crearObjetos(entities);

            transaccion.commit();

        } catch (Exception e) {

            transaccion.rollback();

        } finally {

            entities.close();
        }
    }

    private void crearObjetos(EntityManager entities) {

    }
}
