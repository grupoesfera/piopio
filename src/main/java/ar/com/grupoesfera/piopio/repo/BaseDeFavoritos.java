package ar.com.grupoesfera.piopio.repo;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Favorito;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class BaseDeFavoritos {

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
