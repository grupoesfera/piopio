package ar.com.grupoesfera.redlink.piopio.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Pio {

    @Id
    private Long id;
    
    @Column
    private String mensaje;
    
    @Column
    private Date fechaCreacion;
    
    @Transient
    private Usuario autor;
    
    @Transient
    private List<Comentario> comentarios;
    
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
    
    public Date getFechaCreacion() {
    
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
    
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getAutor() {
    
        return autor;
    }

    public void setAutor(Usuario autor) {
    
        this.autor = autor;
    }

    public List<Comentario> getComentarios() {
    
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
    
        this.comentarios = comentarios;
    }
}
