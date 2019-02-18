package ar.com.grupoesfera.piopio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.com.grupoesfera.main.PioServer;

public class UserAcceptanceTest {

    @BeforeClass
    public static void preparar() {
        
        PioServer.main(null);
    }
    
    @Test
    public void hola() throws Exception {

        RespuestaServicio respuesta = invocarServicio("hola");
        Assert.assertThat(respuesta.getCodigo(), Matchers.is(HttpStatus.SC_OK));
    }
     
    private RespuestaServicio invocarServicio(String urlServicio) throws Exception {
    	
    	final String urlBase = "http://localhost:8080/api/";
    	
    	RespuestaServicio respuesta = new RespuestaServicio();
    	
        URL url = new URL(urlBase + urlServicio);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        respuesta.setCodigo(connection.getResponseCode());
        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        respuesta.setTexto(input.readLine());
        
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
