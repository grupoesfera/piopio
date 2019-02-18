package ar.com.grupoesfera.piopio;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.com.grupoesfera.main.Fixture;

public class LevantarJPATest {

    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("piopio");

    @Before
    public void agregarDatos() {
        
        Fixture.initData();
    }
    
    @After
    public void eliminarDatos() {
        
        Fixture.dropData();
    }

    @Test
    public void deberiaObtenerUnEntityManagerFactory() {

        assertThat(factory, notNullValue(EntityManagerFactory.class));
    }

    @Test
    public void deberiaObtenerUnEntityManagerDelFactory() {

        EntityManager entities = factory.createEntityManager();
        assertThat(entities, notNullValue(EntityManager.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void deberiaTraerElementosAlHacerUnQueryNativa() {

        EntityManager entities = factory.createEntityManager();

        List<Object> pios = entities.createNativeQuery("select * from pio").getResultList();

        assertThat(pios, hasSize(5));
    }
}
