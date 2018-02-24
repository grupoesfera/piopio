package ar.com.grupoesfera.redlink.piopio.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Usuario {

    @Id private Long id;

    @Column private String nombre;

    @Transient private List<Usuario> seguidos;

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
        
        if (seguidos == null) {
            
            this.seguidos = new ArrayList<>();
        }
        
        this.seguidos.addAll(Arrays.asList(seguidos));
        
        return this;
    }
}
