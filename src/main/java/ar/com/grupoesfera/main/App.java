package ar.com.grupoesfera.main;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ar.com.grupoesfera.piopio.repo.BaseDePios;
import ar.com.grupoesfera.piopio.repo.BaseDeUsuarios;
import ar.com.grupoesfera.piopio.rest.API;

public class App extends Application {

    private static final App instancia = new App();
    private static SessionFactory proveedorPersistencia = new Configuration()
    														.addResource("Pio.hbm.xml")
    														.addResource("Usuario.hbm.xml")
    														.buildSessionFactory();
    
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

    public Session obtenerSesion() {

        return proveedorPersistencia.openSession();
    }

    public BaseDeUsuarios obtenerRepoUsuarios() {

        return usuarios;
    }

    public BaseDePios obtenerRepoPios() {

        return pios;
    }
}
