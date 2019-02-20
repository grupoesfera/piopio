package ar.com.grupoesfera.piopio.repo;

import java.util.List;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class BaseDeUsuarios {

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

    @SuppressWarnings("unchecked")
    public List<Usuario> obtenerAislados() {

        return App.instancia().obtenerEntityManager()
            .createQuery("select u from Usuario u where u.seguidos is empty and not exists (select u2 from Usuario u2 where u member of u2.seguidos)")
            .getResultList();
    }
    
    public Usuario obtenerPor(Long id) {
        
        return App.instancia().obtenerEntityManager().find(Usuario.class, id);
    }
}
