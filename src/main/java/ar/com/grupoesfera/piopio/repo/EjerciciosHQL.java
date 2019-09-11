package ar.com.grupoesfera.piopio.repo;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Comentario;
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
    
    public List<String> obtenerTodosLosNombresDeUsuarios() {
        return App.instancia().obtenerSesion().createQuery("select nombre from Usuario", String.class).list();
    }

    public List<Pio> obtenerPiosDe(Usuario usuario) {
        return App.instancia().obtenerSesion()
                .createQuery("select p from Pio p where p.autor = :autor", Pio.class)
                .setParameter("autor", usuario)
                .list();
    }
    
    public List<Usuario> obtenerSinActividad() {
        return App.instancia().obtenerSesion()
                              .createQuery("from Usuario u where not exists ( from Pio p where p.autor = u )", Usuario.class)
                              .list();
    }

    public Long contarPios(Usuario usuario) {
        return App.instancia().obtenerSesion().createQuery("select count(p) from Pio p where p.autor = :autor", Long.class)
                .setParameter("autor", usuario)
                .uniqueResult();
    }
    
    public Integer contarCuantosUsuariosSigue(Usuario usuario) {
        return App.instancia().obtenerSesion()
                .createQuery("select size(seguidos) from Usuario u where u = :usuario", Integer.class)
                .setParameter("usuario", usuario)
                .uniqueResult();
    }
    
    public List<Usuario> obtenerSeguidoresDe(Usuario usuario) {
        return App.instancia().obtenerSesion().createQuery("from Usuario u where :usuario member of u.seguidos", Usuario.class)
                                              .setParameter("usuario", usuario)
                                              .list();
    }
    
    public List<Usuario> obtenerAislados() {
        return App.instancia().obtenerSesion()
                .createQuery("from Usuario u where u.seguidos is empty and not exists (select u2 from Usuario u2 where u member of u2.seguidos)", Usuario.class)
                .list();
    }
    
    public List<Comentario> obtenerComentariosDe(Usuario usuario) {
        return App.instancia().obtenerSesion()
                    .createQuery("from Comentario where autor = :autor", Comentario.class)
                    .setParameter("autor", usuario)
                    .list();
    }
    
    public long contarComentariosRealizadosPor(Usuario usuario) {
        
        return App.instancia().obtenerSesion()
                    .createNamedQuery("contarComentariosRealizadosPor", Long.class)
                    .setParameter("autor", usuario)
                    .uniqueResult();
    }
    
    public List<Pio> obtenerSinComentar() {
        return App.instancia().obtenerSesion()
                .createQuery("From Pio p where p.comentarios is empty", Pio.class)
                .list();
    }
    
    public List<Pio> obtenerPiosFavoritosDe(Usuario fan) {

        return App.instancia().obtenerSesion()
                .createQuery("select f.pio from Favorito f where f.fan = :fan", Pio.class)
                .setParameter("fan", fan)
                .list();
    }
    
    public List<Usuario> obtenerFansDel(Pio pio) {
        return App.instancia().obtenerSesion()
                .createQuery("select f.fan from Favorito f where f.pio = :pio", Usuario.class)
                .setParameter("pio", pio)
                .list();
    }
    
    public List<Pio> obtenerPiosSinFans() {
        return App.instancia().obtenerSesion()
                .createQuery("from Pio p where not exists ( from Favorito f where f.pio = p )", Pio.class)
                .list();
    }
    
    public Long contarFavoritosDeUnPio(Pio pio) {
        
        Long cantidad = App.instancia().obtenerSesion()
                .createQuery("select count( f.pio) from Favorito f where f.pio = :pio group by f.pio", Long.class)
                .setParameter("pio", pio)
                .uniqueResult();
        
        return cantidad == null ? 0 : cantidad;
    }
    
    public List<Usuario> obtenerUsuarioFanDeTodosLosPios() {
        
        return App.instancia().obtenerSesion().createQuery("from Usuario u where u not in ( "
                + "select fan from Pio pio, Usuario fan where pio.autor != fan and not exists ("
                + " from Favorito f where f.fan = fan and f.pio = pio)"
                + " )", Usuario.class)
                .list();
    }
}
