package ar.com.grupoesfera.piopio.modelo.transformer;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.transform.ResultTransformer;

import ar.com.grupoesfera.piopio.modelo.dto.SeguidoresPorUsuario;

public class SeguidoresPorUsuarioTransformer implements ResultTransformer {

    private static final long serialVersionUID = -4464631771754548338L;
    
    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        
        String nombre = String.class.cast(tuple[0]);
        Integer cantidadDeSeguidores = BigInteger.class.cast(tuple[1]).intValue();
        
        return new SeguidoresPorUsuario(nombre, cantidadDeSeguidores);
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }
}
