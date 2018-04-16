package ar.com.grupoesfera.redlink.piopio.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comentario {

    @Id private Long id;
    @Column private String mensaje;
    @ManyToOne private Usuario autor;
    
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
    
    public Usuario getAutor() {
    
        return autor;
    }
    
    public void setAutor(Usuario autor) {
    
        this.autor = autor;
    }
}
