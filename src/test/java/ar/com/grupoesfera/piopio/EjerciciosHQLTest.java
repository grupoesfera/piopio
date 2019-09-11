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
import ar.com.grupoesfera.piopio.repo.EjerciciosHQL;

public class EjerciciosHQLTest {
    
    private EjerciciosHQL hql = new EjerciciosHQL();
    
    @Before
    public void agregarDatos() {

        Fixture.initData();
    }

    @After
    public void eliminarDatos() {

        Fixture.dropData();
    }
    
    @Test
    public void deberiaObtenerTodosLosPios() {

        List<Pio> todosLosPios = hql.obtenerTodosLosPios();
        Assert.assertThat(todosLosPios, hasSize(5));
    }
    
    @Test
    public void deberiaObtenerTodosLosPiosConMensaje() {

        List<Pio> todosLosPios = hql.obtenerPiosConMensaje("Hola");
        Assert.assertThat(todosLosPios, hasSize(2));
    }
    
    @Test
    public void actualizarMensajeDeUnPio() {
        
        hql.actualizar(1L, "Mensaje actualizado");
        
        Pio pioActualizado = hql.obtenerPor(1L);
        assertThat(pioActualizado.getMensaje(), is( equalTo( "Mensaje actualizado" )));
    }
    
    @Test
    public void eliminarPorId() {
        
        hql.eliminarPor(1L);
        
        Pio pio = hql.obtenerPor(1L);
        
        assertThat(pio, is (nullValue()));
    }
    
    @Test
    public void contarPios() {
        
        assertThat(hql.contar(), is( equalTo( 5L )));
    }
    
    @Test
    public void contarCantidadDeCaracteresDelMensajeMasLargo() {
        assertThat(hql.contarCaracteresDePioMasLargo(), is( equalTo( 28 )));
    }
    
    @Test
    public void contarCantidadDeCaracteresDelMensajeMasCorto() {
        assertThat(hql.contarCaracteresDePioMasCorto(), is( equalTo( 4 )));
    }
    
    @Test
    public void buscarElPioMasLargo() {
        
        Pio pioMasLargo = Pio.nuevo().conId(2L);
        
        assertThat(hql.obtenerPioMasLargo(), hasItem( pioMasLargo ));
    }
    
    @Test
    public void calcularLaCantidadPromedioDeCaracteresPorPio() {
        
        assertThat(hql.obtenerPromedioDeCaracteres(), is( equalTo( 15.4 )));
    }
    
    @Test
    public void buscarPiosDeUnAnio() {
        
        List<Pio> piosDel2018 = hql.buscarPorAnio(2018);
        assertThat(piosDel2018, hasSize( 3 ));
    }
    
    @Test
    public void buscarPiosEnUnRangoDeFechas() {
        
        Date fechaInicio = java.sql.Date.valueOf(LocalDate.of(2017, 12, 28));
        Date fechaFin = java.sql.Date.valueOf(LocalDate.of(2018, 1, 1));
        
        List<Pio> piosEnUnRangoDeFechas = hql.buscarEntre(fechaInicio, fechaFin);
        assertThat(piosEnUnRangoDeFechas, hasSize( 2 ));
    }
    
    @Test
    public void obtenerTodosLosNombresDeUsuarios() {
        
        List<String> nombres = hql.obtenerTodosLosNombresDeUsuarios();
        
        assertThat(nombres, hasItems("Marcelo", "Brenda", "India", "Leon", "Sebastian", "Alejandro", "Santiago"));
    }
    
    @Test
    public void obtenerLosPiosDeMarcelo() {
        
        Usuario marcelo = Usuario.nuevo().conId(1L);
        
        Pio primerPioMarcelo = Pio.nuevo().conId(1L);
        Pio segundoPioMarcelo = Pio.nuevo().conId(2L);
        
        List<Pio> piosDeMarcelo = hql.obtenerPiosDe(marcelo);
        
        assertThat(piosDeMarcelo, contains(primerPioMarcelo, segundoPioMarcelo));
    }
    
    @Test
    public void buscarUsuariosInactivos() {
        
        Usuario sebastian = Usuario.nuevo().conId(5L).conNombre("Sebastian");
        Usuario alejandro = Usuario.nuevo().conId(6L).conNombre("Alejandro");
        Usuario santiago = Usuario.nuevo().conId(7L).conNombre("Santiago");
        
        List<Usuario> usuariosInactivos = hql.obtenerSinActividad();
        assertThat(usuariosInactivos, hasItems(sebastian, alejandro, santiago));
    }
    
