package ar.com.grupoesfera.piopio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jayway.jsonpath.matchers.JsonPathMatchers;

import ar.com.grupoesfera.main.PioServer;

public class UserAcceptanceTest {

    @BeforeClass
    public static void preparar() {

        PioServer.main(null);
    }

    @Test
    public void deberiaDecirHolaMundo() throws Exception {

        RespuestaServicio respuesta = invocarServicio("hola");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
        Assert.assertThat(respuesta.getTexto(), Matchers.is("Hola, mundo!"));
    }

    @Test
    public void deberiaDarNotImplementedAlLlamarAPiosSinParametros() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_IMPLEMENTED));
    }

    @Test
    public void deberiaDevolverPioJSONAlLlamarAPiosConIdValidoExistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios/1");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.isJson());
        Assert.assertThat(respuesta.getTexto(), JsonPathMatchers.hasJsonPath("$.id", Matchers.is(1)));
    }

    @Test
    public void deberiaDevolverNotFoundAlLlamarAPiosConIdValidoInexistente() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios/1000");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void deberiaDevolverNotFoundAlLlamarAPiosConIdInvalido() throws Exception {

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
            Matchers.both(Matchers.greaterThan(new Date().getTime() - 500)).and(Matchers.lessThan(new Date().getTime() + 500))));
    }

    @Test
    public void deberiaDarBadRequestAlLlamarAPiosConMensajeYUsuarioInvalido() throws Exception {

        RespuestaServicio respuesta = invocarServicio("pios", "usuario=invalido", "mensaje=UnPioNuevo");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_BAD_REQUEST));
        Assert.assertThat(respuesta.getTexto(), Matchers.is("Pio no creado. El usuario 'invalido' es inválido."));
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

    private RespuestaServicio invocarServicio(String urlServicio, String... params) throws Exception {

        final String urlBase = "http://localhost:8080/api/";

        RespuestaServicio respuesta = new RespuestaServicio();

        try {

            URL url = null;
            HttpURLConnection connection = null;
            
            if (params.length > 0) {

                String query = "";

                for (String param : params) {

                    query = query + "&" + param;
                }
                
                query = query.substring(1);

                url = new URL(urlBase + urlServicio + "?" + query);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

            } else {

                url = new URL(urlBase + urlServicio);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
            }


            respuesta.setCodigo(connection.getResponseCode());

            if (respuesta.getCodigo().equals(HttpStatus.SC_OK)) {

                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                respuesta.setTexto(input.readLine());
            }

        } catch (Exception e) {

            Assert.fail("La prueba falló por un error de conexión");
            LogFactory.getLog(UserAcceptanceTest.class).error(e);
        }

        return respuesta;
    }

    public class RespuestaServicio {

        private Integer codigo;
        private String texto;

        public Integer getCodigo() {

            return codigo;
        }

        public void setCodigo(Integer codigo) {

            this.codigo = codigo;
        }

        public String getTexto() {

            return texto;
        }

        public void setTexto(String texto) {

            this.texto = texto;
        }
    }
}
