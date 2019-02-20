package ar.com.grupoesfera.piopio.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Usuario {

    private Long id;
    private String nombre;
    
    @JsonIgnore private List<Usuario> seguidos;

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

    public List<Usuario> getSeguidos() {

        return seguidos;
    }

    public void setSeguidos(List<Usuario> seguidos) {

        this.seguidos = seguidos;
    }

    public static Usuario nuevo() {

        return new Usuario();
    }

    public Usuario conId(Long id) {

        setId(id);
        return this;
    }

    public Usuario conNombre(String nombre) {

        setNombre(nombre);
        return this;
    }
    
    public Usuario sigueA(Usuario... seguidos) {
        
        if (this.seguidos == null) {
            
            this.seguidos = new ArrayList<>();
        }
        
        this.seguidos.addAll(Arrays.asList(seguidos));
        
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
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
