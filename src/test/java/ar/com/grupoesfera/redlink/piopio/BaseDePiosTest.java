package ar.com.grupoesfera.redlink.piopio;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import ar.com.grupoesfera.redlink.piopio.modelo.Pio;
import ar.com.grupoesfera.redlink.piopio.modelo.Usuario;
import ar.com.grupoesfera.redlink.piopio.repo.BaseDePios;

public class BaseDePiosTest {

    private BaseDePios pios = new BaseDePios();

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
        
        Usuario autor = Usuario.nuevo().conNombre("Autor");
        Pio pioGuardado = pios.guardarCon(autor, "mensaje");
        
        Assert.assertThat(pioGuardado, Matchers.notNullValue());
        Assert.assertThat(pioGuardado.getId(), Matchers.notNullValue());
        Assert.assertThat(pioGuardado.getMensaje(), Matchers.is("mensaje"));
        Assert.assertThat(pioGuardado.getComentarios(), Matchers.nullValue());
    }
}
