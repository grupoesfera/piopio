package ar.com.grupoesfera.piopio;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.grupoesfera.main.Fixture;

public class BaseDeComentariosTest {
    
    @Before
    public void agregarDatos() {

        Fixture.initData();
    }

    @After
    public void eliminarDatos() {

        Fixture.dropData();
    }
    
    @Test
    public void deberiasEscribirPruebasUnitarias() {

        Assert.fail("Tus clases deberían estar probadas y con Cobertura completa");
    }   
}
