package ar.com.grupoesfera.piopio;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.com.grupoesfera.main.Fixture;
import ar.com.grupoesfera.piopio.modelo.Pio;

public class LevantarHibernateTest {
	
	private SessionFactory sessionFactory = new Configuration()
													.addResource("Pio.hbm.xml")
													.addResource("Usuario.hbm.xml")
													.addResource("Favorito.hbm.xml")
													.addResource("Comentario.hbm.xml")
													.buildSessionFactory();
	
    @Before
    public void agregarDatos() {
        
        Fixture.initData();
    }
    
    @After
    public void eliminarDatos() {
        
        Fixture.dropData();
    }
	
	@Test
    public void deberiaObtenerUnaSessionFactory() {

        assertThat(sessionFactory, notNullValue());
	}
	
	@Test
    public void deberiaObtenerUnaSesionDelSessionFactory() {

        Session sesion = sessionFactory.openSession();
        assertThat(sesion.isOpen(), is( true ));
    }
	
	@Test
	public void deberiaSerUnaEntidadPersistible() {
		
		Session sesion = sessionFactory.openSession();
		assertThat(sesion.getMetamodel().entity(Pio.class), notNullValue());
	}
	
    @Test
    @SuppressWarnings("unchecked")
    public void deberiaTraerElementosAlHacerUnQueryNativa() {

        Session sesion = sessionFactory.openSession();

        List<Object> pios = sesion.createNativeQuery("select * from pio").getResultList();

        assertThat(pios, hasSize(5));
    }
}
