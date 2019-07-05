package ar.com.grupoesfera.piopio.modelo;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Comentario.class)
public class Comentario_ {
    
    public static volatile SingularAttribute<Comentario, Long> id;
    public static volatile SingularAttribute<Comentario, String> texto;
    public static volatile SingularAttribute<Comentario, Usuario> autor;

}
