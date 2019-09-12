package ar.com.grupoesfera.piopio.repo;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Comentario;
import ar.com.grupoesfera.piopio.modelo.Comentario_;
import ar.com.grupoesfera.piopio.modelo.Favorito;
import ar.com.grupoesfera.piopio.modelo.Favorito_;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Pio_;
import ar.com.grupoesfera.piopio.modelo.Usuario;
import ar.com.grupoesfera.piopio.modelo.Usuario_;

public class EjerciciosCriteria {
    
    public Pio obtenerPor(Long id) {
        
        Session session = App.instancia().obtenerSesion(); 
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pio> criteria = cb.createQuery(Pio.class);
        
        Root<Pio> root = criteria.from(Pio.class);
        
        criteria.where(cb.equal(root.get(Pio_.id), id));
        
        return session.createQuery(criteria).uniqueResult();
    }
    
    public List<Pio> obtenerTodosLosPios() {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pio> criteria = cb.createQuery(Pio.class);
        
        criteria.from(Pio.class);
        
        return session.createQuery(criteria).list();
    }
    
    public List<Pio> obtenerPiosConMensaje(String mensaje) {
        
        Session session = App.instancia().obtenerSesion(); 
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pio> criteria = cb.createQuery(Pio.class);
        
        Root<Pio> root = criteria.from(Pio.class);
        
        criteria.where(cb.like(root.get(Pio_.mensaje), "%" + mensaje + "%"));
        
        return session.createQuery(criteria).list();
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
        
        Session session = App.instancia().obtenerSesion(); 
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        
        criteria.select(cb.count(criteria.from(Pio.class)));
        
        return session
                .createQuery(criteria)
                  .uniqueResult();
    }
    
    public Integer contarCaracteresDePioMasLargo() {
        
        Session session = App.instancia().obtenerSesion(); 
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Integer> criteria = cb.createQuery(Integer.class);
        
        Root<Pio> root = criteria.from(Pio.class);
        
        criteria.select(cb.max(cb.length(root.get(Pio_.mensaje))));
        
        return session.createQuery(criteria)
                .uniqueResult();
    }
    
    public Integer contarCaracteresDePioMasCorto() {
        Session session = App.instancia().obtenerSesion(); 
        
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Integer> criteria = cb.createQuery(Integer.class);
        
        Root<Pio> root = criteria.from(Pio.class);
        
        criteria.select(cb.min(cb.length(root.get(Pio_.mensaje))));
        
        return session.createQuery(criteria)
                .uniqueResult();
    }
    
    public List<Pio> obtenerPioMasLargo() {
        
        Session session = App.instancia().obtenerSesion(); 
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Pio> pioQuery = cb.createQuery(Pio.class);
        Root<Pio> pio = pioQuery.from(Pio.class);
        
        Subquery<Integer> subquery = pioQuery.subquery(Integer.class);
        Root<Pio> subPio = subquery.from(Pio.class);
        subquery.select(cb.max(cb.length(subPio.get(Pio_.mensaje))));
        
        pioQuery
            .select(pio)
            .where(cb.equal(cb.length(pio.get(Pio_.mensaje)), subquery));
        
        return session.createQuery(pioQuery).list();
    }
    
    public Double obtenerPromedioDeCaracteres() {
        
        Session session = App.instancia().obtenerSesion(); 
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Double> pioQuery = cb.createQuery(Double.class);
        Root<Pio> pio = pioQuery.from(Pio.class);
        
        pioQuery.select(cb.avg(cb.length(pio.get(Pio_.mensaje))));
        
        return session
                .createQuery(pioQuery)
                .uniqueResult();
    }
    
    public List<Pio> buscarPorAnio(int anio) {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Pio> pioQuery = cb.createQuery(Pio.class);
        Root<Pio> pio = pioQuery.from(Pio.class);
        
        pioQuery.select(pio)
                .where(cb.equal(cb.function("YEAR", Integer.class, pio.get(Pio_.fechaCreacion)), anio));
        
        return session.createQuery(pioQuery)
                .list();
    }
    
    public List<Pio> buscarEntre(Date fechaInicio, Date fechaFin) {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Pio> pioQuery = cb.createQuery(Pio.class);
        Root<Pio> pio = pioQuery.from(Pio.class);
        
        pioQuery.select(pio)
            .where(cb.between(pio.get(Pio_.fechaCreacion), fechaInicio, fechaFin));
        
        return session
                .createQuery(pioQuery)
                .list();
    }
    
