package ar.com.grupoesfera.piopio.modelo;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Favorito.class)
public class Favorito_ {
    
    public static volatile SingularAttribute<Favorito, Long> id;
    public static volatile SingularAttribute<Favorito, Pio> pio;
    public static volatile SingularAttribute<Favorito, Usuario> fan;

}
