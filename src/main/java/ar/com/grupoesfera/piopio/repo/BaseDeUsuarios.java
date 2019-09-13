package ar.com.grupoesfera.piopio.repo;

import java.util.List;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class BaseDeUsuarios {

    @SuppressWarnings("unchecked")
    public List<Usuario> obtenerTodos() {
        return App.instancia().obtenerSesion().createQuery("from Usuario u").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> obtenerAislados() {
        return App.instancia().obtenerSesion().createQuery("from Usuario u where u.seguidos is empty and not exists (select u2 from Usuario u2 where u member of u2.seguidos)")
                .getResultList();
    }

    public Usuario obtenerPor(Long id) {
        return App.instancia().obtenerSesion().get(Usuario.class, id);
    }
    
    public Integer contarCuantosUsuariosSigue(Usuario usuario) {
        return App.instancia().obtenerSesion()
                .createQuery("select size(seguidos) from Usuario u where u = :usuario", Integer.class)
                .setParameter("usuario", usuario)
                .uniqueResult();
    }
    
    public List<Usuario> obtenerSeguidoresDe(Usuario usuario) {
        return App.instancia().obtenerSesion().createQuery("from Usuario u where :usuario member of u.seguidos", Usuario.class)
                                              .setParameter("usuario", usuario)
                                              .list();
    }
    
    public List<Usuario> obtenerFansDel(Pio pio) {
        return App.instancia().obtenerSesion()
                .createQuery("select f.fan from Favorito f where f.pio = :pio", Usuario.class)
                .setParameter("pio", pio)
                .list();
    }
}
