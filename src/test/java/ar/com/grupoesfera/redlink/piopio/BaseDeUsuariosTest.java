package ar.com.grupoesfera.redlink.piopio;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import ar.com.grupoesfera.redlink.piopio.modelo.Usuario;
import ar.com.grupoesfera.redlink.piopio.repo.BaseDeUsuarios;

public class BaseDeUsuariosTest {
    
    private BaseDeUsuarios usuarios = new BaseDeUsuarios();

    @Test
    public void deberiaObtenerTodosLosUsuarios() {

        List<Usuario> todosLosUsuarios = usuarios.obtenerTodos();
        Assert.assertThat(todosLosUsuarios, Matchers.hasSize(7));
    }

    @Test
    public void deberiaObtenerLosSeguidoresDeUnUusario() {

        Usuario marcelo = Usuario.nuevo().conId(1L).conNombre("Marcelo");
        List<Usuario> seguidoresDeMarcelo = usuarios.obtenerSeguidoresDe(marcelo);
        Assert.assertThat(seguidoresDeMarcelo, Matchers.hasSize(3));
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
}
