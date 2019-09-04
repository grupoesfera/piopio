package ar.com.grupoesfera.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.grupoesfera.piopio.modelo.Comentario;
import ar.com.grupoesfera.piopio.modelo.Pio;

public class OneToManyBidireccional {
    
    public static void main(String[] args) {
        
        Session sesion = App.instancia().obtenerSesion();
        
        Pio pio = Pio.nuevo().conId(1L).conMensaje("Primer pio");
        Comentario unComentario = Comentario.nuevo().conId(1L).conMensaje("Comentario del pio");
        Comentario otroComentario = Comentario.nuevo().conId(2L).conMensaje("Otro comentario del pio");
        
        
        Transaction transaction = sesion.beginTransaction();
        
        pio.comentar(unComentario);
        pio.comentar(otroComentario);
        
        sesion.persist(pio);
        
        transaction.commit();
        
        transaction = sesion.beginTransaction();
        
        pio.eliminarComentario(otroComentario);
        
        transaction.commit();
    }

}
