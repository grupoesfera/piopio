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
    
    public Long contar() {
        return App.instancia()
                  .obtenerSesion()
                  .createQuery("select count (p) from Pio p", Long.class)
                  .uniqueResult();
    }
    
    public List<Pio> buscarEntre(Date fechaInicio, Date fechaFin) {
        return App.instancia().obtenerSesion()
            .createQuery("from Pio where fechaCreacion >= :fechaInicio and fechaCreacion <= :fechaFin", Pio.class)
            .setParameter("fechaInicio", fechaInicio)
            .setParameter("fechaFin", fechaFin)
            .list();
    }
    
    public List<Pio> obtenerPiosDe(Usuario usuario) {
        return App.instancia().obtenerSesion()
                .createQuery("select p from Pio p where p.autor = :autor", Pio.class)
                .setParameter("autor", usuario)
                .list();
    }
    
    public Long contarPios(Usuario usuario) {
        return App.instancia().obtenerSesion()
                .createQuery("select count(p) from Pio p where p.autor = :autor", Long.class)
                .setParameter("autor", usuario)
                .uniqueResult();
    }
    
    public List<Pio> obtenerPiosConMensaje(String mensaje) {
        
        return App.instancia().obtenerSesion()
                    .createQuery("from Pio p where mensaje like :mensaje", Pio.class)
                    .setParameter("mensaje", "%" + mensaje + "%")
                    .list();
    }
    
    public List<Pio> obtenerPiosFavoritosDe(Usuario fan) {

        return App.instancia().obtenerSesion()
                .createQuery("select f.pio from Favorito f where f.fan = :fan", Pio.class)
                .setParameter("fan", fan)
                .list();
    }
    
    public Long contarFavoritosDeUnPio(Pio pio) {
        
        Long cantidad = App.instancia().obtenerSesion()
                .createQuery("select count( f.pio) from Favorito f where f.pio = :pio group by f.pio", Long.class)
                .setParameter("pio", pio)
                .uniqueResult();
        
        return cantidad == null ? 0 : cantidad;
    }
}
