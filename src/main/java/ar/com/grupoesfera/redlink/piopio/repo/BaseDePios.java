package ar.com.grupoesfera.redlink.piopio.repo;

import java.util.List;

import ar.com.grupoesfera.redlink.main.App;
import ar.com.grupoesfera.redlink.piopio.modelo.Pio;

public class BaseDePios {

    @SuppressWarnings("unchecked")
    public List<Pio> obtenerTodos() {
        
        return App.instancia().obtenerEntityManager().createQuery("select p from Pio p").getResultList();
    }
}
