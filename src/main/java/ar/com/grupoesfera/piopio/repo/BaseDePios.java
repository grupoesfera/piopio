package ar.com.grupoesfera.piopio.repo;

import java.util.Date;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class BaseDePios {

    public synchronized Pio guardarCon(Usuario autor, String mensaje) {
        
        Pio nuevoPio = null;
        
        if (autor != null && mensaje != null) {
            
            nuevoPio = Pio.nuevo().conId(proximoId()).conAutor(autor).conMensaje(mensaje).conFechaCreacion(new Date());
            App.instancia().obtenerEntityManager().persist(nuevoPio);
        }
        
        return nuevoPio;
    }
    
    private Long proximoId() {
        
        Long maxId = null;
        
        maxId = (Long) App.instancia().obtenerEntityManager().createQuery("select max(p.id) from Pio p").getSingleResult();
        
        if (maxId != null) {
            
            maxId += 1L;
            
        } else {
            
            maxId = 1L;
        }
        
        return maxId;
    }
}
