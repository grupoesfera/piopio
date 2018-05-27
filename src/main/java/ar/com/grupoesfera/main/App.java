package ar.com.grupoesfera.main;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Application;

import ar.com.grupoesfera.piopio.repo.BaseDePios;
import ar.com.grupoesfera.piopio.repo.BaseDeUsuarios;
import ar.com.grupoesfera.piopio.rest.API;

public class App extends Application {

    private static final App instancia = new App();
    private static EntityManagerFactory proveedorPersistencia = Persistence.createEntityManagerFactory("piopio");

    private BaseDeUsuarios usuarios = new BaseDeUsuarios();
    private BaseDePios pios = new BaseDePios();

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

    public BaseDeUsuarios obtenerRepoUsuarios() {

        return usuarios;
    }

    public BaseDePios obtenerRepoPios() {

        return pios;
    }
}
