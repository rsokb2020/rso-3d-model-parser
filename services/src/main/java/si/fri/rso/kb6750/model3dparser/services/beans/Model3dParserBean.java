package si.fri.rso.kb6750.model3dparser.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mokiat.data.front.parser.IOBJParser;
import com.mokiat.data.front.parser.OBJModel;
import com.mokiat.data.front.parser.OBJParser;
import org.apache.commons.codec.binary.Base64;
import si.fri.rso.kb6750.model3dparser.lib.Model3dBinaryData;
import si.fri.rso.kb6750.model3dparser.lib.Model3dMetadata;
import si.fri.rso.kb6750.model3dparser.models.converters.Model3dMetadataConverter;
import si.fri.rso.kb6750.model3dparser.models.entities.Model3dMetadataEntity;

@RequestScoped
public class Model3dParserBean {

    private Logger log = Logger.getLogger(Model3dMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    public Model3dMetadata processBinaryData(Model3dBinaryData model3dBinaryData) throws IOException {
        // TODO Parse
        String message = model3dBinaryData.getBinaryArrayString();
        byte[] backToBytes = Base64.decodeBase64(message);
        String test = new String(backToBytes);
        System.out.println("The message decoded: " + test);

        final IOBJParser parser = new OBJParser();
        InputStream targetStream = new ByteArrayInputStream(backToBytes);
        final OBJModel model = parser.parse(targetStream);

        Model3dMetadata model3dMetadata = new Model3dMetadata();

        return model3dMetadata;
    }

    /*
    public List<Model3dMetadata> getModel3dMetadata() {

        TypedQuery<Model3dMetadataEntity> query = em.createNamedQuery(
                "Model3DMetadataEntity.getAll", Model3dMetadataEntity.class);

        List<Model3dMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(Model3dMetadataConverter::toDto).collect(Collectors.toList());

    }

    public List<Model3dMetadata> getModel3dMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Model3dMetadataEntity.class, queryParameters).stream()
                .map(Model3dMetadataConverter::toDto).collect(Collectors.toList());
    }

    public Model3dMetadata getModel3dMetadata(Integer id) {

        Model3dMetadataEntity model3dMetadataEntity = em.find(Model3dMetadataEntity.class, id);

        if (model3dMetadataEntity == null) {
            throw new NotFoundException();
        }

        Model3dMetadata model3dMetadata = Model3dMetadataConverter.toDto(model3dMetadataEntity);

        return model3dMetadata;
    }
    */
    /*
    public Model3dMetadata createModel3dMetadata(Model3dMetadata model3dMetadata) {

        Model3dMetadataEntity model3dMetadataEntity = Model3dMetadataConverter.toEntity(model3dMetadata);

        try {
            beginTx();
            em.persist(model3dMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (model3dMetadataEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return Model3dMetadataConverter.toDto(model3dMetadataEntity);
    }

    public Model3dMetadata putModel3dMetadata(Integer id, Model3dMetadata model3dMetadata) {

        Model3dMetadataEntity c = em.find(Model3dMetadataEntity.class, id);

        if (c == null) {
            return null;
        }

        Model3dMetadataEntity updatedModel3dMetadataEntity = Model3dMetadataConverter.toEntity(model3dMetadata);

        try {
            beginTx();
            updatedModel3dMetadataEntity.setId(c.getId());
            updatedModel3dMetadataEntity = em.merge(updatedModel3dMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return Model3dMetadataConverter.toDto(updatedModel3dMetadataEntity);
    }

    public boolean deleteModel3dMetadata(Integer id) {

        Model3dMetadataEntity model3dMetadata = em.find(Model3dMetadataEntity.class, id);

        if (model3dMetadata != null) {
            try {
                beginTx();
                em.remove(model3dMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
    */
}
