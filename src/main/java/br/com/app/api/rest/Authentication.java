package br.com.app.api.rest;

import br.com.app.api.model.auth.Credentials;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/auth")
public interface Authentication {

    @Path("/connect/token")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials);
}
