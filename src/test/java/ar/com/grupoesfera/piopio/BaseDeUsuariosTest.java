package ar.com.grupoesfera.piopio;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.grupoesfera.main.Fixture;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;
import ar.com.grupoesfera.piopio.repo.BaseDeUsuarios;

public class BaseDeUsuariosTest {
    
    private BaseDeUsuarios usuarios = new BaseDeUsuarios();
    
    @Before
    public void agregarDatos() {
        
        Fixture.initData();
    }
    
    @After
    public void eliminarDatos() {
        
        Fixture.dropData();
    }
    
    @Test
    public void deberiaObtenerTodosLosUsuarios() {

        List<Usuario> todosLosUsuarios = usuarios.obtenerTodos();
        Assert.assertThat(todosLosUsuarios, Matchers.hasSize(7));
    }
    
    @Test
    public void deberiaEncontrarUsuariosAislados() {
        
        List<Usuario> aislados = usuarios.obtenerAislados();
        Assert.assertThat(aislados, Matchers.hasSize(1));
    }
    
    @Test
    public void deberiaObtenerUnUsuarioExistente() {
        
        Long id = 1L;
        Usuario usuario = usuarios.obtenerPor(id);
        Assert.assertThat(usuario.getNombre(), Matchers.is("Marcelo"));
    }
    
    @Test
    public void deberiaDarNullSiElUsuarioNoExiste() {
        
        Long id = 30L;
        Usuario usuario = usuarios.obtenerPor(id);
        Assert.assertThat(usuario, Matchers.nullValue());
    }
    
    @Test
    public void contarACuantosUsuariosSigueUnUsuario() {
        Usuario marcelo = Usuario.nuevo().conId(1L);
        Usuario brenda = Usuario.nuevo().conId(2L);
        Usuario india = Usuario.nuevo().conId(3L);
        Usuario leon = Usuario.nuevo().conId(4L);
                
        assertThat(usuarios.contarCuantosUsuariosSigue(marcelo), is( equalTo( 3 )));
        assertThat(usuarios.contarCuantosUsuariosSigue(brenda), is( equalTo( 2 )));
        assertThat(usuarios.contarCuantosUsuariosSigue(india), is( equalTo( 3 )));
        assertThat(usuarios.contarCuantosUsuariosSigue(leon), is( equalTo( 0 )));
    }
    
    @Test
    public void deberiaObtenerLosSeguidoresDeUnUusario() {

        Usuario marcelo = Usuario.nuevo().conId(1L).conNombre("Marcelo");
        List<Usuario> seguidoresDeMarcelo = usuarios.obtenerSeguidoresDe(marcelo);
        assertThat(seguidoresDeMarcelo, hasSize( 3 ));
    }
    
    @Test
    public void obtenersFansDeUnPio() {
        
        Pio primerPioMarcelo = Pio.nuevo().conId(1L);
        assertThat(usuarios.obtenerFansDel(primerPioMarcelo), hasSize( 2 ));
    }
}