    public List<String> obtenerTodosLosNombresDeUsuarios() {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<String> obtenerNombres = cb.createQuery(String.class);
        Root<Usuario> usuario = obtenerNombres.from(Usuario.class);
        
        obtenerNombres.select(usuario.get(Usuario_.nombre));
        
        return session.createQuery(obtenerNombres).list();
    }

    public List<Pio> obtenerPiosDe(Usuario usuario) {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Pio> pioQuery = cb.createQuery(Pio.class);
        Root<Pio> pio = pioQuery.from(Pio.class);
        
        pioQuery.select(pio)
                .where(cb.equal(pio.get(Pio_.autor), usuario));
        
        return session.createQuery(pioQuery)
                .list();
    }
    
    public List<Usuario> obtenerSinActividad() {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Usuario> buscarUsuariosInactivos = cb.createQuery(Usuario.class);
        Root<Usuario> usuario = buscarUsuariosInactivos.from(Usuario.class);
        
        Subquery<Pio> piosDeUnUsuario = buscarUsuariosInactivos.subquery(Pio.class);
        Root<Pio> pio = piosDeUnUsuario.from(Pio.class);
        
        piosDeUnUsuario.select(pio)
            .where(cb.equal(pio.get(Pio_.autor), usuario));
        
        buscarUsuariosInactivos.select(usuario)
            .where(cb.not(cb.exists(piosDeUnUsuario)));
        
        return session
                .createQuery(buscarUsuariosInactivos)
                .list();
    }

    public Long contarPios(Usuario usuario) {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Long> contarPios = cb.createQuery(Long.class);
        Root<Pio> pio = contarPios.from(Pio.class);
        
        contarPios.select(cb.count(pio)).where(cb.equal(pio.get(Pio_.autor), usuario));
        
        
        return session.createQuery(contarPios)
                .uniqueResult();
    }
    
    public Integer contarCuantosUsuariosSigue(Usuario usuario) {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Integer> contarSeguidos = cb.createQuery(Integer.class);
        Root<Usuario> usuarioRoot = contarSeguidos.from(Usuario.class);
        
        contarSeguidos.select(cb.size(usuarioRoot.get(Usuario_.seguidos)))
            .where(cb.equal(usuarioRoot, usuario));
        
        return session
                .createQuery(contarSeguidos)
                .uniqueResult();
    }
    
    public List<Usuario> obtenerSeguidoresDe(Usuario usuario) {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Usuario> obtenerSeguidores = cb.createQuery(Usuario.class);
        Root<Usuario> usuarioRoot = obtenerSeguidores.from(Usuario.class);
        
        obtenerSeguidores.select(usuarioRoot)
            .where(cb.isMember(usuario, usuarioRoot.get(Usuario_.seguidos)));
        
        return session.createQuery(obtenerSeguidores).list();
    }
    
    public List<Usuario> obtenerAislados() {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Usuario> obtenerAislados = cb.createQuery(Usuario.class);
        Root<Usuario> usuarioAislado = obtenerAislados.from(Usuario.class);
        
        Subquery<Usuario> usuarioEsSeguido = obtenerAislados.subquery(Usuario.class);
        Root<Usuario> usuarioSeguido = usuarioEsSeguido.from(Usuario.class);
        
        usuarioEsSeguido.select(usuarioSeguido)
            .where(cb.isMember(usuarioAislado, usuarioSeguido.get((Usuario_.seguidos))));
        
        Predicate noSigueANadie = cb.isEmpty(usuarioAislado.get(Usuario_.seguidos));
        Predicate noEsSeguidoPorNadie = cb.not(cb.exists(usuarioEsSeguido));
        
        obtenerAislados.select(usuarioAislado)
            .where(cb.and(noSigueANadie, noEsSeguidoPorNadie));
        
        
        return session.createQuery(obtenerAislados)
                .list();
    }
    
    public List<Comentario> obtenerComentariosDe(Usuario usuario) {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Comentario> obtenerComentarios = cb.createQuery(Comentario.class);
        Root<Comentario> comentario = obtenerComentarios.from(Comentario.class);
        
        obtenerComentarios
            .select(comentario)
            .where(cb.equal(comentario.get(Comentario_.autor), usuario));
        
        return session.createQuery(obtenerComentarios).list();
    }
    
    public long contarComentariosRealizadosPor(Usuario usuario) {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Long> contarComentarios = cb.createQuery(Long.class);
        Root<Comentario> comentario = contarComentarios.from(Comentario.class);
        
        contarComentarios
            .select(cb.count(comentario))
            .where(cb.equal(comentario.get(Comentario_.autor), usuario));
        
        return session.createQuery(contarComentarios).uniqueResult();
    }
    
