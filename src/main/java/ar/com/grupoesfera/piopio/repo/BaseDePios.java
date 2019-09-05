package ar.com.grupoesfera.piopio.repo;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Comentario;
import ar.com.grupoesfera.piopio.modelo.Comentario_;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Pio_;
import ar.com.grupoesfera.piopio.modelo.Usuario;
import ar.com.grupoesfera.piopio.modelo.Usuario_;

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
    
    public List<Pio> obtenerConTexto(String texto) {
        
        Session session = App.instancia().obtenerSesion();
        
        CriteriaBuilder criteriaBuilder = App.instancia().obtenerSesion().getCriteriaBuilder();
        CriteriaQuery<Pio> query = criteriaBuilder.createQuery(Pio.class);
        Root<Pio> pios = query.from(Pio.class);
        
        query.select(pios)
            .where(criteriaBuilder.like(pios.get(Pio_.mensaje), "%" + texto + "%"));
        
        return session.createQuery(query).getResultList();
    }
    
    public List<Pio> obtenerComentadosPor(String nombre) {
        
        Session session = App.instancia().obtenerSesion();
        
        CriteriaBuilder criteriaBuilder = App.instancia().obtenerSesion().getCriteriaBuilder();
        CriteriaQuery<Pio> query = criteriaBuilder.createQuery(Pio.class);
        Root<Pio> pios = query.from(Pio.class);
        Join<Comentario, Usuario> autor = pios.join(Pio_.comentarios).join(Comentario_.autor);
        
        query.select(pios)
            .where(criteriaBuilder.equal(autor.get(Usuario_.nombre), nombre));
        
        return session.createQuery(query).getResultList();
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
