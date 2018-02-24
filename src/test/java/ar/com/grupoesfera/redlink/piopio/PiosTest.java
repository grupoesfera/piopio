package ar.com.grupoesfera.redlink.piopio;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import ar.com.grupoesfera.redlink.piopio.modelo.Pio;
import ar.com.grupoesfera.redlink.piopio.repo.Pios;

public class PiosTest {

    @Test
    public void deberiaObtenerTodosLosPios() {
        Pios pios = new Pios();
        List<Pio> todosLosPios = pios.obtenerTodos();
        Assert.assertThat(todosLosPios, Matchers.hasSize(5));
    }
}
