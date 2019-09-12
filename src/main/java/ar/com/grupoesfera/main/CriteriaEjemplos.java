package ar.com.grupoesfera.main;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import ar.com.grupoesfera.piopio.modelo.Comentario;
import ar.com.grupoesfera.piopio.modelo.Comentario_;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Pio_;
import ar.com.grupoesfera.piopio.modelo.Usuario;
import ar.com.grupoesfera.piopio.modelo.Usuario_;
import ar.com.grupoesfera.piopio.modelo.dto.MensajePorPio;

public class CriteriaEjemplos {
    
    public static void main(String[] args) {
        
        //CARGA DE DATOS
        Fixture.initData();
        
        Session sesion = App.instancia().obtenerSesion();
        
        // PRIMERO OBTENER UNA INSTANCIA DE CriteriaBuilder
        CriteriaBuilder cb = sesion.getCriteriaBuilder();
        
        // OBTENER UNA INSTANCIA DE CriteriaQuery
        // El tipo indica cual es el resultado esperado. En este caso Pio  
        CriteriaQuery<Pio> criteria = cb.createQuery(Pio.class);
        
        // INDICAR EL FROM
        Root<Pio> root = criteria.from(Pio.class);
        criteria.select(root);
        
        List<Pio> pios = sesion.createQuery(criteria).list();
        
        System.out.println("Ej1) Obtener una instancia: " + pios);
        
        //OBTENER UN TIPO DE DATO QUE NO ES ENTITY
        cb = sesion.getCriteriaBuilder();
        CriteriaQuery<String> criteriaMensajes = cb.createQuery(String.class);
        
        // INDICAR EL FROM
        Root<Pio> rootPio = criteriaMensajes.from(Pio.class);
        
        // SE INDICA EN EL SELECT QUE ATRIBUTO QUEREMOS DEL PIO
        criteriaMensajes.select(root.get(Pio_.mensaje));
        
        List<String> mensajes = sesion.createQuery(criteriaMensajes).list();
        
        System.out.println("Ej2) Obtener un atributo: " + mensajes);
        
        //SELECCIONAR MULTIPLES VALORES
        // 1) SELECCIONAR UN ARRAY
        CriteriaQuery<Object[]> criteriaMultiplesValores = cb.createQuery(Object[].class);
        
        // INDICAR EL FROM
        root = criteriaMultiplesValores.from(Pio.class);
        Path<Long> idPath = root.get(Pio_.id);
        Path<String> mensajePath = root.get(Pio_.mensaje);
        
        criteriaMultiplesValores.select(cb.array(idPath, mensajePath));
        
        List<Object[]> idsYMensajes = sesion.createQuery(criteriaMultiplesValores).list();
        
        System.out.println("Ej3) Obtener multiples atributos: ");
        idsYMensajes.stream().forEach(a -> Stream.of(a).forEach(o -> System.out.println(o)));
        
        //2) Envolver los resultados en un objeto
        CriteriaQuery<MensajePorPio> criteriaMensajesPorPio = cb.createQuery(MensajePorPio.class);
        root = criteriaMensajesPorPio.from(Pio.class);
        
        criteriaMensajesPorPio.select(cb.construct(MensajePorPio.class, idPath, mensajePath));
        
        List<MensajePorPio> resultados = sesion.createQuery(criteriaMensajesPorPio).list();
        System.out.println("Ej4) Obtener multiples atributos en una clase: ");
        System.out.println(resultados);
        
        //3) Seleccionar tuplas
        CriteriaQuery<Tuple> criteriaTuplas = cb.createQuery(Tuple.class);
        root = criteriaTuplas.from(Pio.class);
        
        criteriaTuplas.multiselect(idPath, mensajePath);
        
        List<Tuple> tuplas = sesion.createQuery(criteriaTuplas).list();

        System.out.println("Ej5) Obtener tuplas: ");
        Consumer<Tuple> imprimirTupla = (t) -> System.out.println("POR PATH:   " + t.get(idPath) + "-" + t.get(mensajePath));
        Consumer<Tuple> imprimirTuplaPorIndice = (t) -> System.out.println("POR INDICE: " + t.get(0) + "-" + t.get(1));
        
        tuplas.stream().forEach(imprimirTupla.andThen(imprimirTuplaPorIndice));
        
        //FILTRAR WHERE
        CriteriaQuery<String> criteriaMensajesEmpezadosEnHola = cb.createQuery(String.class);
        root = criteriaMensajesEmpezadosEnHola.from(Pio.class);
        
        criteriaMensajesEmpezadosEnHola.select(root.get(Pio_.mensaje));
        Predicate filtroMensaje = 
                cb.like(root.get(Pio_.mensaje), "%Hola%");
        
        // SE INDICAN EN EL WHERE TODOS LOS PREDICADOS
        criteriaMensajesEmpezadosEnHola.where(filtroMensaje);
        
        List<String> mensajesEmpezadosEnHola = sesion.createQuery(criteriaMensajesEmpezadosEnHola).list();
        System.out.println("Ej6) Filtrar pios empezados en 'Hola': ");
        System.out.println(mensajesEmpezadosEnHola);
        
        // JOINS
        
        CriteriaQuery<Pio> criteriaJoins = cb.createQuery(Pio.class);
        root = criteriaJoins.from(Pio.class);
        
        // Pio.comentarios es @OneToMany
        Join<Pio, Comentario> comentariosJoin = root.join(Pio_.comentarios);
        //comentario.autor es @ManyToOne
        Join<Comentario, Usuario> autorJoin = comentariosJoin.join(Comentario_.autor);
        
        // Queremos filtrar el nombre del autor
        Predicate nombreDeAutor = cb.equal(autorJoin.get(Usuario_.nombre), "Brenda");
        
        criteriaJoins.where(nombreDeAutor);
        
        List<Pio> piosComentadosPorBrenda = sesion.createQuery(criteriaJoins).list();
        System.out.println("Ej7) Filtrar pios comentados por Brenda con joins");
        System.out.println(piosComentadosPorBrenda);
        
        sesion.close();
        System.exit(0);
        
    }

}
