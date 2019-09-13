package ar.com.grupoesfera.piopio;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.com.grupoesfera.main.Fixture;
import ar.com.grupoesfera.piopio.modelo.Comentario;
import ar.com.grupoesfera.piopio.modelo.Usuario;
import ar.com.grupoesfera.piopio.repo.BaseDeComentarios;

public class BaseDeComentariosTest {
    
    private BaseDeComentarios comentarios = new BaseDeComentarios();
    
    @Before
    public void agregarDatos() {

        Fixture.initData();
    }

    @After
    public void eliminarDatos() {

        Fixture.dropData();
    }
    
    @Test
    public void deberiaObtenerTodosLosComentarios() {
        
        List<Comentario> todosLosComentarios = comentarios.obtenerTodos();
        assertThat(todosLosComentarios, hasSize( 2 ));
    }
    
    @Test
    public void deberiaContarLaCantidadDeComentariosRealizadosPorUnUsuario() {
        Usuario usuario = Usuario.nuevo().conId(2L);
        long cantidadDeComentarios = comentarios.contarComentariosRealizadosPor(usuario);
        
        assertThat(cantidadDeComentarios, equalTo( 2L ));
    }
}
