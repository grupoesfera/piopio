package ar.com.grupoesfera.redlink.main;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.grupoesfera.redlink.piopio.modelo.Pio;
import ar.com.grupoesfera.redlink.piopio.modelo.Usuario;

public class Fixture {

    private static final Log log = LogFactory.getLog(Fixture.class);
    
    public static void initData() {

        EntityManager entities = App.instancia().obtenerEntityManager();
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

    private static void crearDatos(EntityManager entities) {

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

        Pio primerPioMarcelo = Pio.nuevo().conId(1L).conAutor(marcelo).conMensaje("Hola, este es mi primer pio").conFechaCreacion(fecha(2017, 12, 27));
        Pio segundoPioMarcelo = Pio.nuevo().conId(2L).conAutor(marcelo).conMensaje("Hola, este es mi segundo pio").conFechaCreacion(fecha(2017, 12, 28));
        Pio pioBrenda = Pio.nuevo().conId(3L).conAutor(brenda).conMensaje("Aguante India").conFechaCreacion(fecha(2018, 1, 1));
        Pio pioIndia = Pio.nuevo().conId(4L).conAutor(india).conMensaje("Guau!").conFechaCreacion(fecha(2018, 1, 2));
        Pio pioLeon = Pio.nuevo().conId(5L).conAutor(leon).conMensaje("Miau").conFechaCreacion(fecha(2018, 1, 2));
        
        entities.persist(primerPioMarcelo);
        entities.persist(segundoPioMarcelo);
        entities.persist(pioBrenda);
        entities.persist(pioIndia);
        entities.persist(pioLeon);
    }
    
    private static Date fecha(Integer anio, Integer mes, Integer dia ) {
        
        return java.sql.Date.valueOf(LocalDate.of(anio, mes, dia));
    }
}
