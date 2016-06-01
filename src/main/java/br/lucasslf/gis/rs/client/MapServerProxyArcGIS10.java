/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.lucasslf.gis.rs.client;

import br.lucasslf.gis.swingviewer.map.BaseTiledMapService;
import br.lucasslf.gis.swingviewer.map.MapService;
import br.lucasslf.gis.swingviewer.map.ImageService;
import br.lucasslf.gis.swingviewer.model.BaseMapConfiguration;
import br.lucasslf.gis.swingviewer.model.MapConfiguration;
import br.lucasslf.gis.swingviewer.model.ImageConfiguration;
import br.lucasslf.gis.swingviewer.model.LayerConfiguration;
import br.lucasslf.gis.swingviewer.model.LayerLegend;
import br.lucasslf.gis.swingviewer.model.LegendResource;
import br.lucasslf.gis.swingviewer.model.QueryReturn;
import br.lucasslf.gis.swingviewer.utilities.ChannelTools;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.ws.rs.core.MediaType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author cy81
 */
public class MapServerProxyArcGIS10 implements MapServerProxy {

    private Client client;
    private Log log = LogFactory.getLog(MapServerProxyArcGIS10.class);

    static {
        ImageIO.setCacheDirectory(new File("D:/appfiles/temp"));
    }

    private Client getClient() {
        if (client == null) {
            client = new Client();
        }
        return client;
    }

    @Override
    public BaseMapConfiguration getBaseMapConfiguration(BaseTiledMapService baseMapService) {
        if (baseMapService == null || baseMapService.getUrl() == null) {
            throw new IllegalArgumentException("A URL do servidor não pode ser null");
        }

        String serverUrl = baseMapService.getUrl().toString();
        String requestUrl = serverUrl.concat("?f=json");
        Client c = getClient();
        WebResource rs = c.resource(requestUrl);
        ClientResponse response = rs.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Código: " + response.getStatus() + " " + requestUrl);

        }
        String jsonResponse = response.getEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        BaseMapConfiguration config;
        try {
            config = mapper.readValue(jsonResponse, BaseMapConfiguration.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return config;

    }

    @Override
    public File downloadTileImageFile(URL url, int level, int row, int column, String fileName) {
        if (url == null) {
            throw new IllegalArgumentException("A URL do servidor não pode ser null");
        }
        File file = new File(fileName);
        if (!file.exists()) {
            String serverUrl = url.toString();
            String requestUrl = serverUrl.concat("/tile/")
                    .concat(String.valueOf(level))
                    .concat("/")
                    .concat(String.valueOf(row))
                    .concat("/")
                    .concat(String.valueOf(column));
            Client c = getClient();
            WebResource rs = c.resource(requestUrl);
            ClientResponse response = rs.accept(new MediaType("image", "jpg")).get(ClientResponse.class);
            if (response.getStatus() == 404) {
                response.close();
                return null;
            } else if (response.getStatus() != 200) {
                throw new RuntimeException("Response com erro: " + response.getStatus());
            }

            try {

                file.deleteOnExit();

                final InputStream input = response.getEntityInputStream();
                final OutputStream output = new FileOutputStream(file);
                final ReadableByteChannel inputChannel = Channels.newChannel(input);
                final WritableByteChannel outputChannel = Channels.newChannel(output);
                ChannelTools.fastChannelCopy(inputChannel, outputChannel);
                inputChannel.close();
                outputChannel.close();

            } catch (javax.imageio.IIOException ex) {
                log.warn("Ignorando exceção");
                log.error(ex);
            } catch (IOException ex) {
                log.error(ex);
                throw new RuntimeException(ex);
            }
            response.close();
        }

        return file;
    }

    @Override
    public Image exportImage(URL url, Double xMin, Double yMin, Double xMax, Double yMax, Dimension size, String coordinateSystem) {
        if (url == null) {
            throw new IllegalArgumentException("A URL do servidor não pode ser null");
        }
        String serverUrl = url.toString();
        String requestUrl = serverUrl.concat("/exportImage?bbox=")
                .concat(xMin.toString())
                .concat(",")
                .concat(yMin.toString())
                .concat(",")
                .concat(xMax.toString())
                .concat(",")
                .concat(yMax.toString())
                .concat("&size=")
                .concat(Integer.toString((int) size.getWidth()))
                .concat(",")
                .concat(Integer.toString((int) size.getHeight()))
                .concat("&f=image&bboxSR=")
                .concat(coordinateSystem)
                .concat("&imageSR=")
                .concat(coordinateSystem);

        Client c = getClient();
        WebResource rs = c.resource(requestUrl);
        ClientResponse response = rs.accept(new MediaType("image", "png")).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Response com erro: " + response.getStatus());
        }
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(response.getEntityInputStream());

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        response.close();
        return bi;
    }

