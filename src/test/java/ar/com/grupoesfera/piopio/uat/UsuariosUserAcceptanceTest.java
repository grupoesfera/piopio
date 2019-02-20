package ar.com.grupoesfera.piopio.uat;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.jayway.jsonpath.matchers.JsonPathMatchers;

public class UsuariosUserAcceptanceTest extends UserAcceptanceTest {

    @Test
    public void deberiaDarUsuariosAlLlamarAUsuariosSinParametros() throws Exception {

        RespuestaServicio respuesta = invocarServicio("usuarios");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.isJson());
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$[*].id", Matchers.hasItems(1, 2, 3, 4, 5, 6, 7)));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$[*].nombre", 
            Matchers.hasItems("Marcelo", "Brenda", "India", "Leon", "Alejandro", "Santiago", "Sebastian")));
    }

    @Test
    public void deberiaDarNotFoundAlLlamarAUsuariosConIdValidoInexistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("usuarios/1000");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void deberiaDarNotFoundAlLlamarAUsuariosConIdInvalido() throws Exception {

        RespuestaServicio respuesta = invocarServicio("usuarios/id-invalido");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }
    
    @Test
    public void deberiaDarUsuariosAisladosAlLlamarAUsuariosAislados() throws Exception {
        
        RespuestaServicio respuesta = invocarServicio("usuarios/aislados");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.isJson());
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$[0].id", Matchers.is(4)));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$[0].nombre", Matchers.is("Leon")));
    }

    @Test
    public void deberiaDarLosUsuariosSeguidoresAlLlamarAUsuariosSeguidoresDeUnUsuarioExistente() throws Exception {
        
        RespuestaServicio respuesta = invocarServicio("usuarios/1/seguidores");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.isJson());
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$[*].id", Matchers.hasItems(2, 3, 5)));
    }

    @Test
    public void deberiaDarNotFoundAlLlamarUsuariosSeguidoresDeUnUsuarioInexistente() throws Exception {
        
        RespuestaServicio respuesta = invocarServicio("usuarios/1000/seguidores");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void deberiaDarLosUsuariosSeguidosAlLlamarAUsuariosSeguidosDeUnUsuarioExistente() throws Exception {
        
        RespuestaServicio respuesta = invocarServicio("usuarios/1/seguidos");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.isJson());
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$[*].id", Matchers.hasItems(2, 3, 5)));
    }

    @Test
    public void deberiaDarNotFoundAlLlamarUsuariosSeguidosDeUnUsuarioInexistente() throws Exception {
        
        RespuestaServicio respuesta = invocarServicio("usuarios/1000/seguidos");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void deberiaDarLosPiosAlLlamarAPiosDeUnUsuarioExistente() throws Exception {
        
        RespuestaServicio respuesta = invocarServicio("usuarios/1/pios");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.isJson());
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$[*].id", Matchers.hasItems(1, 2)));
    }

    @Test
    public void deberiaDarNotFoundAlLlamarPiosDeUnUsuarioInexistente() throws Exception {
        
        RespuestaServicio respuesta = invocarServicio("usuarios/1000/pios");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }
}
