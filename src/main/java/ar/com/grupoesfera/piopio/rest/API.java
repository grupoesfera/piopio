package ar.com.grupoesfera.piopio.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ar.com.grupoesfera.main.App;
import ar.com.grupoesfera.piopio.modelo.Pio;
import ar.com.grupoesfera.piopio.modelo.Usuario;
import ar.com.grupoesfera.piopio.repo.BaseDePios;
import ar.com.grupoesfera.piopio.repo.BaseDeUsuarios;

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
    @Path("/pios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPios() {
        
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("/pios/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPio(@PathParam("id") Long id) {
        
        Pio pio = pios.obtenerPor(id);
        
        return pio != null ? Response.ok(pio).build() : Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @Path("/pios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response publicarPio(@QueryParam("mensaje") String mensaje, @QueryParam("usuario") Long id) {
        
        Response respuesta = Response.status(Status.BAD_REQUEST).build();
        Pio nuevoPio = null;
        
        Usuario autor = usuarios.obtenerPor(id);
        
        if (autor != null) {
            
            nuevoPio = pios.guardarCon(autor, mensaje);
            
            respuesta = nuevoPio != null ? Response.status(Status.CREATED).entity(nuevoPio).build() : Response.fromResponse(respuesta).entity("Pio no creado. El pio no tiene mensaje.").build();
        
        } else {
            
            respuesta = Response.fromResponse(respuesta)
                .entity("Pio no creado. El usuario '" + id + "' no existe.").build();
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
        
        return Response.ok(usuarios.obtenerPor(id).getSeguidos()).build();
    }

    @GET
    @Path("/usuario/{id}/pios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPiosDeUsuario(@PathParam("id") Long id) {
        
        return Response.ok(pios.obtenerPor(Usuario.nuevo().conId(id))).build();
    }
}
