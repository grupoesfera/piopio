package ar.com.grupoesfera.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.grupoesfera.piopio.modelo.Comentario;
import ar.com.grupoesfera.piopio.modelo.Pio;

public class EjemploLazy {
    
    public static void main(String[] args) {
        
        Session session = App.instancia().obtenerSesion();
        Transaction transaccion = session.getTransaction();
        
        Pio pio = Pio.nuevo().conId(100L).conMensaje("Un pio de prueba");
        Comentario comentarioUno = Comentario.nuevo().conId(100L).conMensaje("Mensaje del pio de prueba uno");
        Comentario comentarioDos = Comentario.nuevo().conId(101L).conMensaje("Mensaje del pio de prueba dos");
        Comentario comentarioTres = Comentario.nuevo().conId(102L).conMensaje("Mensaje del pio de prueba tres");
        
        transaccion.begin();
        session.save(pio);
        pio.conComentario(comentarioUno);
        pio.conComentario(comentarioDos);
        pio.conComentario(comentarioTres);
        
        session.save(comentarioUno);
        session.save(comentarioDos);
        session.save(comentarioTres);
        transaccion.commit();
        session.close();
        
        Pio pioInsertado = App.instancia().obtenerSesion().get(Pio.class, 100L);
        System.out.println(pioInsertado);
        System.out.println(pioInsertado.getComentarios());
        System.exit(0);
    }

}
