package ar.com.grupoesfera.piopio.modelo;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Usuario.class)
public class Usuario_ {
    
    public static volatile SingularAttribute<Usuario, Long> id;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile ListAttribute<Usuario, Usuario> seguidos;

}
