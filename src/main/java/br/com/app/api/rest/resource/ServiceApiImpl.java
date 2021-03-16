package br.com.app.api.rest.resource;

import br.com.app.api.model.SamplePayload;
import br.com.app.api.rest.ServiceAPI;

import javax.ws.rs.core.Response;
import java.util.logging.Logger;

public class ServiceApiImpl implements ServiceAPI {

    static final Logger LOG = Logger.getLogger(ServiceApiImpl.class.getName());

    @Override
    public Response sentPayload(SamplePayload payload) {
        LOG.info("========== sentPayload: "+payload);
        return Response.ok().build();
    }

    @Override
    public Response getMessage(String name) {
        LOG.info("========== getMessage: "+name);
        return Response.ok(name).build();
    }
}
