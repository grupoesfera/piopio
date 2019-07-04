package ar.com.grupoesfera.piopio;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.grupoesfera.main.Fixture;
import ar.com.grupoesfera.piopio.modelo.Favorito;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;
import ar.com.grupoesfera.piopio.repo.BaseDeFavoritos;

public class BaseDeFavoritosTest {
    
    private BaseDeFavoritos favoritos = new BaseDeFavoritos();
    
    @Before
    public void agregarDatos() {

        Fixture.initData();
    }

    @After
    public void eliminarDatos() {

        Fixture.dropData();
    }
    
    @Test
    public void deberiaObtenerTodosLosFavoritos() {
        
        List<Favorito> todosLosFavoritos = favoritos.obtenerTodos();
        Assert.assertThat(todosLosFavoritos, Matchers.hasSize(7));
    }
    
    @Test
    public void deberiaObtenerLosPiosFavoritadosPorUnUsuario() {
        
        Usuario fan = Usuario.nuevo().conId(3L);
        List<Pio> piosFavoritosDelUsuario = favoritos.obtenerPiosFavoritosDe(fan);
        Assert.assertThat(piosFavoritosDelUsuario, Matchers.hasSize(2));
    }

}
