package ar.com.grupoesfera.piopio.repo;

import java.util.List;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Favorito;

public class BaseDeFavoritos {

    @SuppressWarnings("unchecked")
    public List<Favorito> obtenerTodos() {
        
        return App.instancia().obtenerSesion()
                              .createQuery("from Favorito f")
                              .getResultList();
    }
}
