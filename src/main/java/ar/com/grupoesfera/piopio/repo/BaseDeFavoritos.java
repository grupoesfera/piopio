package ar.com.grupoesfera.piopio.repo;

import java.util.List;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Favorito;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class BaseDeFavoritos {

    @SuppressWarnings("unchecked")
    public List<Favorito> obtenerTodos() {
        
        return App.instancia().obtenerSesion()
                              .createQuery("from Favorito f")
                              .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Pio> obtenerPiosFavoritosDe(Usuario fan) {

        return App.instancia().obtenerSesion()
                .createQuery("select f.pio from Favorito f where f.fan = :fan")
                .setParameter("fan", fan)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> obtenerFansDel(Pio pio) {
        return App.instancia().obtenerSesion()
                .createQuery("select f.fan from Favorito f where f.pio = :pio")
                .setParameter("pio", pio)
                .getResultList();
    }
}
