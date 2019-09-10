package ar.com.grupoesfera.piopio.repo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class EjerciciosHQL {
    
    public Pio obtenerPor(Long id) {
        return App.instancia().obtenerSesion().get(Pio.class, id);
    }
    
    public List<Pio> obtenerTodosLosPios() {
        
        return App.instancia().obtenerSesion().createQuery("from Pio p", Pio.class).list();
    }
    
    public List<Pio> obtenerPiosConMensaje(String mensaje) {
        
        return App.instancia().obtenerSesion().createQuery("from Pio where mensaje like :mensaje", Pio.class)
                    .setParameter("mensaje", "%" + mensaje + "%")
                    .list();
        
    }
    
    public void actualizar(Long id, String mensaje) {

        Session session = App.instancia().obtenerSesion();
        Pio pio = session.get(Pio.class, id);
        
        session.merge(pio);
        pio.setMensaje(mensaje);

        Transaction transaccion = session.beginTransaction();
        transaccion.commit();
        session.close();
    }
    
    public void eliminarPor(Long id) {

        Session session = App.instancia().obtenerSesion();
        Transaction transaccion = session.beginTransaction();

        try {
            
            session.createQuery("delete Favorito where pio.id = :id").setParameter("id", id).executeUpdate();
            
            Pio pio = session.load(Pio.class, id);
            pio.borrarComentarios();
            session.remove(pio);

            transaccion.commit();

        } catch (Exception e) {

            transaccion.rollback();

        } finally {

            session.close();
        }

    }
    
    public Long contar() {
        return App.instancia()
                  .obtenerSesion()
                  .createQuery("select count (p) from Pio p", Long.class)
                  .uniqueResult();
    }
    
    public Integer contarCaracteresDePioMasLargo() {
        return App.instancia()
                .obtenerSesion()
                .createQuery("select max(length(p.mensaje)) from Pio p", Integer.class)
                .uniqueResult();
    }
    
    public Integer contarCaracteresDePioMasCorto() {
        return App.instancia()
                .obtenerSesion()
                .createQuery("select min(length(p.mensaje)) from Pio p", Integer.class)
                .uniqueResult();
    }
    
    public List<Pio> obtenerPioMasLargo() {
        return App.instancia().obtenerSesion()
                .createQuery("select p from Pio p where length(p.mensaje) = (select max(length(mensaje)) from Pio)", Pio.class)
                .list();
    }
    
    public Double obtenerPromedioDeCaracteres() {
        return App.instancia().obtenerSesion()
                .createQuery("select avg(length(mensaje)) from Pio", Double.class)
                .uniqueResult();
    }
    
    public List<Pio> buscarPorAnio(int anio) {
        return App.instancia().obtenerSesion()
                .createQuery("from Pio where YEAR(fechaCreacion) = :anio", Pio.class)
                .setParameter("anio", anio)
                .list();
    }
    
    public List<Pio> buscarEntre(Date fechaInicio, Date fechaFin) {
        return App.instancia().obtenerSesion()
            .createQuery("from Pio where fechaCreacion >= :fechaInicio and fechaCreacion <= :fechaFin", Pio.class)
            .setParameter("fechaInicio", fechaInicio)
            .setParameter("fechaFin", fechaFin)
            .list();
    }
    
    public List<Pio> obtenerSinComentar() {
        return App.instancia().obtenerSesion()
                .createQuery("From Pio p where p.comentarios is empty", Pio.class)
                .list();
    }
    
    public List<String> obtenerTodosLosNombresDeUsuarios() {
        return App.instancia().obtenerSesion().createQuery("select nombre from Usuario", String.class).list();
    }

    public List<Pio> obtenerPiosDe(Usuario usuario) {
        return App.instancia().obtenerSesion()
                .createQuery("select p from Pio p where p.autor = :autor", Pio.class)
                .setParameter("autor", usuario)
                .list();
    }
}
