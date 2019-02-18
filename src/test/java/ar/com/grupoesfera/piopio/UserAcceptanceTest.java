package ar.com.grupoesfera.piopio;

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

        URL url = new URL("http://localhost:8080/api/hola");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        Assert.assertThat(connection.getResponseCode(), Matchers.is(HttpStatus.SC_OK));
    }
}
