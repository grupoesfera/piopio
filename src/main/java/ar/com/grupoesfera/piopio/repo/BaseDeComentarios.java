package ar.com.grupoesfera.piopio.repo;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Comentario;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class BaseDeComentarios {

    @SuppressWarnings("unchecked")
    public List<Comentario> obtenerTodos() {
        
        return App.instancia().obtenerSesion().createCriteria(Comentario.class).list();
    }

    @SuppressWarnings("unchecked")
    public List<Comentario> obtenerRealizadosPor(Usuario usuario) {
        return App.instancia().obtenerSesion().createCriteria(Comentario.class)
                .add(Restrictions.eq("autor", usuario))
                .list();
    }

}
