package br.lucasslf.gis.swingviewer.model;

import java.net.URI;
import java.util.List;

public class MapConfiguration implements Configuration {

    private Double currentVersion;
    private String serviceDescription;
    private String mapName;
    private String description;
    private String copyrightText;
    private List<Layer> layers;
    private SpatialReference spatialReference;
    private boolean singleFusedMapCache;
    private TileInfo tileInfo;
    private Extent initialExtent;
    private Extent fullExtent;
    private String units;
    private String supportedImageFormatTypes;
    private DocumentInfo documentInfo;
    private List<Table> tables;
    private String capabilities;
    private boolean supportsDynamicLayers;
    private TimeInfo timeInfo;
    private int maxRecordCount;
    private int maxImageHeight;
    private int maxImageWidth;
    private double minScale;
    private double  maxScale;
    private List<URI> tileServers;
    private String supportedQueryFormats;
    
    private boolean exportTilesAllowed;
    private int maxExportTilesCount;
    private String supportedExtensions;

    public int getMaxExportTilesCount() {
        return maxExportTilesCount;
    }

    public void setMaxExportTilesCount(int maxExportTilesCount) {
        this.maxExportTilesCount = maxExportTilesCount;
    }

    public String getSupportedExtensions() {
        return supportedExtensions;
    }

    public void setSupportedExtensions(String supportedExtensions) {
        this.supportedExtensions = supportedExtensions;
    }
    
    

    public boolean isExportTilesAllowed() {
        return exportTilesAllowed;
    }

    public void setExportTilesAllowed(boolean exportTilesAllowed) {
        this.exportTilesAllowed = exportTilesAllowed;
    }
    
    
    
    public int getMaxRecordCount() {
        return maxRecordCount;
    }

    public void setMaxRecordCount(int maxRecordCount) {
        this.maxRecordCount = maxRecordCount;
    }

    public int getMaxImageHeight() {
        return maxImageHeight;
    }

    public void setMaxImageHeight(int maxImageHeight) {
        this.maxImageHeight = maxImageHeight;
    }

    public int getMaxImageWidth() {
        return maxImageWidth;
    }

    public void setMaxImageWidth(int maxImageWidth) {
        this.maxImageWidth = maxImageWidth;
    }

    public double getMinScale() {
        return minScale;
    }

    public void setMinScale(double minScale) {
        this.minScale = minScale;
    }

    public double getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(double maxScale) {
        this.maxScale = maxScale;
    }

    public List<URI> getTileServers() {
        return tileServers;
    }

    public void setTileServers(List<URI> tileServers) {
        this.tileServers = tileServers;
    }

    public String getSupportedQueryFormats() {
        return supportedQueryFormats;
    }

    public void setSupportedQueryFormats(String supportedQueryFormats) {
        this.supportedQueryFormats = supportedQueryFormats;
    }
    
    
    public TimeInfo getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(TimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }

    public boolean isSupportsDynamicLayers() {
        return supportsDynamicLayers;
    }

    public void setSupportsDynamicLayers(boolean supportsDynamicLayers) {
        this.supportsDynamicLayers = supportsDynamicLayers;
    }

    public String getCopyrightText() {
        return this.copyrightText;
    }

    public void setCopyrightText(String copyrightText) {
        this.copyrightText = copyrightText;
    }

    public Double getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Double currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DocumentInfo getDocumentInfo() {
        return this.documentInfo;
    }

    public void setDocumentInfo(DocumentInfo documentInfo) {
        this.documentInfo = documentInfo;
    }

    public Extent getFullExtent() {
        return this.fullExtent;
    }

    public void setFullExtent(Extent fullExtent) {
        this.fullExtent = fullExtent;
    }

    public Extent getInitialExtent() {
        return this.initialExtent;
    }

    public void setInitialExtent(Extent initialExtent) {
        this.initialExtent = initialExtent;
    }

    public List<Layer> getLayers() {
        return this.layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public String getMapName() {
        return this.mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getServiceDescription() {
        return this.serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public boolean getSingleFusedMapCache() {
        return this.singleFusedMapCache;
    }

    public void setSingleFusedMapCache(boolean singleFusedMapCache) {
        this.singleFusedMapCache = singleFusedMapCache;
    }

    public SpatialReference getSpatialReference() {
        return this.spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    public String getSupportedImageFormatTypes() {
        return this.supportedImageFormatTypes;
    }

    public void setSupportedImageFormatTypes(String supportedImageFormatTypes) {
        this.supportedImageFormatTypes = supportedImageFormatTypes;
    }

    public TileInfo getTileInfo() {
        return this.tileInfo;
    }

    public void setTileInfo(TileInfo tileInfo) {
        this.tileInfo = tileInfo;
    }

    public String getUnits() {
        return this.units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public String getName() {
        return mapName;
    }
}
