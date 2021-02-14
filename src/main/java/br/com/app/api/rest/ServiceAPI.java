package br.com.app.api.rest;

import br.com.app.api.model.SamplePayload;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/service")
@Produces(APPLICATION_JSON)
public interface ServiceAPI {

    @POST
    @Path("/send")
    @Consumes(APPLICATION_JSON)
    public Response sentPayload(SamplePayload payload);

    @GET
    @Path("/{name}")
    public Response getMessage(@PathParam("name") String name);
}
