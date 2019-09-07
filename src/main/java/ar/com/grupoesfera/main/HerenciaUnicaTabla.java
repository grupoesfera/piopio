package ar.com.grupoesfera.main;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;

import ar.com.grupoesfera.piopio.modelo.herencia.Cuenta;
import ar.com.grupoesfera.piopio.modelo.herencia.CuentaCredito;
import ar.com.grupoesfera.piopio.modelo.herencia.CuentaDebito;

public class HerenciaUnicaTabla {
    
    public static void main(String[] args) {
        
        CuentaCredito cuentaCredito = new CuentaCredito();
        cuentaCredito.setId( 1L );
        cuentaCredito.setDuenio( "Marcelo Gore" );
        cuentaCredito.setSaldo( BigDecimal.valueOf( 250000 ) );
        cuentaCredito.setTasaDeInteres( BigDecimal.valueOf( 1.9d ) );
        cuentaCredito.setLimiteDeCredito( BigDecimal.valueOf( 60000 ) );
        
        CuentaDebito cuentaDebito = new CuentaDebito();
        cuentaDebito.setId(2L);
        cuentaDebito.setDuenio( "Marcelo Gore" );
        cuentaDebito.setSaldo( BigDecimal.valueOf( 90000 ));
        cuentaDebito.setTasaDeInteres( BigDecimal.valueOf( 1.5d ) );
        cuentaDebito.setCargoPorSobregiro( BigDecimal.valueOf( 100 ));
        
        Session session = App.instancia().obtenerSesion();
        
        session.persist(cuentaDebito);
        session.persist(cuentaCredito);
        
        session.beginTransaction().commit();
        
        List<Cuenta> cuentas = session
                .createQuery( "select c from Cuenta c", Cuenta.class )
                .getResultList();
        
        System.out.println(cuentas);
        
        List<CuentaDebito> cuentasDebito = session.createQuery("select cd from CuentaDebito cd", CuentaDebito.class).list();
        
        System.out.println(cuentasDebito);
        
        session.close();
        System.exit(0);
        
    }

}
