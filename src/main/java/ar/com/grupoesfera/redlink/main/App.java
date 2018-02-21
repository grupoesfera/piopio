package ar.com.grupoesfera.redlink.main;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
}
