package br.com.app.filter;

import br.com.app.annotations.Secured;
import br.com.app.api.model.auth.JWT;
import br.com.app.security.TokenValidation;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    static final Logger LOG = Logger.getLogger(AuthenticationFilter.class.getName());
    private static final String SCOPE = "scope";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) {

        LOG.info("authentication filter");

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (!isTokenBasedAuthentication(authorizationHeader)) {
            LOG.info("Not a token based authentication! Aborting request...");
            abortWithUnauthorized(requestContext);
            return;
        }

        try {
            validateToken(authorizationHeader);
        }
        catch (Exception e) {
            e.printStackTrace();
            abortWithUnauthorized(requestContext);
        }
    }

    private void validateToken(String authorizationHeader) throws Exception {

        LOG.info("Validating token...");

        String accessToken = authorizationHeader.substring(7);
        JWT jwt = new JWT(accessToken);

        if (!TokenValidation.isValid(jwt))
            throw new Exception("Invalid JWT");

    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        requestContext.abortWith(
                    Response.status(HttpStatus.SC_UNAUTHORIZED)
                            .header(HttpHeaders.WWW_AUTHENTICATE,
                                                    AUTHENTICATION_SCHEME + " scope = " + SCOPE)
                            .build());
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        if (authorizationHeader == null)
            return false;

        return authorizationHeader.startsWith(AUTHENTICATION_SCHEME + " ");
    }
}
