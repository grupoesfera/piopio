package ar.com.grupoesfera.piopio;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

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
        Assert.assertThat(todosLosPios, hasSize(5));
    }
    
    @Test
    public void deberiaObtenerUnPio() {

        final Long ID_BUSQUEDA = 1L;
        Pio pioEsperado = Pio.nuevo().conId(ID_BUSQUEDA).conMensaje("Hola, este es mi primer pio");
        Assert.assertThat(pios.obtenerPor(ID_BUSQUEDA), is(pioEsperado));
    }
    
    @Test
    public void deberiaGuardarUnPio() {
        
        Usuario autor = Usuario.nuevo().conNombre("Autor");
        Pio pioGuardado = pios.guardarCon(autor, "mensaje");
        
        Assert.assertThat(pioGuardado, notNullValue());
        Assert.assertThat(pioGuardado.getId(), notNullValue());
        Assert.assertThat(pioGuardado.getMensaje(), is("mensaje"));
        Assert.assertThat(pioGuardado.getComentarios(), empty());
    }
    
    @Test
    public void deberiaGuardarUnPioConId1SiLaBaseEstaVacia() {
        
        // Elimina los datos para asignar el ID 1
        Fixture.dropData();
        
        Usuario autor = Usuario.nuevo().conNombre("Autor");
        Pio pioGuardado = pios.guardarCon(autor, "mensaje");
        
        Assert.assertThat(pioGuardado, notNullValue());
        Assert.assertThat(pioGuardado.getId(), is(1L));
    }
    
    @Test
    public void deberiaDevolverNullSiElPioAGuardarNoTieneAutor() {
        
        Usuario autor = null;
        Pio pioGuardado = pios.guardarCon(autor, "mensaje");
        
        Assert.assertThat(pioGuardado, nullValue());
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
        
        Assert.assertThat(pioGuardado, nullValue());
    }
    
    @Test
    public void deberiaObtenerUnaListaVaciaSiElUsuarioNoPublicoPios() {
        
        Usuario usuarioSinPios = Usuario.nuevo().conId(5L);
        List<Pio> listaVacia = pios.obtenerPor(usuarioSinPios);
        
        Assert.assertThat(listaVacia, empty());
    }
    
    @Test
    public void deberiaObtenerUnaListaConPiosSiElUsuarioPublicoPios() {
        
        Usuario usuarioConPios = Usuario.nuevo().conId(1L);
        List<Pio> piosDelUsuario = pios.obtenerPor(usuarioConPios);
        
        Assert.assertThat(piosDelUsuario, not(empty()));
    }
    
    @Test
    public void deberiaObtenerLossPioQueContenganEnElMensajeElTextoIndicado() {
        
        List<Pio> piosEncontrados = pios.obtenerConTexto("pio"); 
        Assert.assertThat(piosEncontrados, hasSize(2));
    }
    
    @Test
    public void deberiaObtenerUnaListaVaciaDePiosSiNoSeCumpleElCriterio() {
        List<Pio> piosEncontrados = pios.obtenerConTexto("no hay pios con este texto");
        Assert.assertThat(piosEncontrados, empty());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void deberiaObtenerLosPiosQueFueronComentadosBuscandoPorNombreDeAutor() {
        
        List<Pio> piosComentados = pios.obtenerComentadosPor("Brenda");
        assertThat(piosComentados, iterableWithSize( 2 ));
        assertThat(piosComentados, hasItems(hasProperty("id", equalTo(1L)),
                                            hasProperty("id", equalTo(4L))));
    }
    
    @Test
    public void actualizarMensajeDeUnPio() {
        
        Pio pio = pios.obtenerPor(1L);
        pio.setMensaje("Mensaje actualizado");
        
        pios.actualizar(pio);
        
        Pio pioActualizado = pios.obtenerPor(1L);
        assertThat(pioActualizado.getMensaje(), is( equalTo( "Mensaje actualizado" )));
    }
    
    @Test
    public void eliminarPorId() {
        
        pios.eliminarPor(1L);
        
        Pio pio = pios.obtenerPor(1L);
        
        assertThat(pio, is (nullValue()));
    }
    
}
