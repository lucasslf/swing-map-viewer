package br.lucasslf.gis.swingviewer.model;

import java.util.List;

public class ImageConfiguration  implements Configuration {

    private Double currentVersion;
    private String serviceDescription;
    private String name;
    private String description;
    private Extent extent;
    private TimeInfo timeInfo;
    private Double pixelSizeX;
    private Double pixelSizeY;
    private int bandCount;
    private String pixelType;
    private Double minPixelSize;
    private Double maxPixelSize;
    private String copyrightText;
    private String serviceDataType;
    private List<Double[]> minValues;
    private List<Double[]> maxValues;
    private List<Double[]> meanValues;
    private List<Double[]> stdvValues;
    private String objectIdField;
    private List<Field> fields;
    
    
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

    public Extent getExtent() {
        return this.extent;
    }

    public void setExtent(Extent initialExtent) {
        this.extent = initialExtent;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String mapName) {
        this.name = mapName;
    }

    public String getServiceDescription() {
        return this.serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public TimeInfo getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(TimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }

    public Double getPixelSizeX() {
        return pixelSizeX;
    }

    public void setPixelSizeX(Double pixelSizeX) {
        this.pixelSizeX = pixelSizeX;
    }

    public Double getPixelSizeY() {
        return pixelSizeY;
    }

    public void setPixelSizeY(Double pixelSizeY) {
        this.pixelSizeY = pixelSizeY;
    }

    public int getBandCount() {
        return bandCount;
    }

    public void setBandCount(int bandCount) {
        this.bandCount = bandCount;
    }

    public String getPixelType() {
        return pixelType;
    }

    public void setPixelType(String pixelType) {
        this.pixelType = pixelType;
    }

    public Double getMinPixelSize() {
        return minPixelSize;
    }

    public void setMinPixelSize(Double minPixelSize) {
        this.minPixelSize = minPixelSize;
    }

    public Double getMaxPixelSize() {
        return maxPixelSize;
    }

    public void setMaxPixelSize(Double maxPixelSize) {
        this.maxPixelSize = maxPixelSize;
    }

    public String getServiceDataType() {
        return serviceDataType;
    }

    public void setServiceDataType(String serviceDataType) {
        this.serviceDataType = serviceDataType;
    }

    public List<Double[]> getMinValues() {
        return minValues;
    }

    public void setMinValues(List<Double[]> minValues) {
        this.minValues = minValues;
    }

    public List<Double[]> getMaxValues() {
        return maxValues;
    }

    public void setMaxValues(List<Double[]> maxValues) {
        this.maxValues = maxValues;
    }

    public List<Double[]> getMeanValues() {
        return meanValues;
    }

    public void setMeanValues(List<Double[]> meanValues) {
        this.meanValues = meanValues;
    }

    public List<Double[]> getStdvValues() {
        return stdvValues;
    }

    public void setStdvValues(List<Double[]> stdvValues) {
        this.stdvValues = stdvValues;
    }

    public String getObjectIdField() {
        return objectIdField;
    }

    public void setObjectIdField(String objectIdField) {
        this.objectIdField = objectIdField;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
    
    
}
