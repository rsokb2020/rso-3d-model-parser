package si.fri.rso.kb6750.model3dparser.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
// import javax.persistence.EntityManager;
// import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
// import com.kumuluz.ee.rest.utils.JPAUtils;
import com.mokiat.data.front.parser.IOBJParser;
import com.mokiat.data.front.parser.OBJModel;
import com.mokiat.data.front.parser.OBJParser;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import si.fri.rso.kb6750.model3dparser.config.RestProperties;
import si.fri.rso.kb6750.model3dparser.lib.Model3dBinaryData;
import si.fri.rso.kb6750.model3dparser.lib.Model3dMetadata;
// import si.fri.rso.kb6750.model3dparser.models.converters.Model3dMetadataConverter;
// import si.fri.rso.kb6750.model3dparser.models.entities.Model3dMetadataEntity;


@RequestScoped
public class Model3dParserBean {

    private Logger log = Logger.getLogger(Model3dParserBean.class.getName());

    @Inject
    private RestProperties restProperties;

    // @Inject
    // private EntityManager em;

    public Model3dMetadata processBinaryData(Model3dBinaryData model3dBinaryData) throws IOException {
        // String of the binary array representing the .obj model
        String message = model3dBinaryData.getBinaryArrayString();
        // Convert the string into a byte array
        byte[] backToBytes = Base64.decodeBase64(message);

        String test = new String(backToBytes);
        System.out.println("The message decoded: " + test.substring(0, 3));

        final IOBJParser parser = new OBJParser();
        InputStream targetStream = new ByteArrayInputStream(backToBytes);
        final OBJModel model = parser.parse(targetStream);

        System.out.println(MessageFormat.format(
                "OBJ model has {0} vertices, {1} normals, {2} texture coordinates, and {3} objects.",
                model.getVertices().size(),
                model.getNormals().size(),
                model.getTexCoords().size(),
                model.getObjects().size()));

        Model3dMetadata model3dMetadata = new Model3dMetadata();
        model3dMetadata.setBinaryArray(message);
        model3dMetadata.setTitle(model3dBinaryData.getTitle());
        model3dMetadata.setDescription(model3dBinaryData.getDescription());
        model3dMetadata.setNumberOfFaces((long)(model.getNormals().size()));
        model3dMetadata.setNumberOfVertices((long)(model.getVertices().size()));
        model3dMetadata.setAssetBundleBinaryArray(model3dBinaryData.getAssetBundleBinaryArray());
        boolean sendingSucceded = sendDataToCatalog(model3dMetadata);

        return model3dMetadata;
    }

    public boolean sendDataToCatalog(Model3dMetadata model3dMetadata) throws IOException {
        // "{\"binary\":\"jiberish\",\"created\":\"2006-01-01T14:36:38Z\",\"description\":\"22This is the first model that I created within my app.\",\"faces\":500,\"modelId\":1,\"title\":\"Our fist 3d model\",\"uri\":\"free3d.com\/3d-models\/obj-library\",\"vertices\":1200}"
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            String url = restProperties.getCatalogServiceIp();
            URL obj = new URL(url);
            // "http://172.17.0.1:8080/v1/models3d"
            // HttpPost request = new HttpPost(uri);
            JSONObject json = new JSONObject();

            json.put("title", model3dMetadata.getTitle());
            json.put("description", model3dMetadata.getDescription());
            // json.put("uri", "This value should get removed soon.");
            json.put("binary", model3dMetadata.getBinaryArray());
            json.put("vertices", model3dMetadata.getNumberOfVertices());
            json.put("normals", model3dMetadata.getNumberOfFaces());
            json.put("assetBundleBinaryArray", model3dMetadata.getAssetBundleBinaryArray());

            HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setRequestProperty("Content-Type", "application/json");
            postConnection.setDoOutput(true);

            OutputStream os = postConnection.getOutputStream();
            os.write(json.toString().getBytes());
            os.flush();
            os.close();
          int responseCode = postConnection.getResponseCode();

            System.out.println("POST Response Code :  " + responseCode);
            System.out.println("POST Response Message : " + postConnection.getResponseMessage());

            if (responseCode == HttpURLConnection.HTTP_CREATED || responseCode == HttpURLConnection.HTTP_OK ) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                System.out.println(response.toString());
            } else {
                System.out.println("POST NOT WORKED");
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
}
