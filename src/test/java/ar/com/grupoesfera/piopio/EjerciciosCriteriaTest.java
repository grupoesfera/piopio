package ar.com.grupoesfera.piopio;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.com.grupoesfera.main.Fixture;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;
import ar.com.grupoesfera.piopio.repo.EjerciciosCriteria;

public class EjerciciosCriteriaTest {
    
    private EjerciciosCriteria criteria = new EjerciciosCriteria();
    
    @Before
    public void agregarDatos() {

        Fixture.initData();
    }

    @After
    public void eliminarDatos() {

        Fixture.dropData();
    }
    
    @Test
    public void deberiaObtenerPioPorId() {

        Pio primerPioMarcelo = Pio.nuevo().conId(1L);
        
        assertThat(criteria.obtenerPor(1L), is( equalTo( primerPioMarcelo )));
    }
    
    @Test
    public void deberiaObtenerTodosLosPios() {

        List<Pio> todosLosPios = criteria.obtenerTodosLosPios();
        Assert.assertThat(todosLosPios, hasSize(5));
    }
    
    @Test
    public void deberiaObtenerTodosLosPiosConMensaje() {

        List<Pio> todosLosPios = criteria.obtenerPiosConMensaje("Hola");
        Assert.assertThat(todosLosPios, hasSize(2));
    }
    
    @Test
    public void actualizarMensajeDeUnPio() {
        
        criteria.actualizar(1L, "Mensaje actualizado");
        
        Pio pioActualizado = criteria.obtenerPor(1L);
        assertThat(pioActualizado.getMensaje(), is( equalTo( "Mensaje actualizado" )));
    }
    
    @Test
    public void eliminarPorId() {
        
        criteria.eliminarPor(1L);
        
        Pio pio = criteria.obtenerPor(1L);
        
        assertThat(pio, is (nullValue()));
    }
    
    @Test
    public void contarPios() {
        
        assertThat(criteria.contar(), is( equalTo( 5L )));
    }
    
    @Test
    public void contarCantidadDeCaracteresDelMensajeMasLargo() {
        assertThat(criteria.contarCaracteresDePioMasLargo(), is( equalTo( 28 )));
    }
    
    @Test
    public void contarCantidadDeCaracteresDelMensajeMasCorto() {
        assertThat(criteria.contarCaracteresDePioMasCorto(), is( equalTo( 4 )));
    }
    
    @Test
    public void buscarElPioMasLargo() {
        
        Pio pioMasLargo = Pio.nuevo().conId(2L);
        
        assertThat(criteria.obtenerPioMasLargo(), hasItem( pioMasLargo ));
    }
    
    @Test
    public void calcularLaCantidadPromedioDeCaracteresPorPio() {
        
        assertThat(criteria.obtenerPromedioDeCaracteres(), is( equalTo( 15.4 )));
    }
    
    @Test
    public void buscarPiosDeUnAnio() {
        
        List<Pio> piosDel2018 = criteria.buscarPorAnio(2018);
        assertThat(piosDel2018, hasSize( 3 ));
    }
    
    @Test
    public void buscarPiosEnUnRangoDeFechas() {
        
        Date fechaInicio = java.sql.Date.valueOf(LocalDate.of(2017, 12, 28));
        Date fechaFin = java.sql.Date.valueOf(LocalDate.of(2018, 1, 1));
        
        List<Pio> piosEnUnRangoDeFechas = criteria.buscarEntre(fechaInicio, fechaFin);
        assertThat(piosEnUnRangoDeFechas, hasSize( 2 ));
    }
    
    @Test
    public void obtenerTodosLosNombresDeUsuarios() {
        
        List<String> nombres = criteria.obtenerTodosLosNombresDeUsuarios();
        
        assertThat(nombres, hasItems("Marcelo", "Brenda", "India", "Leon", "Sebastian", "Alejandro", "Santiago"));
    }
    
    @Test
    public void obtenerLosPiosDeMarcelo() {
        
        Usuario marcelo = Usuario.nuevo().conId(1L);
        
        Pio primerPioMarcelo = Pio.nuevo().conId(1L);
        Pio segundoPioMarcelo = Pio.nuevo().conId(2L);
        
        List<Pio> piosDeMarcelo = criteria.obtenerPiosDe(marcelo);
        
        assertThat(piosDeMarcelo, contains(primerPioMarcelo, segundoPioMarcelo));
    }
    
    @Test
    public void buscarUsuariosInactivos() {
        
        Usuario sebastian = Usuario.nuevo().conId(5L).conNombre("Sebastian");
        Usuario alejandro = Usuario.nuevo().conId(6L).conNombre("Alejandro");
        Usuario santiago = Usuario.nuevo().conId(7L).conNombre("Santiago");
        
        List<Usuario> usuariosInactivos = criteria.obtenerSinActividad();
        assertThat(usuariosInactivos, hasItems(sebastian, alejandro, santiago));
    }
    
