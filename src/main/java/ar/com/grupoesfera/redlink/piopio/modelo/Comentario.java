package ar.com.grupoesfera.redlink.piopio.modelo;


public class Comentario {

    private String mensaje;
    private Usuario autor;
    
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
