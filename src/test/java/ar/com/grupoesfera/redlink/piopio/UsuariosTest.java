package ar.com.grupoesfera.redlink.piopio;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import ar.com.grupoesfera.redlink.piopio.modelo.Usuario;
import ar.com.grupoesfera.redlink.piopio.repo.Usuarios;

public class UsuariosTest {

    @Test
    public void deberiaObtenerTodosLosUsuarios() {

        Usuarios usuarios = new Usuarios();
        List<Usuario> todosLosUsuarios = usuarios.obtenerTodos();
        Assert.assertThat(todosLosUsuarios, Matchers.hasSize(5));
    }

    @Test
    public void deberiaObtenerLosSeguidoresDeUnUusario() {

        Usuarios usuarios = new Usuarios();
        Usuario marcelo = Usuario.nuevo().conId(1L).conNombre("Marcelo");
        List<Usuario> seguidoresDeMarcelo = usuarios.obtenerSeguidoresDe(marcelo);
        Assert.assertThat(seguidoresDeMarcelo, Matchers.hasSize(3));
    }
}
