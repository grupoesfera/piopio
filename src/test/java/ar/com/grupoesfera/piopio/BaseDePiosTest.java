package ar.com.grupoesfera.piopio;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.grupoesfera.main.Fixture;
import ar.com.grupoesfera.main.Transaction;
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
    public void deberiaObtenerTodosLosPios() {

        List<Pio> todosLosPios = pios.obtenerTodos();
        Assert.assertThat(todosLosPios, Matchers.hasSize(5));
    }

    @Test
    public void deberiaObtenerUnPio() {

        final Long ID_BUSQUEDA = 1L;
        Pio pioEsperado = Pio.nuevo().conId(ID_BUSQUEDA).conMensaje("Hola, este es mi primer pio");
        Assert.assertThat(pios.obtenerPor(ID_BUSQUEDA), Matchers.is(pioEsperado));
    }
    
    @Test
    public void deberiaGuardarUnPio() {
        
        Usuario autor = Usuario.nuevo().conId(100L).conNombre("Autor");

        Transaction.run((entities) -> {
            entities.persist(autor);
            return null;
        });

        Pio pioGuardado = pios.guardarCon(autor, "mensaje");
        
        Assert.assertThat(pioGuardado, Matchers.notNullValue());
        Assert.assertThat(pioGuardado.getId(), Matchers.notNullValue());
        Assert.assertThat(pioGuardado.getMensaje(), Matchers.is("mensaje"));
        Assert.assertThat(pioGuardado.getComentarios(), Matchers.nullValue());
    }
    
    @Test
    public void deberiaGuardarUnPioConId1SiLaBaseEstaVacia() {
        
        // Elimina los datos para asignar el ID 1
        Fixture.dropData();
        
        Usuario autor = Usuario.nuevo().conId(200L).conNombre("Autor");
        
        Transaction.run((entities) -> {
            entities.persist(autor);
            return null;
        });

        Pio pioGuardado = pios.guardarCon(autor, "mensaje");
        
        Assert.assertThat(pioGuardado, Matchers.notNullValue());
        Assert.assertThat(pioGuardado.getId(), Matchers.is(1L));
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

    @Test
    public void deberiaObtenerUnaListaVaciaSiElUsuarioNoPublicoPios() {
        
        Usuario usuarioSinPios = Usuario.nuevo().conId(5L);
        List<Pio> listaVacia = pios.obtenerPor(usuarioSinPios);
        
        Assert.assertThat(listaVacia, Matchers.empty());
    }

    @Test
    public void deberiaObtenerUnaListaConPiosSiElUsuarioPublicoPios() {
        
        Usuario usuarioConPios = Usuario.nuevo().conId(1L);
        List<Pio> piosDelUsuario = pios.obtenerPor(usuarioConPios);
        
        Assert.assertThat(piosDelUsuario, Matchers.not(Matchers.empty()));
    }
}