    @Test
    public void contarPiosDeUnUsuario() {
        
        Usuario marcelo = Usuario.nuevo().conId(1L);
        
        Long pios = criteria.contarPios(marcelo);
        
        assertThat(pios, is( equalTo( 2L )));
    }
    
    @Test
    public void contarACuantosUsuariosSigueUnUsuario() {
        Usuario marcelo = Usuario.nuevo().conId(1L);
        Usuario brenda = Usuario.nuevo().conId(2L);
        Usuario india = Usuario.nuevo().conId(3L);
        Usuario leon = Usuario.nuevo().conId(4L);
                
        assertThat(criteria.contarCuantosUsuariosSigue(marcelo), is( equalTo( 3 )));
        assertThat(criteria.contarCuantosUsuariosSigue(brenda), is( equalTo( 2 )));
        assertThat(criteria.contarCuantosUsuariosSigue(india), is( equalTo( 3 )));
        assertThat(criteria.contarCuantosUsuariosSigue(leon), is( equalTo( 0 )));
    }
    
    @Test
    public void deberiaObtenerLosSeguidoresDeUnUsuario() {

        Usuario marcelo = Usuario.nuevo().conId(1L).conNombre("Marcelo");
        List<Usuario> seguidoresDeMarcelo = criteria.obtenerSeguidoresDe(marcelo);
        assertThat(seguidoresDeMarcelo, hasSize( 3 ));
    }
    
    @Test
    public void deberiaEncontrarUsuariosAislados() {
        
        List<Usuario> aislados = criteria.obtenerAislados();
        assertThat(aislados, hasSize(1));
    }
    
    @Test
    public void obtenerComentariosDeUnUsuario() {
        
        Usuario brenda = Usuario.nuevo().conId(2L);
        
        assertThat(criteria.obtenerComentariosDe(brenda), hasSize( 2 ));
    }
    
    @Test
    public void deberiaContarLaCantidadDeComentariosRealizadosPorUnUsuario() {
        Usuario usuario = Usuario.nuevo().conId(2L);
        long cantidadDeComentarios = criteria.contarComentariosRealizadosPor(usuario);
        
        assertThat(cantidadDeComentarios, equalTo( 2L ));
    }
    
    @Test
    public void obtenerPiosSinComentar() {
        
        List<Pio> piosSinComentar = criteria.obtenerSinComentar();
        assertThat(piosSinComentar, hasSize( 3 ));
    }
    
    @Test
    public void obtenerPiosFavoritosDeUnUsuario() {
        
        Usuario brenda = Usuario.nuevo().conId(2L);
        
        assertThat(criteria.obtenerPiosFavoritosDe(brenda), hasSize( 3 ));
        
    }
    
    @Test
    public void obtenersFansDeUnPio() {
        
        Pio primerPioMarcelo = Pio.nuevo().conId(1L);
        assertThat(criteria.obtenerFansDel(primerPioMarcelo), hasSize( 2 ));
    }
    
    @Test
    public void obtenerPiosSinFans() {
        
        Pio pioLeon = Pio.nuevo().conId(5L);
        
        List<Pio> piosSinFans = criteria.obtenerPiosSinFans();
        
        assertThat(piosSinFans, contains( pioLeon ));
    }
    
    @Test
    public void contarFavoritosDeUnPio() {
        
        Pio primerPioMarcelo = Pio.nuevo().conId(1L);
        Pio segundoPioMarcelo = Pio.nuevo().conId(2L);
        Pio pioBrenda = Pio.nuevo().conId(3L);
        Pio pioIndia = Pio.nuevo().conId(4L);
        Pio pioLeon = Pio.nuevo().conId(5L);
        
        assertThat(criteria.contarFavoritosDeUnPio(primerPioMarcelo), is( equalTo( 2L )));
        assertThat(criteria.contarFavoritosDeUnPio(segundoPioMarcelo), is( equalTo( 4L )));
        assertThat(criteria.contarFavoritosDeUnPio(pioBrenda), is( equalTo( 3L )));
        assertThat(criteria.contarFavoritosDeUnPio(pioIndia), is( equalTo( 2L )));
        assertThat(criteria.contarFavoritosDeUnPio(pioLeon), is( equalTo( 0L )));
    }
    
    @Test
    public void obtenerUsuarioFanDeTodosLosPios() {
        
        Usuario leon = Usuario.nuevo().conId(4L);
        
        assertThat(criteria.obtenerUsuarioFanDeTodosLosPios(), hasItem( leon ));
    }

}
