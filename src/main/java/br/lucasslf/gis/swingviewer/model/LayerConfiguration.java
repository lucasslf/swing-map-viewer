package br.lucasslf.gis.swingviewer.model;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class LayerConfiguration {

    private Double currentVersion;
    private int id;
    private String name;
    private String type;
    private String description;
    private String definitionExpression;
    private String geometryType;
    private String copyrightText;
    private Map<String, Object> parentLayer;
    private List<Map<String, Object>> subLayers;
    private double minScale;
    private double maxScale;
    private boolean defaultVisibility;
    private Extent extent;
    private boolean hasAttachments;
    private String htmlPopupType;
    private DrawingInfo drawingInfo;
    private String displayField;
    private List<Field> fields;
    private Object typeIdField;
    private List<Type> types;
    private List<Relationship> relationships;
    private String capabilities;
    private boolean hasZ;
    private boolean hasM;
    private boolean canModifyLayer;
    private boolean canScaleSymbols;
    private boolean hasLabels;
    private int maxRecordCount;
    private boolean supportsStatistics;
    private boolean supportsAdvancedQueries;
    private String supportedQueryFormats;
    private OwnershipBasedAccessControl ownershipBasedAccessControlForFeatures;
    private TimeInfo timeInfo;
    private double effectiveMinScale;
    private double effectiveMaxScale;
    private boolean useStandardizedQueries;

    public double getEffectiveMinScale() {
        return effectiveMinScale;
    }

    public void setEffectiveMinScale(double effectiveMinScale) {
        this.effectiveMinScale = effectiveMinScale;
    }

    public double getEffectiveMaxScale() {
        return effectiveMaxScale;
    }

    public void setEffectiveMaxScale(double effectiveMaxScale) {
        this.effectiveMaxScale = effectiveMaxScale;
    }

    public boolean isUseStandardizedQueries() {
        return useStandardizedQueries;
    }

    public void setUseStandardizedQueries(boolean useStandardizedQueries) {
        this.useStandardizedQueries = useStandardizedQueries;
    }

    public TimeInfo getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(TimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }

    public Double getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Double currentVersion) {
        this.currentVersion = currentVersion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefinitionExpression() {
        return definitionExpression;
    }

    public void setDefinitionExpression(String definitionExpression) {
        this.definitionExpression = definitionExpression;
    }

    public String getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
    }

    public String getCopyrightText() {
        return copyrightText;
    }

    public void setCopyrightText(String copyrightText) {
        this.copyrightText = copyrightText;
    }

    public Map<String, Object> getParentLayer() {
        return parentLayer;
    }

    public void setParentLayer(Map<String, Object> parentLayer) {
        this.parentLayer = parentLayer;
    }

    public List<Map<String, Object>> getSubLayers() {
        return subLayers;
    }

    public void setSubLayers(List<Map<String, Object>> subLayers) {
        this.subLayers = subLayers;
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

    public boolean isDefaultVisibility() {
        return defaultVisibility;
    }

    public void setDefaultVisibility(boolean defaultVisibility) {
        this.defaultVisibility = defaultVisibility;
    }

    public Extent getExtent() {
        return extent;
    }

    public void setExtent(Extent extent) {
        this.extent = extent;
    }

    public boolean isHasAttachments() {
        return hasAttachments;
    }

    public void setHasAttachments(boolean hasAttachments) {
        this.hasAttachments = hasAttachments;
    }

    public String getHtmlPopupType() {
        return htmlPopupType;
    }

    public void setHtmlPopupType(String htmlPopupType) {
        this.htmlPopupType = htmlPopupType;
    }

    public DrawingInfo getDrawingInfo() {
        return drawingInfo;
    }

    public void setDrawingInfo(DrawingInfo drawingInfo) {
        this.drawingInfo = drawingInfo;
    }

    public String getDisplayField() {
        return displayField;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Object getTypeIdField() {
        return typeIdField;
    }

    public void setTypeIdField(Object typeIdField) {
        this.typeIdField = typeIdField;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public boolean isHasZ() {
        return hasZ;
    }

    public void setHasZ(boolean hasZ) {
        this.hasZ = hasZ;
    }

    public boolean isHasM() {
        return hasM;
    }

    public void setHasM(boolean hasM) {
        this.hasM = hasM;
    }

    public boolean isCanModifyLayer() {
        return canModifyLayer;
    }

    public void setCanModifyLayer(boolean canModifyLayer) {
        this.canModifyLayer = canModifyLayer;
    }

    public boolean isCanScaleSymbols() {
        return canScaleSymbols;
    }

    public void setCanScaleSymbols(boolean canScaleSymbols) {
        this.canScaleSymbols = canScaleSymbols;
    }

    public boolean isHasLabels() {
        return hasLabels;
    }

    public void setHasLabels(boolean hasLabels) {
        this.hasLabels = hasLabels;
    }

    public int getMaxRecordCount() {
        return maxRecordCount;
    }

    public void setMaxRecordCount(int maxRecordCount) {
        this.maxRecordCount = maxRecordCount;
    }

    public boolean isSupportsStatistics() {
        return supportsStatistics;
    }

    public void setSupportsStatistics(boolean supportsStatistics) {
        this.supportsStatistics = supportsStatistics;
    }

    public boolean isSupportsAdvancedQueries() {
        return supportsAdvancedQueries;
    }

    public void setSupportsAdvancedQueries(boolean supportsAdvancedQueries) {
        this.supportsAdvancedQueries = supportsAdvancedQueries;
    }

    public String getSupportedQueryFormats() {
        return supportedQueryFormats;
    }

    public void setSupportedQueryFormats(String supportedQueryFormats) {
        this.supportedQueryFormats = supportedQueryFormats;
    }

    public OwnershipBasedAccessControl getOwnershipBasedAccessControlForFeatures() {
        return ownershipBasedAccessControlForFeatures;
    }

    public void setOwnershipBasedAccessControlForFeatures(OwnershipBasedAccessControl ownershipBasedAccessControlForFeatures) {
        this.ownershipBasedAccessControlForFeatures = ownershipBasedAccessControlForFeatures;
    }
}
