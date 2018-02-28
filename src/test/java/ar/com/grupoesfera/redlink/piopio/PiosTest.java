package ar.com.grupoesfera.redlink.piopio;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import ar.com.grupoesfera.redlink.piopio.modelo.Pio;
import ar.com.grupoesfera.redlink.piopio.repo.BaseDePios;

public class PiosTest {

    @Test
    public void deberiaObtenerTodosLosPios() {
        BaseDePios pios = new BaseDePios();
        List<Pio> todosLosPios = pios.obtenerTodos();
        Assert.assertThat(todosLosPios, Matchers.hasSize(5));
    }
}
