package ar.com.grupoesfera.redlink.piopio.modelo;

import java.util.List;

public class Usuario {

    private Long id;
    private String nombre;
    private List<Usuario> seguidores;
    private List<Usuario> seguidos;
    
    public Long getId() {
    
        return id;
    }
    
    public void setId(Long id) {
    
        this.id = id;
    }
    
    public String getNombre() {
    
        return nombre;
    }
    
    public void setNombre(String nombre) {
    
        this.nombre = nombre;
    }
    
    public List<Usuario> getSeguidores() {
    
        return seguidores;
    }
    
    public void setSeguidores(List<Usuario> seguidores) {
    
        this.seguidores = seguidores;
    }
    
    public List<Usuario> getSeguidos() {
    
        return seguidos;
    }
    
    public void setSeguidos(List<Usuario> seguidos) {
    
        this.seguidos = seguidos;
    }
}
