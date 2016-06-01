package br.lucasslf.gis.swingviewer.model;

import java.util.List;

public class BaseMapConfiguration implements Configuration {

    private String capabilities;
    private String copyrightText;
    private Number currentVersion;
    private String description;
    private DocumentInfo documentInfo;
    private Extent fullExtent;
    private Extent initialExtent;
    private List<Layer> layers;
    private String mapName;
    private String serviceDescription;
    private boolean singleFusedMapCache;
    private SpatialReference spatialReference;
    private String supportedImageFormatTypes;
    private List<Table> tables;
    private TileInfo tileInfo;
    private String units;
    private boolean supportsDynamicLayers;
    private double minScale;
    private double maxScale;
    private String supportedQueryFormats;
    private boolean exportTilesAllowed;
    private int maxRecordCount;
    private int maxImageHeight;
    private int maxImageWidth;
    private String supportedExtensions;

    public String getSupportedQueryFormats() {
        return supportedQueryFormats;
    }

    public void setSupportedQueryFormats(String supportedQueryFormats) {
        this.supportedQueryFormats = supportedQueryFormats;
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

    public String getSupportedExtensions() {
        return supportedExtensions;
    }

    public void setSupportedExtensions(String supportedExtensions) {
        this.supportedExtensions = supportedExtensions;
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

    
    
    public String getCapabilities() {
        return this.capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public String getCopyrightText() {
        return this.copyrightText;
    }

    public void setCopyrightText(String copyrightText) {
        this.copyrightText = copyrightText;
    }

    @Override
    public Number getCurrentVersion() {
        return this.currentVersion;
    }

    public void setCurrentVersion(Number currentVersion) {
        this.currentVersion = currentVersion;
    }

    @Override
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

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
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

    public String getName() {
        return mapName;
    }

    public boolean isSupportsDynamicLayers() {
        return supportsDynamicLayers;
    }

    public void setSupportsDynamicLayers(boolean supportsDynamicLayers) {
        this.supportsDynamicLayers = supportsDynamicLayers;
    }
}
