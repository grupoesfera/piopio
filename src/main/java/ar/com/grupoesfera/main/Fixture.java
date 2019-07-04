package ar.com.grupoesfera.main;

import java.time.LocalDate;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.grupoesfera.piopio.modelo.Comentario;
import ar.com.grupoesfera.piopio.modelo.Favorito;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class Fixture {

    private static final Log log = LogFactory.getLog(Fixture.class);
    
    public static void initData() {

        Session session = App.instancia().obtenerSesion();
        Transaction transaccion = session.getTransaction();

        try {

            transaccion.begin();

            crearDatos(session);

            transaccion.commit();

        } catch (Exception e) {

            log.error("Falló la transacción", e);
            transaccion.rollback();

        } finally {

            session.close();
        }
    }

    public static void dropData() {

        Session session = App.instancia().obtenerSesion();
        Transaction transaccion = session.getTransaction();

        try {

            transaccion.begin();

            eliminarDatos(session);

            transaccion.commit();

        } catch (Exception e) {

            log.error("Falló la transacción", e);
            transaccion.rollback();

        } finally {

            session.close();
        }
    }

    private static void crearDatos(Session session) {

        Usuario marcelo = Usuario.nuevo().conId(1L).conNombre("Marcelo");
        Usuario brenda = Usuario.nuevo().conId(2L).conNombre("Brenda");
        Usuario india = Usuario.nuevo().conId(3L).conNombre("India");
        Usuario leon = Usuario.nuevo().conId(4L).conNombre("Leon");
        Usuario sebastian = Usuario.nuevo().conId(5L).conNombre("Sebastian");
        Usuario alejandro = Usuario.nuevo().conId(6L).conNombre("Alejandro");
        Usuario santiago = Usuario.nuevo().conId(7L).conNombre("Santiago");

        persistirSiEsPosible(session, marcelo, brenda, india, leon, sebastian, alejandro, santiago);

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
        
        persistirSiEsPosible(session, primerPioMarcelo, segundoPioMarcelo, pioBrenda, pioIndia, pioLeon);
        
        Favorito favoritoBrenMarcelo1 = Favorito.nuevo().conId(1L).conPio(primerPioMarcelo).conFan(brenda);
        Favorito favoritoBrenMarcelo2 = Favorito.nuevo().conId(2L).conPio(segundoPioMarcelo).conFan(brenda);
        Favorito favoritoIndiaMarcelo2 = Favorito.nuevo().conId(3L).conPio(segundoPioMarcelo).conFan(india);
        Favorito favoritoSebastianMarcelo2 = Favorito.nuevo().conId(4L).conPio(segundoPioMarcelo).conFan(sebastian);
        Favorito favoritoMarceloBrenda1 = Favorito.nuevo().conId(5L).conPio(pioBrenda).conFan(marcelo);
        Favorito favoritoIndiaBrenda1 = Favorito.nuevo().conId(6L).conPio(pioBrenda).conFan(india);
        Favorito favoritoBrendaIndia1 = Favorito.nuevo().conId(7L).conPio(pioIndia).conFan(brenda);
        Favorito favoritoLeonMarcelo1 = Favorito.nuevo().conId(8L).conPio(primerPioMarcelo).conFan(leon);
        Favorito favoritoLeonMarcelo2 = Favorito.nuevo().conId(9L).conPio(segundoPioMarcelo).conFan(leon);
        Favorito favoritoLeonBrenda1 = Favorito.nuevo().conId(10L).conPio(pioBrenda).conFan(leon);
        Favorito favoritoLeonIndia1 = Favorito.nuevo().conId(11L).conPio(pioIndia).conFan(leon);
        
        persistirSiEsPosible(session, favoritoBrenMarcelo1, favoritoBrenMarcelo2, favoritoIndiaMarcelo2, favoritoSebastianMarcelo2, 
            favoritoMarceloBrenda1, favoritoIndiaBrenda1, favoritoBrendaIndia1, favoritoLeonMarcelo1, favoritoLeonMarcelo2,
            favoritoLeonBrenda1, favoritoLeonIndia1);
        
        Comentario comentarioBrendaMarcelo = Comentario.nuevo().conId(1L).conMensaje("Bien por vos").conAutor(brenda);
        Comentario comentarioBrendaIndia = Comentario.nuevo().conId(2L).conMensaje("Muy bien").conAutor(brenda);
        
        persistirSiEsPosible(session, comentarioBrendaMarcelo, comentarioBrendaIndia);
        
        primerPioMarcelo.conComentario(comentarioBrendaMarcelo);
        pioIndia.conComentario(comentarioBrendaIndia);
    }
    
    private static void persistirSiEsPosible(Session session, Object... entidades) {
        
        for (Object entidad : entidades) {
            
            if (esEntidad(session, entidad.getClass())) {
                
                session.persist(entidad);

            } else {
                
                log.warn(entidad.getClass().getSimpleName() + " no es una Entity (no está mapeada). Nada que hacer aquí.");
            }
        }
    }
    
    private static Date fecha(Integer anio, Integer mes, Integer dia ) {
        
        return java.sql.Date.valueOf(LocalDate.of(anio, mes, dia));
    }

    private static void eliminarDatos(Session session) {
        
        eliminarSiEsPosible(session, Favorito.class);
        eliminarSiEsPosible(session, Comentario.class);
        eliminarSiEsPosible(session, Pio.class);
        eliminarSiEsPosible(session, Usuario.class);
    }
    
    private static void eliminarSiEsPosible(Session session, Class<?> entity) {
        
        if (esEntidad(session, entity)) {
            
        	session.createQuery("delete from " + entity.getSimpleName()).executeUpdate();

        } else {
            
            log.warn(entity.getSimpleName() + " no es una Entity (no está mapeada). Nada que hacer aquí.");
        }
    }
    
    private static Boolean esEntidad(Session session, Class<?> entity) {
    	Boolean esEntidad = true;
    	try {
    		session.getMetamodel().entity(entity);
    		
    	}catch (IllegalArgumentException e) {
			esEntidad = false;
		}
        return esEntidad;
    }
    
}
