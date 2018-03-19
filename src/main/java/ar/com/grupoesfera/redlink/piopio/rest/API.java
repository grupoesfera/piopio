package ar.com.grupoesfera.redlink.piopio.rest;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ar.com.grupoesfera.redlink.main.App;
import ar.com.grupoesfera.redlink.piopio.modelo.Pio;
import ar.com.grupoesfera.redlink.piopio.modelo.Usuario;
import ar.com.grupoesfera.redlink.piopio.repo.BaseDePios;
import ar.com.grupoesfera.redlink.piopio.repo.BaseDeUsuarios;

@Path("/")
public class API {

    private BaseDeUsuarios usuarios = App.instancia().obtenerRepoUsuarios();
    private BaseDePios pios = App.instancia().obtenerRepoPios();
    
    @GET
    @Path("/hola")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hola() {
        
        return Response.ok("Hola, mundo!").build();
    }

    @GET
    @Path("/pio/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPio(@PathParam("id") Long id) {
        
        return Response.ok(pios.obtenerPor(id)).build();
    }

    @POST
    @Path("/pio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response publicarPio(@QueryParam("mensaje") String mensaje, @QueryParam("usuario") Long id) {
        
        Response respuesta = Response.status(Status.BAD_REQUEST).build();
        Pio nuevoPio = null;
        
        Usuario autor = usuarios.obtenerPor(id);
        
        if (autor != null) {
            
            nuevoPio = pios.guardarCon(autor, mensaje);
            respuesta = Response.ok(nuevoPio).build();
        }

        return respuesta;
    }

    @GET
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarios() {
        
        return Response.ok(usuarios.obtenerTodos()).build();
    }

    @GET
    @Path("/usuarios/aislados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuariosAislados() {

        return Response.ok(usuarios.obtenerAislados()).build();
    }

    @GET
    @Path("/usuario/{id}/seguidores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeguidores(@PathParam("id") Long id) {
        
        return Response.ok(usuarios.obtenerSeguidoresDe(Usuario.nuevo().conId(id))).build();
    }

    @GET
    @Path("/usuario/{id}/seguidos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeguidos(@PathParam("id") Long id) {
        
        List<Usuario> usuarios = new LinkedList<>();

        return Response.ok(usuarios).build();
    }

    @GET
    @Path("/usuario/{id}/pios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPios(@PathParam("id") Long id) {
        
        List<Pio> pios = new LinkedList<>();

        return Response.ok(pios).build();
    }

    @GET
    @Path("/usuario/{id}/pios/favoritos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPiosOrdenadosPorFavoritos(@PathParam("id") Long id) {
        
        List<Pio> pios = new LinkedList<>();

        return Response.ok(pios).build();
    }
    
    @GET
    @Path("/pio/{id}/cant-y-fav")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCantidadyQuienesFavDePio(@PathParam("id") Long id) {
        
        return Response.ok().build();
    }
}
