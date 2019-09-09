package ar.com.grupoesfera.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class EjemploJDBC {
    
    public static void main(String[] args) {
        
        Fixture.initData();
        
        List<Pio> pios = new LinkedList<>();
        
        try {
        
        Class.forName("org.h2.Driver");
        Connection conexion = DriverManager.getConnection("jdbc:h2:mem:piopio;DB_CLOSE_DELAY=-1;MVCC=TRUE", 
                "sa", "");
        
        Statement statement = conexion.createStatement();
        ResultSet rs = statement.executeQuery("select * from Pio");
        while(rs.next()) {
            
            Pio pio = new Pio();
            pio.setId(rs.getLong("id"));
            pio.setMensaje(rs.getString("mensaje"));
            pio.setFechaCreacion(rs.getDate("fechaCreacion"));
            
            Usuario usuario = new Usuario();
            usuario.setId(rs.getLong("autor_id"));
            
            pio.setAutor(usuario);
            
            pios.add(pio);
        }
        
        }catch (Exception e) {
        
            System.err.println("Ocurrió un error inesperado. Mensaje: " + e.getMessage());
            
        }
        
        System.out.println(pios);
        
        System.exit(0);
    }
}
