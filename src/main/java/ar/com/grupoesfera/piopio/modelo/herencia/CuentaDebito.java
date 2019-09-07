package ar.com.grupoesfera.piopio.modelo.herencia;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class CuentaDebito extends Cuenta {
    
    private BigDecimal cargoPorSobregiro;
    
    public BigDecimal getCargoPorSobregiro() {
        return cargoPorSobregiro;
    }
    
    public void setCargoPorSobregiro(BigDecimal cargoPorSobregiro) {
        this.cargoPorSobregiro = cargoPorSobregiro;
    }
    
    @Override
    public String toString() {
        return "CuentaDebito[saldo:"+this.getSaldo()+"]";
    }
}