package ar.com.grupoesfera.piopio.uat;

import java.util.Date;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.jayway.jsonpath.matchers.JsonPathMatchers;

public class PiosUserAcceptanceTest extends UserAcceptanceTest {

    @Test
    public void deberiaDarNotImplementedAlLlamarAPiosSinParametros() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_IMPLEMENTED));
    }

    @Test
    public void deberiaDarPioJSONAlLlamarAPiosConIdValidoExistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios/1");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.isJson());
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$.id", Matchers.is(1)));
    }

    @Test
    public void deberiaDarNotFoundAlLlamarAPiosConIdValidoInexistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios/1000");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void deberiaDarNotFoundAlLlamarAPiosConIdInvalido() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios/id-invalido");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void deberiaDarJSONNuevoPioAlLlamarAPiosConMensajeYUsuarioExistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios", "usuario=1", "mensaje=UnPioNuevo");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_CREATED));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.isJson());
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$.mensaje", Matchers.is("UnPioNuevo")));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$.autor.id", Matchers.is(1)));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$.autor.nombre", Matchers.is("Marcelo")));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$.fechaCreacion",
            Matchers.both(Matchers.greaterThan(new Date().getTime() - 5000)).and(Matchers.lessThan(new Date().getTime() + 5000))));
    }

    @Test
    public void deberiaDarNotFoundAlLlamarAPiosConMensajeYUsuarioInvalido() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios", "usuario=invalido", "mensaje=UnPioNuevo");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void deberiaDarBadRequestAlLlamarAPiosConMensajeYUsuarioValidoInexistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios", "usuario=1000", "mensaje=UnPioNuevo");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_BAD_REQUEST));
        Assert.assertThat(respuesta.getTexto(), Matchers.is("Pio no creado. El usuario '1000' no existe."));
    }

    @Test
    public void deberiaDarBadRequestAlLlamarAPiosSinMensajeYUsuarioValidoExistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios", "usuario=1");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_BAD_REQUEST));
        Assert.assertThat(respuesta.getTexto(), Matchers.is("Pio no creado. El pio no tiene mensaje."));
    }
}
