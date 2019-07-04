package ar.com.grupoesfera.piopio.repo;

import java.util.List;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Comentario;

public class BaseDeComentarios {

    @SuppressWarnings("unchecked")
    public List<Comentario> obtenerTodos() {
        
        return App.instancia().obtenerSesion().createCriteria(Comentario.class).list();
    }

}
