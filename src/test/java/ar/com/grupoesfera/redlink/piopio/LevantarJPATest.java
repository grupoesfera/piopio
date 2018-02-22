package ar.com.grupoesfera.redlink.piopio;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

public class LevantarJPATest {

    private EntityManagerFactory ref1;
    private EntityManagerFactory ref2;
    
    @Test
    public void deberiaObtenerUnEntityManagerFactory() {
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("piopio");
        ref1 = factory;
        assertThat(factory, notNullValue(EntityManagerFactory.class));
    }
    
    @Test
    public void deberiaObtenerUnEntityManagerDelFactory() {
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("piopio");
        ref2 = factory;
        EntityManager entities = factory.createEntityManager();
        assertThat(entities, notNullValue(EntityManager.class));
    }
    
    @Test
    public void deberiaObtenerUnUnicoFactory() {
        assertThat(ref1, sameInstance(ref2));
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void deberiaTraerElementosAlHacerUnQueryNativa() {
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("piopio");
        EntityManager entities = factory.createEntityManager();
        
        List<Object> pios = entities.createNativeQuery("select * from pio").getResultList();
        
        assertThat(pios, hasSize(1));
    }
}
