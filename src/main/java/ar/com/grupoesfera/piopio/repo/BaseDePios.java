package ar.com.grupoesfera.piopio.repo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class BaseDePios {
    
    @SuppressWarnings("unchecked")
    public List<Pio> obtenerTodos() {
        
        return App.instancia().obtenerSesion().createQuery("from Pio p").getResultList();
    }
    
    public Pio obtenerPor(Long id) {
        return App.instancia().obtenerSesion().find(Pio.class, id);
    }
    
    @SuppressWarnings("unchecked")
    public List<Pio> obtenerPor(Usuario autor) {
        return App.instancia().obtenerSesion().createQuery("from Pio p where p.autor = :autor")
                                              .setParameter("autor", autor)
                                              .getResultList();
    }
    
    public synchronized Pio guardarCon(Usuario autor, String mensaje) {
        
        Pio nuevoPio = null;
        
        if (autor != null && mensaje != null) {
            
            nuevoPio = Pio.nuevo().conId(proximoId()).conAutor(autor).conMensaje(mensaje).conFechaCreacion(new Date());
            App.instancia().obtenerSesion().persist(nuevoPio);
        }
        
        return nuevoPio;
    }
    
    public void actualizar(Pio pio) {

        Session session = App.instancia().obtenerSesion();

        Transaction transaccion = session.beginTransaction();
        
        session.createQuery("update Pio set mensaje = :mensaje where id = :id")
                .setParameter("mensaje", pio.getMensaje())
                .setParameter("id", pio.getId())
                .executeUpdate();
        
        transaccion.commit();
        session.close();
    }
    
    public void eliminarPor(Long id) {

        Session session = App.instancia().obtenerSesion();
        Transaction transaccion = session.beginTransaction();

        try {
            
            session.createQuery("delete Favorito where pio.id = :id").setParameter("id", id).executeUpdate();
            
            Pio pio = session.load(Pio.class, id);
            pio.borrarComentarios();
            session.remove(pio);

            transaccion.commit();

        } catch (Exception e) {

            transaccion.rollback();

        } finally {

            session.close();
        }

    }
    
    private Long proximoId() {
        
        Long maxId = null;
        
        maxId = (Long) App.instancia().obtenerSesion().createQuery("select max(p.id) from Pio p").getSingleResult();
        
        if (maxId != null) {
            
            maxId += 1L;
            
        } else {
            
            maxId = 1L;
        }
        
        return maxId;
    }


}
