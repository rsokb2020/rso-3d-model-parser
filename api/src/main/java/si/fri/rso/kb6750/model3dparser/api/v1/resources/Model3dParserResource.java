package si.fri.rso.kb6750.model3dparser.api.v1.resources;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import si.fri.rso.kb6750.model3dparser.lib.Model3dBinaryData;
import si.fri.rso.kb6750.model3dparser.lib.Model3dMetadata;
import si.fri.rso.kb6750.model3dparser.services.beans.Model3dParserBean;

@ApplicationScoped
@Path("/parse")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class Model3dParserResource {
    private Logger log = Logger.getLogger(Model3dParserResource.class.getName());

    @Inject
    private Model3dParserBean model3dParserBean;

    @Context
    protected UriInfo uriInfo;

    @POST
    public Response parseModel3dMetadata(Model3dBinaryData model3dBinaryData) throws IOException {
        Model3dMetadata model3dMetadata;

        if (model3dBinaryData.getBinaryArrayString() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            model3dMetadata = model3dParserBean.processBinaryData(model3dBinaryData);
        }
        System.out.println("Recieved: " + model3dBinaryData.getBinaryArrayString().substring(0,3));
        return Response.status(Response.Status.OK).entity(model3dMetadata).build();
    }
}
