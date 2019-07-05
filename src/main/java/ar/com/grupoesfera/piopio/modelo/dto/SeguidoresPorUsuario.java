package ar.com.grupoesfera.piopio.modelo.dto;

public class SeguidoresPorUsuario {
    
    private String nombre;
    private Integer cantidadDeSeguidores;
    
    public SeguidoresPorUsuario() {}
    
    public SeguidoresPorUsuario(String nombre, Integer cantidadDeSeguidores) {
        this.nombre = nombre;
        this.cantidadDeSeguidores = cantidadDeSeguidores;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setCantidadDeSeguidores(Integer cantidadDeSeguidores) {
        this.cantidadDeSeguidores = cantidadDeSeguidores;
    }
    
    public Integer getCantidadDeSeguidores() {
        return cantidadDeSeguidores;
    }

}
