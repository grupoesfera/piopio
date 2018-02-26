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

import ar.com.grupoesfera.redlink.piopio.modelo.Pio;
import ar.com.grupoesfera.redlink.piopio.modelo.Usuario;

@Path("/")
public class API {

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
        
        Pio pio = new Pio();
        pio.setId(id);
        pio.setMensaje("Este es un pio autogenerado que no existe en la DB");
        
        return Response.ok(pio).build();
    }

    @POST
    @Path("/pio")
    @Produces(MediaType.APPLICATION_JSON)
    public Response publicarPio(@QueryParam("mensaje") String mensaje, @QueryParam("usuario") Long id) {
        
        Pio pio = new Pio();
        
        return Response.ok(pio).build();
    }

    @GET
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarios() {
        
        List<Usuario> usuarios = new LinkedList<>();

        return Response.ok(usuarios).build();
    }

    @GET
    @Path("/usuarios/aislados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuariosAislados() {
        
        List<Usuario> usuarios = new LinkedList<>();

        return Response.ok(usuarios).build();
    }

    @GET
    @Path("/usuario/{id}/seguidores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeguidores(@PathParam("id") Long id) {
        
        List<Usuario> usuarios = new LinkedList<>();

        return Response.ok(usuarios).build();
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
