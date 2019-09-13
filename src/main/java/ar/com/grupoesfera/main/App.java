package ar.com.grupoesfera.main;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.grupoesfera.piopio.repo.BaseDePios;
import ar.com.grupoesfera.piopio.repo.BaseDeUsuarios;

@Repository("app")
@Transactional
public class App {

    private static final Log log = LogFactory.getLog(App.class);
    private static final App instancia = new App();
    
    @Inject
    private SessionFactory proveedorPersistencia;
    
    private BaseDeUsuarios usuarios = new BaseDeUsuarios();
    private BaseDePios pios = new BaseDePios();

    private App() {
        
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

    public Session obtenerSesion() {

        return proveedorPersistencia.getCurrentSession();
    }

    public BaseDeUsuarios obtenerRepoUsuarios() {

        return usuarios;
    }

    public BaseDePios obtenerRepoPios() {

        return pios;
    }
}
