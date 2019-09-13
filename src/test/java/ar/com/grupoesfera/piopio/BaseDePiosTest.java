package ar.com.grupoesfera.piopio;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.util.Date;
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
        Assert.assertThat(pioGuardado.getComentarios(), is( empty()));
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
    
    @Test
    public void contarPios() {
        
        assertThat(pios.contar(), is( equalTo( 5L )));
    }
    
    @Test
    public void buscarPiosEnUnRangoDeFechas() {
        
        Date fechaInicio = java.sql.Date.valueOf(LocalDate.of(2017, 12, 28));
        Date fechaFin = java.sql.Date.valueOf(LocalDate.of(2018, 1, 1));
        
        List<Pio> piosEnUnRangoDeFechas = pios.buscarEntre(fechaInicio, fechaFin);
        assertThat(piosEnUnRangoDeFechas, hasSize( 2 ));
    }
    
    @Test
    public void obtenerLosPiosDeMarcelo() {
        
        Usuario marcelo = Usuario.nuevo().conId(1L);
        
        Pio primerPioMarcelo = Pio.nuevo().conId(1L);
        Pio segundoPioMarcelo = Pio.nuevo().conId(2L);
        
        List<Pio> piosDeMarcelo = pios.obtenerPiosDe(marcelo);
        
        assertThat(piosDeMarcelo, contains(primerPioMarcelo, segundoPioMarcelo));
    }
    
    @Test
    public void contarPiosDeUnUsuario() {
        
        Usuario marcelo = Usuario.nuevo().conId(1L);
        
        Long piosDeMarcelo = pios.contarPios(marcelo);
        
        assertThat(piosDeMarcelo, is( equalTo( 2L )));
    }
    
    @Test
    public void deberiaObtenerTodosLosPiosConMensaje() {

        List<Pio> todosLosPios = pios.obtenerPiosConMensaje("Hola");
        Assert.assertThat(todosLosPios, hasSize(2));
    }
    
    @Test
    public void obtenerPiosFavoritosDeUnUsuario() {
        
        Usuario brenda = Usuario.nuevo().conId(2L);
        
        assertThat(pios.obtenerPiosFavoritosDe(brenda), hasSize( 3 ));
    }
    
    @Test
    public void contarFavoritosDeUnPio() {
        
        Pio primerPioMarcelo = Pio.nuevo().conId(1L);
        Pio segundoPioMarcelo = Pio.nuevo().conId(2L);
        Pio pioBrenda = Pio.nuevo().conId(3L);
        Pio pioIndia = Pio.nuevo().conId(4L);
        Pio pioLeon = Pio.nuevo().conId(5L);
        
        assertThat(pios.contarFavoritosDeUnPio(primerPioMarcelo), is( equalTo( 2L )));
        assertThat(pios.contarFavoritosDeUnPio(segundoPioMarcelo), is( equalTo( 4L )));
        assertThat(pios.contarFavoritosDeUnPio(pioBrenda), is( equalTo( 3L )));
        assertThat(pios.contarFavoritosDeUnPio(pioIndia), is( equalTo( 2L )));
        assertThat(pios.contarFavoritosDeUnPio(pioLeon), is( equalTo( 0L )));
    }
}