    private String buildVisibleLayersParameter(List<Integer> visibleLayers) {
        StringBuilder sb = new StringBuilder("&layers=show:-1");
        for (int layerId : visibleLayers) {
            sb.append(",").append(layerId);
        }
        return sb.toString();
    }

    @Override
    public QueryReturn performQuery(URL url, String coordinateSystem, String text, int layerId) {
        if (url == null) {
            throw new IllegalArgumentException("A URL do servidor não pode ser null");
        }
        try {
            text = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }

        String serverUrl = url.toString();
        String requestUrl = serverUrl.concat("/")
                .concat(String.valueOf(layerId))
                .concat("/query?where=")
                .concat(text)
                .concat("&f=json")
                .concat("&outSR=")
                .concat(coordinateSystem);

        Client c = getClient();
        WebResource rs = c.resource(requestUrl);
        ClientResponse response = rs.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Código: " + response.getStatus() + " " + requestUrl);

        }
        String jsonResponse = response.getEntity(String.class);
        QueryReturn q = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.getFactory().enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
            q = mapper.readValue(jsonResponse, QueryReturn.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return q;
    }

    @Override
    public Image export(URL url, Double xMin, Double yMin, Double xMax, Double yMax, Dimension size, String coordinateSystem, List<Integer> visibleLayers) {
        if (url == null) {
            throw new IllegalArgumentException("A URL do servidor não pode ser null");
        }
        String serverUrl = url.toString();
        String requestUrl = serverUrl.concat("/export?bbox=")
                .concat(xMin.toString())
                .concat(",")
                .concat(yMin.toString())
                .concat(",")
                .concat(xMax.toString())
                .concat(",")
                .concat(yMax.toString())
                .concat("&size=")
                .concat(Integer.toString((int) size.getWidth()))
                .concat(",")
                .concat(Integer.toString((int) size.getHeight()))
                .concat("&format=png8&transparent=true&dpi=96")
                .concat("&_ts=")
                .concat(String.valueOf(Calendar.getInstance().getTimeInMillis()))
                .concat("&f=image&bboxSR=")
                .concat(coordinateSystem)
                .concat("&imageSR=")
                .concat(coordinateSystem)
                .concat(buildVisibleLayersParameter(visibleLayers));
        Client c = getClient();
        WebResource rs = c.resource(requestUrl);
        ClientResponse response = rs.accept(new MediaType("image", "png")).get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Código: " + response.getStatus() + " " + requestUrl);

        }
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(response.getEntityInputStream());

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        response.close();
        return bi;

    }

    @Override
    public LayerConfiguration getLayerConfiguration(MapService dynamicMapService, int layerId) {
        if (dynamicMapService == null || dynamicMapService.getUrl() == null) {
            throw new IllegalArgumentException("A URL do servidor não pode ser null");
        }

        String serverUrl = dynamicMapService.getUrl().toString();
        String requestUrl = serverUrl.concat("/").concat(String.valueOf(layerId)).concat("?f=json");
        Client c = getClient();
        WebResource rs = c.resource(requestUrl);
        ClientResponse response = rs.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Código: " + response.getStatus() + " " + requestUrl);

        }
        String jsonResponse = response.getEntity(String.class);

        ObjectMapper mapper = new ObjectMapper();
        LayerConfiguration config;
        try {
            mapper.getFactory().enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
            config = mapper.readValue(jsonResponse, LayerConfiguration.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return config;
    }

    @Override
    public MapConfiguration getMapConfiguration(MapService dynamicMapService) {
        if (dynamicMapService == null || dynamicMapService.getUrl() == null) {
            throw new IllegalArgumentException("A URL do servidor não pode ser null");
        }

        String serverUrl = dynamicMapService.getUrl().toString();
        String requestUrl = serverUrl.concat("?f=json");
        Client c = getClient();
        WebResource rs = c.resource(requestUrl);
        ClientResponse response = rs.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Código: " + response.getStatus() + " " + requestUrl);

        }
        String jsonResponse = response.getEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        MapConfiguration config;
        try {
            mapper.getFactory().enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
            config = mapper.readValue(jsonResponse, MapConfiguration.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return config;
    }

    @Override
    public ImageConfiguration getImageConfiguration(ImageService imageService) {
        if (imageService == null || imageService.getUrl() == null) {
            throw new IllegalArgumentException("A URL do servidor não pode ser null");
        }

        String serverUrl = imageService.getUrl().toString();
        String requestUrl = serverUrl.concat("?f=json");
        Client c = getClient();
        WebResource rs = c.resource(requestUrl);
        ClientResponse response = rs.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Código: " + response.getStatus() + " " + requestUrl);

        }
        String jsonResponse = response.getEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        ImageConfiguration config;
        try {
            mapper.getFactory().enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
            config = mapper.readValue(jsonResponse, ImageConfiguration.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return config;
    }

    @Override
    public List<LayerLegend> getLayerLegends(MapService dynamicMapService) {

        if (dynamicMapService == null || dynamicMapService.getUrl() == null) {
            throw new IllegalArgumentException("A URL do servidor não pode ser null");
        }

        String serverUrl = dynamicMapService.getUrl().toString();
        String requestUrl = serverUrl.concat("/legend?f=json");
        Client c = getClient();
        WebResource rs = c.resource(requestUrl);
        ClientResponse response = rs.get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Código: " + response.getStatus() + " " + requestUrl);

        }
        String jsonResponse = response.getEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        LegendResource legendResource = null;
        try {
            mapper.getFactory().enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
            legendResource = mapper.readValue(jsonResponse, LegendResource.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return legendResource.getLayers();
    }

    public static void main(String[] args) throws Exception {
        String x = "{\"displayFieldName\":\"COD_MD\",\"fieldAliases\":{\"COD_MD\":\"COD_MD\"},\"geometryType\":\"esriGeometryPolygon\",\"spatialReference\":{\"wkid\":102100},\"fields\":[{\"name\":\"COD_MD\",\"type\":\"esriFieldTypeString\",\"alias\":\"COD_MD\",\"length\":5}],\"features\":[{\"attributes\":{\"COD_MD\":\"02495\"},\"geometry\":{\"rings\":[[[-4448901.9971614536,-1481181.5681389824],[-4446048.7931190552,-1484374.1640357675],[-4445836.3088127356,-1484490.3810884333],[-4445069.5099925725,-1485284.5886007743],[-4442949.1114713904,-1486344.8120983997],[-4442725.8307329426,-1486735.233921791],[-4442685.6339327302,-1487239.1968041465],[-4443010.0073516816,-1488512.958725028],[-4443057.3890457815,-1488866.8998528079],[-4450732.9065638408,-1496911.6289685257],[-4466179.9300591592,-1491274.5769600852],[-4470339.5780870374,-1472702.3294239251],[-4465176.3336426923,-1473153.9476837311],[-4462519.7515470861,-1473617.6007089247],[-4459228.3812985038,-1472090.2078136103],[-4458639.5102090854,-1472175.2031112125],[-4458247.5683082575,-1472424.1219527284],[-4457563.3117773216,-1473168.5792560547],[-4457057.5061787423,-1473566.2806037324],[-4454901.3448239909,-1474745.4972565284],[-4454460.6311825756,-1475060.6292790917],[-4453841.402600104,-1475755.38233024],[-4453288.4903778806,-1476647.5594119329],[-4452425.4492900725,-1477821.1534016719],[-4451771.8012776449,-1478120.6405770045],[-4450986.313250808,-1478255.6093719702],[-4450709.0254161181,-1478504.0946746671],[-4450432.1709482102,-1478884.356800067],[-4450091.1345599685,-1479594.2911639663],[-4448901.9971614536,-1481181.5681389824]]]}}]}";

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.getFactory().enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
            QueryReturn q = mapper.readValue(x, QueryReturn.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
