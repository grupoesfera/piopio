package ar.com.grupoesfera.redlink.piopio.repo;

import java.util.List;

import ar.com.grupoesfera.redlink.main.App;
import ar.com.grupoesfera.redlink.piopio.modelo.Usuario;

public class Usuarios {

    @SuppressWarnings("unchecked")
    public List<Usuario> obtenerTodos() {
        
        return App.instancia().obtenerEntityManager().createQuery("select u from Usuario u").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> obtenerSeguidoresDe(Usuario usuario) {

        return App.instancia().obtenerEntityManager()
            .createQuery("select u from Usuario u where :usuario member of u.seguidos")
            .setParameter("usuario", usuario)
            .getResultList();
    }
}
