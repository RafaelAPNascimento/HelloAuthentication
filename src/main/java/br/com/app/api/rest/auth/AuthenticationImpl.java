package br.com.app.api.rest.auth;

import br.com.app.Database;
import br.com.app.api.model.auth.Credentials;
import br.com.app.api.model.auth.JWT;
import br.com.app.api.rest.Authentication;
import br.com.app.security.TokenFactory;

import javax.ws.rs.core.Response;
import java.util.logging.Logger;

public class AuthenticationImpl implements Authentication {

    static final Logger LOG = Logger.getLogger(AuthenticationImpl.class.getName());

    @Override
    public Response authenticateUser(Credentials credentials) {

        LOG.info("Authentication service: "+credentials);

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
