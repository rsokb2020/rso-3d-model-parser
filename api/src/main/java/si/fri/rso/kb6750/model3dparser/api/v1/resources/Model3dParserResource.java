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

import com.kumuluz.ee.logs.cdi.Log;
import org.json.JSONObject;
import si.fri.rso.kb6750.model3dparser.lib.Model3dBinaryData;
import si.fri.rso.kb6750.model3dparser.lib.Model3dMetadata;
import si.fri.rso.kb6750.model3dparser.services.beans.Model3dParserBean;

@Log
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

    @Log
    @GET
    public Response getModel3dMetadata() {
        System.out.println("Recieved GET request on /parser.");
        return Response.status(Response.Status.OK).entity("Success").build();
    }
    /*
    @GET
    @Path("/info")
    public Response getModel3dMetadata(@PathParam("model3dMetadataId") Integer model3dMetadataId) {

        JSONObject json = new JSONObject();

        json.put("clani", "['kb6750']");
        json.put("opis_projekta", "Aplikacija za nalaganje,shranjevanje in prikazovanje 3d modelov.");
        json.put("mikrostoritve", "['40.76.130.74:8080/v1/models3d','40.88.212.66:8080/v1/parser']");
        // json.put("uri", "This value should get removed soon.");
        json.put("github", "['https://github.com/rsokb2020/rso-3d-model-parser,'https://github.com/rsokb2020/rso-3d-model-catalog']");
        json.put("travis", "['https://github.com/rsokb2020/rso-3d-model-parser/actions','https://github.com/rsokb2020/rso-3d-model-catalog/actions']");
        json.put("dockerhub", "['https://hub.docker.com/repository/docker/klemiba/model-3d-parser'],['https://hub.docker.com/repository/docker/klemiba/model-3d-catalog']");

        return Response.status(Response.Status.OK).entity(json.toString()).build();
    }*/
    @Log
    @POST
    public Response parseModel3dMetadata(Model3dBinaryData model3dBinaryData) throws IOException {
        Model3dMetadata model3dMetadata;

        if (model3dBinaryData.getBinaryArrayString() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            model3dMetadata = model3dParserBean.processAndForwardBinaryData(model3dBinaryData);
        }
        System.out.println("Recieved: " + model3dBinaryData.getBinaryArrayString().substring(0,3));
        return Response.status(Response.Status.OK).entity(model3dMetadata).build();
    }

    @Log
    @POST
    @Path("/parseExisting")
    public Response parseExistingModel3dMetadata(Model3dBinaryData model3dBinaryData) throws IOException {
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
