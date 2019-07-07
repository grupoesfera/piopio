package ar.com.grupoesfera.main;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import ar.com.grupoesfera.piopio.repo.BaseDePios;
import ar.com.grupoesfera.piopio.repo.BaseDeUsuarios;
import ar.com.grupoesfera.piopio.rest.API;

public class App extends Application {

    private static final Log log = LogFactory.getLog(App.class);
    private static final App instancia = new App();
    private static SessionFactory proveedorPersistencia;
    
    private BaseDeUsuarios usuarios = new BaseDeUsuarios();
    private BaseDePios pios = new BaseDePios();

    private App() {
        configurarHibernate();
    }

    private void configurarHibernate() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() 
                .build();
        try {
            proveedorPersistencia = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
            log.error("No se pudo configurar hibernate. Mensaje: " + e.getMessage());
            log.debug("No se pudo configurar hibernate. Mensaje: " + e.getMessage() + " Causa: " + e);
        }
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
