package ar.com.grupoesfera.piopio;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

public class TestDeHumo extends SpringTest {
    
    @Test
    @Transactional
    public void deberiaObtenerUnaSession() {
        
        Assert.assertNotNull(this.getSession());
    }
}
