package ar.com.grupoesfera.piopio.modelo;

import java.util.Date;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Pio.class)
public class Pio_ {
    
    public static volatile SingularAttribute<Pio, Long> id;
    public static volatile SingularAttribute<Pio, String> mensaje;
    public static volatile SingularAttribute<Pio, Date> fechaCreacion;
    public static volatile SingularAttribute<Pio, Usuario> autor;
    public static volatile ListAttribute<Pio, Comentario> comentarios;

}
