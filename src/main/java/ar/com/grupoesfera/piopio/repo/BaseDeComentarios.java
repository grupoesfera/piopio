package ar.com.grupoesfera.piopio.repo;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Comentario;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class BaseDeComentarios {

    public List<Comentario> obtenerTodos() {
        
        Session session = App.instancia().obtenerSesion();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Comentario> query = criteriaBuilder.createQuery(Comentario.class);
        Root<Comentario> comentarios = query.from(Comentario.class);
        query.select(comentarios);
        
        return session.createQuery(query).getResultList();
    }

    public long contarComentariosRealizadosPor(Usuario usuario) {
        
        return App.instancia().obtenerSesion()
                    .createNamedQuery("contarComentariosRealizadosPor"
                            , Long.class)
                    .setParameter("autor", usuario)
                    .getSingleResult();
    }
}
