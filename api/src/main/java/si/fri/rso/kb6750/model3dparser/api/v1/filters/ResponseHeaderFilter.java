package si.fri.rso.kb6750.model3dparser.api.v1.filters;

import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.kb6750.model3dparser.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Log
@Provider
@ApplicationScoped
public class ResponseHeaderFilter implements ContainerResponseFilter {

    @Inject
    private RestProperties restProperties;

    @Context
    HttpHeaders httpHeaders;

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        System.out.println("Request header in Response filter: " + containerRequestContext.getHeaderString("request-chain"));
    }
}