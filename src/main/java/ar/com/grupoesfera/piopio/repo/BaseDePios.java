package ar.com.grupoesfera.piopio.repo;

import java.util.Date;
import java.util.List;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.main.Transaction;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;

public class BaseDePios {

    @SuppressWarnings("unchecked")
    public List<Pio> obtenerTodos() {

        return App.instancia().obtenerEntityManager().createQuery("select p from Pio p").getResultList();
    }

    public Pio obtenerPor(Long id) {

        return App.instancia().obtenerEntityManager().find(Pio.class, id);
    }

    public synchronized Pio guardarCon(Usuario autor, String mensaje) {

        final Pio nuevoPio;

        if (autor != null && mensaje != null) {

            nuevoPio = Pio.nuevo().conId(proximoId()).conAutor(autor).conMensaje(mensaje).conFechaCreacion(new Date());
            Transaction.run((entities) -> {
                entities.persist(nuevoPio);
                return null;
            });
        } else {
            
            nuevoPio = null;
        }

        return nuevoPio;
    }

    private Long proximoId() {

        Long maxId = null;

        maxId = (Long) App.instancia().obtenerEntityManager().createQuery("select max(p.id) from Pio p").getSingleResult();

        if (maxId != null) {

            maxId += 1L;

        } else {

            maxId = 1L;
        }

        return maxId;
    }

    @SuppressWarnings("unchecked")
    public List<Pio> obtenerPor(Usuario autor) {

        return App.instancia().obtenerEntityManager().createQuery("select p from Pio p where autor = :autor").setParameter("autor", autor).getResultList();
    }
}
