package ar.com.grupoesfera.piopio.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pio {

    @Id private Long id;
    @Column private String mensaje;
    @Column private Date fechaCreacion;
    @ManyToOne private Usuario autor;
    
    
    @OneToMany(mappedBy = "pio", cascade = CascadeType.ALL)
    private List<Comentario> comentarios;
    
    public Pio() {
        this.comentarios = new LinkedList<>();
    }

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

    public static Pio nuevo() {

        Pio nuevoPio = new Pio();
        nuevoPio.setFechaCreacion(new Date());
        return nuevoPio;
    }

    public Pio conId(Long id) {

        setId(id);
        return this;
    }

    public Pio conMensaje(String mensaje) {

        setMensaje(mensaje);
        return this;
    }

    public Pio conAutor(Usuario autor) {

        setAutor(autor);
        return this;
    }

    public Pio conFechaCreacion(Date fechaCreacion) {

        this.fechaCreacion = fechaCreacion;
        return this;
    }
    
    public Pio conComentario(Comentario comentario) {
        
        if (getComentarios() == null) {
            
            setComentarios(new ArrayList<>());
        }
        
        getComentarios().add(comentario);
        return this;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pio other = (Pio) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public void comentar(Comentario comentario) {
        this.comentarios.add(comentario);
        comentario.setPio(this);
    }

    public void eliminarComentario(Comentario comentario) {
        this.comentarios.remove(comentario);
        comentario.setPio(null);
    }
}
