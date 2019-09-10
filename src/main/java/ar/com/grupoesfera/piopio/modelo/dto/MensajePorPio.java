package ar.com.grupoesfera.piopio.modelo.dto;

public class MensajePorPio {
    
    private Long id;
    private String mensaje;
    
    public MensajePorPio(Long id, String mensaje) {
        this.id = id;
        this.mensaje = mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return "id:" + id + "-mensaje:" + mensaje;
    }

}
