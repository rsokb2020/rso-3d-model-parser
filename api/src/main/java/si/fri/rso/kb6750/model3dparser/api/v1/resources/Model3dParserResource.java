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
import java.util.List;
import java.util.logging.Logger;

import com.mokiat.data.front.parser.IOBJParser;
import com.mokiat.data.front.parser.OBJParser;
import si.fri.rso.kb6750.model3dparser.lib.Model3dBinaryData;
import si.fri.rso.kb6750.model3dparser.lib.Model3dMetadata;
import si.fri.rso.kb6750.model3dparser.services.beans.Model3dParserBean;

@ApplicationScoped
@Path("/parser")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class Model3dParserResource {
    private Logger log = Logger.getLogger(Model3dParserResource.class.getName());

    @Inject
    private Model3dParserBean model3dParserBean;

    @Context
    protected UriInfo uriInfo;

    @POST
    public Response parseModel3dMetadata(Model3dBinaryData model3dBinaryData) {
        Model3dMetadata model3dMetadata;
        if (model3dBinaryData.getBinaryArrayString() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            model3dMetadata = model3dParserBean.processBinaryData(model3dBinaryData);
        }
        System.out.println("Recieved: " + model3dBinaryData.getBinaryArrayString());
        return Response.status(Response.Status.OK).entity(model3dMetadata).build();
    }
    /*
    @GET
    public Response getModel3dMetadata() {

        List<Model3dMetadata> model3dMetadata = model3dMetadataBean.getModel3dMetadataFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(model3dMetadata).build();
    }

    @GET
    @Path("/{model3dMetadataId}")
    public Response getModel3dMetadata(@PathParam("model3dMetadataId") Integer model3dMetadataId) {

        Model3dMetadata model3dMetadata = model3dMetadataBean.getModel3dMetadata(model3dMetadataId);

        if (model3dMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(model3dMetadata).build();
    }
    */

    /*
    @PUT
    @Path("{model3dMetadataId}")
    public Response putModel3dMetadata(@PathParam("model3dMetadataId") Integer model3dMetadataId,
                                       Model3dMetadata model3dMetadata) {

        model3dMetadata = model3dMetadataBean.putModel3dMetadata(model3dMetadataId, model3dMetadata);

        if (model3dMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @DELETE
    @Path("{model3dMetadataId}")
    public Response deleteModel3dMetadata(@PathParam("model3dMetadataId") Integer model3dMetadataId) {

        boolean deleted = model3dMetadataBean.deleteModel3dMetadata(model3dMetadataId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    */
}
