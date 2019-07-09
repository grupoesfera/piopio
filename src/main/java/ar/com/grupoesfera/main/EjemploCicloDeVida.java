package ar.com.grupoesfera.main;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.grupoesfera.piopio.modelo.Pio;

public class EjemploCicloDeVida {
    
    public static void main(String[] args) {
        
        Session session = App.instancia().obtenerSesion();
        Pio pio = Pio.nuevo().conId(100L).conMensaje("Un pio de prueba");
        
        Transaction primerTransaccion = session.beginTransaction();
        session.save(pio);
        pio.setMensaje("Mismo pio de prueba");
        primerTransaccion.commit();
        session.close();
        
        Session otraSession = App.instancia().obtenerSesion();
        Transaction segundaTransaccion = otraSession.beginTransaction();
        otraSession.update(pio);
        pio.setMensaje("Cambio el mensaje");
        segundaTransaccion.commit();
        otraSession.close();
        
        Session tercerSession = App.instancia().obtenerSesion();
        Pio pioModificado = tercerSession.get(Pio.class, 100L);
        System.out.println(pioModificado.getMensaje());
        Transaction terceraTransaction = tercerSession.beginTransaction();
        pioModificado.setMensaje("Cambio el mensaje por tercera vez");
        terceraTransaction.commit();
        tercerSession.close();
        
        Session cuartaSesion = App.instancia().obtenerSesion();
        Pio pioModificadoUnaVezMas = cuartaSesion.get(Pio.class, 100L);
        System.out.println(pioModificadoUnaVezMas.getMensaje());
        
        System.exit(0);
    }
    
}
