package ar.com.grupoesfera.piopio;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.grupoesfera.main.Fixture;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;
import ar.com.grupoesfera.piopio.repo.BaseDePios;

public class BaseDePiosTest {
	
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
