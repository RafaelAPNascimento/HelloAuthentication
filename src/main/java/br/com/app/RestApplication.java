package br.com.app;

import br.com.app.api.rest.auth.AuthenticationImpl;
import br.com.app.api.rest.resource.ServiceApiImpl;
import br.com.app.filter.AuthenticationFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class RestApplication extends Application {

    public Set<Class<?>> getClasses() {

        Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(ServiceApiImpl.class);
        resources.add(AuthenticationImpl.class);
        resources.add(AuthenticationFilter.class);

        return resources;
    }
}
