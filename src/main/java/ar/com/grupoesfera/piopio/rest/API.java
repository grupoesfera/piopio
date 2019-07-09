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

@Path("/")
public class API {
    
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
        
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @POST
    @Path("/pios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response publicarPio(@QueryParam("mensaje") String mensaje, @QueryParam("usuario") Long id) {
        
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarios() {
        
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("/usuarios/aislados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuariosAislados() {

        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("/usuarios/{id}/seguidores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeguidores(@PathParam("id") Long id) {
        
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("/usuarios/{id}/seguidos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeguidos(@PathParam("id") Long id) {
        
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("/usuarios/{id}/pios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPiosDeUsuario(@PathParam("id") Long id) {
        
        return Response.status(Status.NOT_IMPLEMENTED).build();
    }
}
