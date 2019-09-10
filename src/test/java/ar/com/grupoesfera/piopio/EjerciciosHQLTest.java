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
    public void obtenerPiosSinComentar() {
        
        List<Pio> piosSinComentar = hql.obtenerSinComentar();
        assertThat(piosSinComentar, hasSize( 3 ));
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

}
