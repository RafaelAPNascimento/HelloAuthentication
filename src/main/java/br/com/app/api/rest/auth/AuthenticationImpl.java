package br.com.app.api.rest.auth;

import br.com.app.Database;
import br.com.app.api.model.auth.Credentials;
import br.com.app.api.model.auth.JWT;
import br.com.app.api.rest.Authentication;
import br.com.app.security.TokenFactory;

import javax.ws.rs.core.Response;

public class AuthenticationImpl implements Authentication {

    @Override
    public Response authenticateUser(Credentials credentials) {

        try {
            proceedAuthentication(credentials);
            JWT token = TokenFactory.issueToken(credentials);

            return Response.ok(token).build();
        }
        catch (IllegalArgumentException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private void proceedAuthentication(Credentials credentials) {

        Credentials cred = Database.get(credentials.getUsername());
        if (cred == null)
            throw new IllegalArgumentException("Invalid password or username");
    }
}
