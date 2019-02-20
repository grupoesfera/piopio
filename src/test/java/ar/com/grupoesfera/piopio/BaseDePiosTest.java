package ar.com.grupoesfera.piopio;

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

    private BaseDePios pios = new BaseDePios();
    
    @Before
    public void agregarDatos() {
        
        Fixture.initData();
    }
    
    @After
    public void eliminarDatos() {
        
        Fixture.dropData();
    }

    @Test
    public void deberiaGuardarUnPio() {
        
        Usuario autor = Usuario.nuevo().conNombre("Autor");
        Pio pioGuardado = pios.guardarCon(autor, "mensaje");
        
        Assert.assertThat(pioGuardado, Matchers.notNullValue());
        Assert.assertThat(pioGuardado.getId(), Matchers.notNullValue());
        Assert.assertThat(pioGuardado.getMensaje(), Matchers.is("mensaje"));
        Assert.assertThat(pioGuardado.getComentarios(), Matchers.nullValue());
    }
    
    @Test
    public void deberiaDevolverNullSiElPioAGuardarNoTieneAutor() {
        
        Usuario autor = null;
        Pio pioGuardado = pios.guardarCon(autor, "mensaje");
        
        Assert.assertThat(pioGuardado, Matchers.nullValue());
    }

    @Test
    public void deberiaDevolverNullSiElPioAGuardarNoTieneMensaje() {
        
        Usuario autor = Usuario.nuevo().conNombre("Autor");
        Pio pioGuardado = pios.guardarCon(autor, null);
        
        Assert.assertThat(pioGuardado, Matchers.nullValue());
    }

    @Test
    public void deberiaDevolverNullSiElPioAGuardarNoTieneAutorNiMensaje() {
        
        Usuario autor = null;
        Pio pioGuardado = pios.guardarCon(autor, null);
        
        Assert.assertThat(pioGuardado, Matchers.nullValue());
    }
}
