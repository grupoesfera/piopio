package ar.com.grupoesfera.piopio.repo;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.criterion.Restrictions;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Usuario;
import ar.com.grupoesfera.piopio.modelo.dto.SeguidoresPorUsuario;
import ar.com.grupoesfera.piopio.modelo.transformer.SeguidoresPorUsuarioTransformer;

public class BaseDeUsuarios {

    @SuppressWarnings("unchecked")
    public List<Usuario> obtenerTodos() {
        return App.instancia().obtenerSesion().createQuery("from Usuario u").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> obtenerSeguidoresDe(Usuario usuario) {
        return App.instancia().obtenerSesion().createQuery("from Usuario u where :usuario member of u.seguidos")
                                              .setParameter("usuario", usuario)
                                              .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Usuario> obtenerAislados() {
        return App.instancia().obtenerSesion().createQuery("from Usuario u where u.seguidos is empty and not exists (select u2 from Usuario u2 where u member of u2.seguidos)")
                .getResultList();
    }

    public Usuario obtenerPor(Long id) {
        return App.instancia().obtenerSesion().get(Usuario.class, id);
    }
    
    @SuppressWarnings("unchecked")
    public List<String> obtenerNombres() {
        
        return App.instancia().obtenerSesion().createNativeQuery("call nombres()").getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Usuario> obtenerSeguidoresDePorNombre(Usuario seguido, String nombreDeSeguidor) {
        
        return App.instancia().obtenerSesion().createCriteria(Usuario.class)
                                              .add(Restrictions.eq("nombre", nombreDeSeguidor))
                                              .createCriteria("seguidos")
                                                  .add(Restrictions.eq("id", seguido.getId()))
                                              .list();
    }
    
    @SuppressWarnings("deprecation")
    public List<SeguidoresPorUsuario> contarCantidadDeSeguidoresDeLosUsuarios() {
        return App.instancia().obtenerSesion()
                .createNativeQuery("SELECT u.nombre as nombre, count(s.seguidor) as cantidadDeSeguidores "
                        + "FROM Usuario u "
                        + "LEFT JOIN Seguidos s on u.id = s.seguido "
                        + "GROUP BY u.nombre")
                .setResultTransformer(new SeguidoresPorUsuarioTransformer())
                .list();
        
    }
}
