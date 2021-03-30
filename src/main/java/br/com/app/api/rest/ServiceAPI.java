package br.com.app.api.rest;

import br.com.app.annotations.Secured;
import br.com.app.api.model.SamplePayload;
import br.com.app.api.model.ValidNames;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/service")
@Produces(APPLICATION_JSON)
public interface ServiceAPI {

    @POST
    @Secured
    @Path("/send")
    @Consumes(APPLICATION_JSON)
    public Response sentPayload(@NotNull SamplePayload payload);

    @GET
    @Secured
    @Path("/{name}")
    public Response getMessage(@PathParam("name") ValidNames name);
}
