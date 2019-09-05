package ar.com.grupoesfera.piopio.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comentario {

    @Id private Long id;
    @Column private String mensaje;
    @ManyToOne @JoinColumn(name="autorId") private Usuario autor;
    
    @ManyToOne
    private Pio pio;
    
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
    
    public static Comentario nuevo() {
        
        return new Comentario();
    }
    
    public Comentario conId(Long id) {
        
        setId(id);
        return this;
    }
    
    public Comentario conMensaje(String mensaje) {
        
        setMensaje(mensaje);
        return this;
    }
    
    public Comentario conAutor(Usuario autor) {
        
        setAutor(autor);
        return this;
    }

    public void setPio(Pio pio) {
        this.pio = pio;
    }
}
