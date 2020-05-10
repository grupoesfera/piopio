package ar.com.grupoesfera.piopio.modelo;

public class Comentario {

    private Long id;
    private String mensaje;
    private Usuario autor;
    
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
}
