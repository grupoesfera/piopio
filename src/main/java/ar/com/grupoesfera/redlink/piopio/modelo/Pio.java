package ar.com.grupoesfera.redlink.piopio.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pio {

    @Id
    private Long id;
    
    @Column
    private String mensaje;
    
    public Long getId() {
    
        return id;
    }
    
    public void setId(Long id) {
    
        this.id = id;
    }
    
    public String getMensaje() {
    
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
    
        this.mensaje = mensaje;
    }
}
