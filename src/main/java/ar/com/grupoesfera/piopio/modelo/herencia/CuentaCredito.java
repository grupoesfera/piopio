package ar.com.grupoesfera.piopio.modelo.herencia;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class CuentaCredito extends Cuenta {
    
    private BigDecimal limiteDeCredito;
    
    public void setLimiteDeCredito(BigDecimal limiteDeCredito) {
        this.limiteDeCredito = limiteDeCredito;
    }
    
    public BigDecimal getLimiteDeCredito() {
        return limiteDeCredito;
    }
}
