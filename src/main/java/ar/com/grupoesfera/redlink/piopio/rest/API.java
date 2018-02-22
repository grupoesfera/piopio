package ar.com.grupoesfera.redlink.piopio.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ar.com.grupoesfera.redlink.piopio.modelo.Pio;

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
}