    public List<Pio> obtenerSinComentar() {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Pio> piosSinComentarios = cb.createQuery(Pio.class);
        Root<Pio> pio = piosSinComentarios.from(Pio.class);
        
        piosSinComentarios.select(pio)
            .where(cb.isEmpty(pio.get(Pio_.comentarios)));
        
        
        return session.createQuery(piosSinComentarios)
                .list();
    }
    
    public List<Pio> obtenerPiosFavoritosDe(Usuario fan) {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Pio> piosFavoritos = cb.createQuery(Pio.class);
        Root<Favorito> favorito = piosFavoritos.from(Favorito.class);
        
        piosFavoritos
            .select(favorito.get(Favorito_.pio))
            .where(cb.equal(favorito.get(Favorito_.fan), fan));
        

        return session.createQuery(piosFavoritos).list();
    }
    
    public List<Usuario> obtenerFansDel(Pio pio) {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Usuario> fansDeUnPio = cb.createQuery(Usuario.class);
        Root<Favorito> favorito = fansDeUnPio.from(Favorito.class);
        
        fansDeUnPio
            .select(favorito.get(Favorito_.fan))
            .where(cb.equal(favorito.get(Favorito_.pio), pio));
        
        return session.createQuery(fansDeUnPio)
                .list();
    }
    
    public List<Pio> obtenerPiosSinFans() {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Pio> piosSinFans = cb.createQuery(Pio.class);
        Root<Pio> pio = piosSinFans.from(Pio.class);
        
        Subquery<Favorito> favoritosDelPio = piosSinFans.subquery(Favorito.class);
        Root<Favorito> favorito = favoritosDelPio.from(Favorito.class);
        
        favoritosDelPio.select(favorito)
            .where(cb.equal(favorito.get(Favorito_.pio), pio));
        
        piosSinFans.select(pio)
            .where(cb.not(cb.exists(favoritosDelPio)));
        
        
        return session.createQuery(piosSinFans)
                .list();
    }
    
    public Long contarFavoritosDeUnPio(Pio pio) {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Long> favoritosDeUnPio = cb.createQuery(Long.class);
        Root<Favorito> favorito = favoritosDeUnPio.from(Favorito.class);
        
        favoritosDeUnPio
            .select(cb.count(favorito.get(Favorito_.pio)))
            .where(cb.equal(favorito.get(Favorito_.pio), pio))
            .groupBy(favorito.get(Favorito_.pio));
        
        Long cantidad = session
                .createQuery(favoritosDeUnPio)
                .uniqueResult();
        
        return cantidad == null ? 0 : cantidad;
    }
    
    public List<Usuario> obtenerUsuarioFanDeTodosLosPios() {
        
        Session session = App.instancia().obtenerSesion();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        
        CriteriaQuery<Usuario> usuarioSuperFan = cb.createQuery(Usuario.class);
        Root<Usuario> usuario = usuarioSuperFan.from(Usuario.class);
        
        Subquery<Usuario> usuariosQueNoSonFanDeAlgunPio = usuarioSuperFan.subquery(Usuario.class);
        Root<Usuario> fan = usuariosQueNoSonFanDeAlgunPio.from(Usuario.class);
        Root<Pio> pio = usuariosQueNoSonFanDeAlgunPio.from(Pio.class);
        
        Subquery<Favorito> favoritosDeUnUsuario = usuariosQueNoSonFanDeAlgunPio.subquery(Favorito.class);
        Root<Favorito> favorito = favoritosDeUnUsuario.from(Favorito.class);
       
        Predicate esFavoritoDeUnPio = cb.and(cb.equal(favorito.get(Favorito_.fan), fan),
                                             cb.equal(favorito.get(Favorito_.pio), pio));
        favoritosDeUnUsuario.select(favorito)
            .where(esFavoritoDeUnPio);
        
        Predicate noEsMismoFan = cb.notEqual(pio.get(Pio_.autor), fan);
        Predicate noEsFanDeAlgunPio = cb.not(cb.exists(favoritosDeUnUsuario));
        
        usuariosQueNoSonFanDeAlgunPio
            .select(fan)
            .where(cb.and(noEsMismoFan, noEsFanDeAlgunPio));
        
        usuarioSuperFan
            .select(usuario)
            .where(cb.not(cb.in(usuario).value(usuariosQueNoSonFanDeAlgunPio)));
        
        
        return session.createQuery(usuarioSuperFan).list();
    }

}
