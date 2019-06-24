package ar.com.grupoesfera.piopio;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.grupoesfera.main.Fixture;
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
    
    @Test
    public void deberiaObtenerUnStringDadoVuelta() {
        
        String reverso = usuarios.obtenerReverso("123456789");
        Assert.assertThat(reverso, Matchers.is("987654321"));
    }

    @Test
    public void deberiaObtenerTodosLosNombres() {
        
        List<String> nombres = usuarios.obtenerNombres();
        Assert.assertThat(nombres, Matchers.containsInAnyOrder("Marcelo", "Brenda", "India", "Leon", "Sebastian", "Santiago", "Alejandro"));
    }
}
