package ar.com.grupoesfera.redlink.main;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.core.Application;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.grupoesfera.redlink.piopio.modelo.Pio;
import ar.com.grupoesfera.redlink.piopio.modelo.Usuario;
import ar.com.grupoesfera.redlink.piopio.repo.BaseDePios;
import ar.com.grupoesfera.redlink.piopio.repo.BaseDeUsuarios;
import ar.com.grupoesfera.redlink.piopio.rest.API;

public class App extends Application {

    private static final Log log = LogFactory.getLog(App.class);

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

    public void initData() {

        EntityManager entities = obtenerEntityManager();
        EntityTransaction transaccion = entities.getTransaction();

        try {

            transaccion.begin();

            crearDatos(entities);

            transaccion.commit();

        } catch (Exception e) {

            log.error("Falló la transacción", e);
            transaccion.rollback();

        } finally {

            entities.close();
        }
    }

    private void crearDatos(EntityManager entities) {

        Usuario marcelo = Usuario.nuevo().conId(1L).conNombre("Marcelo");
        Usuario brenda = Usuario.nuevo().conId(2L).conNombre("Brenda");
        Usuario india = Usuario.nuevo().conId(3L).conNombre("India");
        Usuario leon = Usuario.nuevo().conId(4L).conNombre("Leon");
        Usuario sebastian = Usuario.nuevo().conId(5L).conNombre("Sebastian");
        Usuario alejandro = Usuario.nuevo().conId(6L).conNombre("Alejandro");
        Usuario santiago = Usuario.nuevo().conId(7L).conNombre("Santiago");

        entities.persist(marcelo);
        entities.persist(brenda);
        entities.persist(india);
        entities.persist(leon);
        entities.persist(sebastian);
        entities.persist(alejandro);
        entities.persist(santiago);

        marcelo.sigueA(brenda, india, sebastian);
        brenda.sigueA(india, marcelo);
        india.sigueA(brenda, sebastian, marcelo);
        sebastian.sigueA(marcelo);
        alejandro.sigueA(santiago);

        Pio primerPioMarcelo = Pio.nuevo().conId(1L).conAutor(marcelo).conMensaje("Hola, este es mi primer pio");
        Pio segundoPioMarcelo = Pio.nuevo().conId(2L).conAutor(marcelo).conMensaje("Hola, este es mi segundo pio");
        Pio pioBrenda = Pio.nuevo().conId(3L).conAutor(brenda).conMensaje("Aguante India");
        Pio pioIndia = Pio.nuevo().conId(4L).conAutor(india).conMensaje("Guau!");
        Pio pioLeon = Pio.nuevo().conId(5L).conAutor(leon).conMensaje("Miau");
        
        entities.persist(primerPioMarcelo);
        entities.persist(segundoPioMarcelo);
        entities.persist(pioBrenda);
        entities.persist(pioIndia);
        entities.persist(pioLeon);
    }
    
    public BaseDeUsuarios obtenerRepoUsuarios() {

        return usuarios;
    }

    public BaseDePios obtenerRepoPios() {

        return pios;
    }
}
