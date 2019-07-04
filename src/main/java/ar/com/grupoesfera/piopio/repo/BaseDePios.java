package ar.com.grupoesfera.piopio.repo;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

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
    
    @SuppressWarnings({ "deprecation", "unchecked" })
    public List<Pio> obtenerConTexto(String texto) {
        
        return App.instancia().obtenerSesion().createCriteria(Pio.class)
            .add(Restrictions.like("mensaje", "%" + texto + "%"))
            .list();
    }

    public synchronized Pio guardarCon(Usuario autor, String mensaje) {
        
        Pio nuevoPio = null;
        
        if (autor != null && mensaje != null) {
            
            nuevoPio = Pio.nuevo().conId(proximoId()).conAutor(autor).conMensaje(mensaje).conFechaCreacion(new Date());
            App.instancia().obtenerSesion().persist(nuevoPio);
        }
        
        return nuevoPio;
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
