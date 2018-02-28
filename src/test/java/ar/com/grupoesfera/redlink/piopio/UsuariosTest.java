package ar.com.grupoesfera.redlink.piopio;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import ar.com.grupoesfera.redlink.piopio.modelo.Usuario;
import ar.com.grupoesfera.redlink.piopio.repo.BaseDeUsuarios;

public class UsuariosTest {

    @Test
    public void deberiaObtenerTodosLosUsuarios() {

        BaseDeUsuarios usuarios = new BaseDeUsuarios();
        List<Usuario> todosLosUsuarios = usuarios.obtenerTodos();
        Assert.assertThat(todosLosUsuarios, Matchers.hasSize(7));
    }

    @Test
    public void deberiaObtenerLosSeguidoresDeUnUusario() {

        BaseDeUsuarios usuarios = new BaseDeUsuarios();
        Usuario marcelo = Usuario.nuevo().conId(1L).conNombre("Marcelo");
        List<Usuario> seguidoresDeMarcelo = usuarios.obtenerSeguidoresDe(marcelo);
        Assert.assertThat(seguidoresDeMarcelo, Matchers.hasSize(3));
    }
    
    @Test
    public void deberiaEncontrarUsuariosAislados() {
        
        BaseDeUsuarios usuarios = new BaseDeUsuarios();
        List<Usuario> aislados = usuarios.obtenerAislados();
        Assert.assertThat(aislados, Matchers.hasSize(1));
    }
}
