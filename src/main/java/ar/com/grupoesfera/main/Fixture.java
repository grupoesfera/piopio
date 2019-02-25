package ar.com.grupoesfera.main;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.EntityManager;

import ar.com.grupoesfera.piopio.modelo.Comentario;
import ar.com.grupoesfera.piopio.modelo.Favorito;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class Fixture {
    
    public static void initData() {

        Transaction.run(crearDatos());
    }

    public static void dropData() {

        Transaction.run(eliminarDatos());
    }

    private static Action<Void> crearDatos() {

        return (entities) -> {

                Usuario marcelo = Usuario.nuevo().conId(1L).conNombre("Marcelo");
                Usuario brenda = Usuario.nuevo().conId(2L).conNombre("Brenda");
                Usuario india = Usuario.nuevo().conId(3L).conNombre("India");
                Usuario leon = Usuario.nuevo().conId(4L).conNombre("Leon");
                Usuario sebastian = Usuario.nuevo().conId(5L).conNombre("Sebastian");
                Usuario alejandro = Usuario.nuevo().conId(6L).conNombre("Alejandro");
                Usuario santiago = Usuario.nuevo().conId(7L).conNombre("Santiago");
                
                persistir(entities, marcelo, brenda, india, leon, sebastian, alejandro, santiago);
                
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
                
                persistir(entities, primerPioMarcelo, segundoPioMarcelo, pioBrenda, pioIndia, pioLeon);
                
                Favorito favoritoBrenMarcelo1 = Favorito.nuevo().conId(1L).conPio(primerPioMarcelo).conFan(brenda);
                Favorito favoritoBrenMarcelo2 = Favorito.nuevo().conId(2L).conPio(segundoPioMarcelo).conFan(brenda);
                Favorito favoritoIndiaMarcelo2 = Favorito.nuevo().conId(3L).conPio(segundoPioMarcelo).conFan(india);
                Favorito favoritoSebastianMarcelo2 = Favorito.nuevo().conId(4L).conPio(segundoPioMarcelo).conFan(sebastian);
                Favorito favoritoMarceloBrenda1 = Favorito.nuevo().conId(5L).conPio(pioBrenda).conFan(marcelo);
                Favorito favoritoIndiaBrenda1 = Favorito.nuevo().conId(6L).conPio(pioBrenda).conFan(india);
                Favorito favoritoBrendaIndia1 = Favorito.nuevo().conId(7L).conPio(pioIndia).conFan(brenda);
                
                persistir(entities, favoritoBrenMarcelo1, favoritoBrenMarcelo2, favoritoIndiaMarcelo2, favoritoSebastianMarcelo2, 
                    favoritoMarceloBrenda1, favoritoIndiaBrenda1, favoritoBrendaIndia1);
                
                Comentario comentarioBrendaMarcelo = Comentario.nuevo().conId(1L).conMensaje("Bien por vos").conAutor(brenda);
                Comentario comentarioBrendaIndia = Comentario.nuevo().conId(2L).conMensaje("Muy bien").conAutor(brenda);
                
                persistir(entities, comentarioBrendaMarcelo, comentarioBrendaIndia);
                
                primerPioMarcelo.conComentario(comentarioBrendaMarcelo);
                pioIndia.conComentario(comentarioBrendaIndia);
                
                return null;
        };
    }
    
    private static void persistir(EntityManager entities, Object... entidades) {
        
        for (Object entidad : entidades) {
            
            entities.persist(entidad);
        }
    }
    
    private static Date fecha(Integer anio, Integer mes, Integer dia ) {
        
        return java.sql.Date.valueOf(LocalDate.of(anio, mes, dia));
    }

    private static Action<Void> eliminarDatos() {
        
        return (entities) -> {

            entities.createQuery("delete from Favorito").executeUpdate();
            entities.createQuery("delete from Pio").executeUpdate();
            entities.createQuery("delete from Comentario").executeUpdate();
            entities.createQuery("delete from Usuario").executeUpdate();

            return null;
        };
    }
}