    @Test
    public void contarPiosDeUnUsuario() {
        
        Usuario marcelo = Usuario.nuevo().conId(1L);
        
        Long pios = hql.contarPios(marcelo);
        
        assertThat(pios, is( equalTo( 2L )));
    }
    
    @Test
    public void contarACuantosUsuariosSigueUnUsuario() {
        Usuario marcelo = Usuario.nuevo().conId(1L);
        Usuario brenda = Usuario.nuevo().conId(2L);
        Usuario india = Usuario.nuevo().conId(3L);
        Usuario leon = Usuario.nuevo().conId(4L);
                
        assertThat(hql.contarCuantosUsuariosSigue(marcelo), is( equalTo( 3 )));
        assertThat(hql.contarCuantosUsuariosSigue(brenda), is( equalTo( 2 )));
        assertThat(hql.contarCuantosUsuariosSigue(india), is( equalTo( 3 )));
        assertThat(hql.contarCuantosUsuariosSigue(leon), is( equalTo( 0 )));
    }
    
    @Test
    public void deberiaObtenerLosSeguidoresDeUnUusario() {

        Usuario marcelo = Usuario.nuevo().conId(1L).conNombre("Marcelo");
        List<Usuario> seguidoresDeMarcelo = hql.obtenerSeguidoresDe(marcelo);
        assertThat(seguidoresDeMarcelo, hasSize( 3 ));
    }
    
    @Test
    public void deberiaEncontrarUsuariosAislados() {
        
        List<Usuario> aislados = hql.obtenerAislados();
        assertThat(aislados, hasSize(1));
    }
    
    @Test
    public void obtenerComentariosDeUnUsuario() {
        
        Usuario brenda = Usuario.nuevo().conId(2L);
        
        assertThat(hql.obtenerComentariosDe(brenda), hasSize( 2 ));
    }
    
    @Test
    public void deberiaContarLaCantidadDeComentariosRealizadosPorUnUsuario() {
        Usuario usuario = Usuario.nuevo().conId(2L);
        long cantidadDeComentarios = hql.contarComentariosRealizadosPor(usuario);
        
        assertThat(cantidadDeComentarios, equalTo( 2L ));
    }
    
    @Test
    public void obtenerPiosSinComentar() {
        
        List<Pio> piosSinComentar = hql.obtenerSinComentar();
        assertThat(piosSinComentar, hasSize( 3 ));
    }
    
    @Test
    public void obtenerPiosFavoritosDeUnUsuario() {
        
        Usuario brenda = Usuario.nuevo().conId(2L);
        
        assertThat(hql.obtenerPiosFavoritosDe(brenda), hasSize( 3 ));
        
    }
    
    @Test
    public void obtenersFansDeUnPio() {
        
        Pio primerPioMarcelo = Pio.nuevo().conId(1L);
        assertThat(hql.obtenerFansDel(primerPioMarcelo), hasSize( 2 ));
    }
    
    @Test
    public void obtenerPiosSinFans() {
        
        Pio pioLeon = Pio.nuevo().conId(5L);
        
        List<Pio> piosSinFans = hql.obtenerPiosSinFans();
        
        assertThat(piosSinFans, contains( pioLeon ));
    }
    
    @Test
    public void contarFavoritosDeUnPio() {
        
        Pio primerPioMarcelo = Pio.nuevo().conId(1L);
        Pio segundoPioMarcelo = Pio.nuevo().conId(2L);
        Pio pioBrenda = Pio.nuevo().conId(3L);
        Pio pioIndia = Pio.nuevo().conId(4L);
        Pio pioLeon = Pio.nuevo().conId(5L);
        
        assertThat(hql.contarFavoritosDeUnPio(primerPioMarcelo), is( equalTo( 2L )));
        assertThat(hql.contarFavoritosDeUnPio(segundoPioMarcelo), is( equalTo( 4L )));
        assertThat(hql.contarFavoritosDeUnPio(pioBrenda), is( equalTo( 3L )));
        assertThat(hql.contarFavoritosDeUnPio(pioIndia), is( equalTo( 2L )));
        assertThat(hql.contarFavoritosDeUnPio(pioLeon), is( equalTo( 0L )));
    }
    
    @Test
    public void obtenerUsuarioFanDeTodosLosPios() {
        
        Usuario leon = Usuario.nuevo().conId(4L);
        
        assertThat(hql.obtenerUsuarioFanDeTodosLosPios(), hasItem( leon ));
    }
}
