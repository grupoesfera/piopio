package ar.com.grupoesfera.piopio;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

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
        Assert.assertThat(todosLosFavoritos, hasSize(11));
    }
    
    @Test
    public void deberiaObtenerLosPiosFavoritadosPorUnUsuario() {
        
        Usuario fan = Usuario.nuevo().conId(3L);
        List<Pio> piosFavoritosDelUsuario = favoritos.obtenerPiosFavoritosDe(fan);
        Assert.assertThat(piosFavoritosDelUsuario, hasSize(2));
    }
    
    @Test
    public void deberiaObtenerLosUsuariosFanDeUnPio() {
        
        Pio pio = Pio.nuevo().conId(2L);
        List<Usuario> fans = favoritos.obtenerFansDel(pio);
        Assert.assertThat(fans, hasSize(4));
    }
    
    @Test
    public void deberiaObtenerPiosSinFans() {
        
        List<Pio> piosSinFans = favoritos.obtenerPiosSinFans();
        
        Assert.assertThat(piosSinFans, hasSize(1));
    }  

    @Test
    public void deberiaObtenerUnFavorito() {
        
        final Long ID_BUSQUEDA = 1L;
        Pio pioFavoriteado = Pio.nuevo().conId(1L);
        Usuario fan = Usuario.nuevo().conId(2L);
        Favorito favoritoEsperado = Favorito.nuevo().conId(ID_BUSQUEDA)
                                                    .conPio(pioFavoriteado)
                                                    .conFan(fan);
        
        Favorito favoritoObtenido = favoritos.obtenerPor(ID_BUSQUEDA);
        
        Assert.assertThat(favoritoObtenido, equalTo(favoritoEsperado));
        Assert.assertThat(favoritoObtenido.getFan(), equalTo(fan));
        Assert.assertThat(favoritoObtenido.getPio(), equalTo(pioFavoriteado));
    }

    @Test
    public void deberiaGuardarUnFavorito() {
        
        Pio pio = Pio.nuevo().conId(5L);
        Usuario fan = Usuario.nuevo().conId(1L);        
        Favorito favoritoGuardado = favoritos.guardarCon(fan, pio);
        
        Assert.assertThat(favoritoGuardado, notNullValue());
        Assert.assertThat(favoritoGuardado, equalTo(favoritoGuardado));
        Assert.assertThat(favoritoGuardado.getFan(), equalTo(fan));
        Assert.assertThat(favoritoGuardado.getPio(), equalTo(pio));
    }
    
    @Test
    public void deberiaDevolverNullSiElFavoritoAGuardarNoTieneFan() {
        
        Pio pio = Pio.nuevo().conId(5L);
        Favorito favoritoGuardado = favoritos.guardarCon(null, pio);
        
        Assert.assertThat(favoritoGuardado, nullValue());
    }
    
    @Test
    public void deberiaDevolverNullSiElFavoritoAGuardarNoTienePio() {
        
        Usuario fan = Usuario.nuevo().conId(1L);        
        Favorito favoritoGuardado = favoritos.guardarCon(fan, null);
        
        Assert.assertThat(favoritoGuardado, nullValue());
    }
    
    @Test
    public void deberiaDevolverNullSiElFavoritoAGuardarNoTieneFanNiPio() {
        
        Favorito favoritoGuardado = favoritos.guardarCon(null, null);
        
        Assert.assertThat(favoritoGuardado, nullValue());
    }
    
    @Test
    public void LeonDeberiaSerElUsuarioFanDeTodosLosPios() {
        
        List<Usuario> usuariosSuperFans = favoritos.obtenerUsuariosFansDeTodosLosPios();
        Assert.assertThat(usuariosSuperFans, hasSize( 1 ));
        Assert.assertThat(usuariosSuperFans, hasItem(hasProperty("nombre", is("Leon"))));
    }
}
