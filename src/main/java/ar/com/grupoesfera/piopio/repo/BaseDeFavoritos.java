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
    
    public Favorito obtenerPor(Long id) {
        return App.instancia().obtenerSesion().get(Favorito.class, id);
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

    @SuppressWarnings("unchecked")
    public List<Pio> obtenerPiosSinFans() {
        return App.instancia().obtenerSesion()
                .createQuery("from Pio p where not exists ( from Favorito f where f.pio = p )")
                .getResultList();
    }

    public synchronized Favorito guardarCon(Usuario fan, Pio pio) {
        
        Favorito favorito = null;
        
        if (fan != null && pio != null) {
            
            favorito = Favorito.nuevo().conId(proximoId()).conFan(fan).conPio(pio);
            App.instancia().obtenerSesion().save(favorito);
        }
        
        return favorito;
    }

    private Long proximoId() {
        
        Long maxId = null;
        
        maxId = (Long) App.instancia().obtenerSesion().createQuery("select max(f.id) from Favorito f").getSingleResult();
        
        if (maxId != null) {
            
            maxId += 1L;
            
        } else {
            
            maxId = 1L;
        }
        
        return maxId;
    }
}
