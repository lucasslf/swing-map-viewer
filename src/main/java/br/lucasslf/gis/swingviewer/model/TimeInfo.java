package br.lucasslf.gis.swingviewer.model;

import java.util.Date;

/**
 *
 */
public class TimeInfo {

    private Date[] timeExtent;
    private TimeReference timeReference;
    private String timeRelation;
    private Number defaultTimeInterval;
    private String defaultTimeIntervalUnits;
    private Number defaultTimeWindow;
    private boolean hasLiveData;
    private String startTimeField;
    private String endTimeField;
    private String trackIdField;
    private Number timeInterval;
    private String timeIntervalUnits;
    private Number timeWindow;
    private ExportOptions exportOptions;

    public ExportOptions getExportOptions() {
        return exportOptions;
    }

    public void setExportOptions(ExportOptions exportOptions) {
        this.exportOptions = exportOptions;
    }
    
    
    public String getStartTimeField() {
        return startTimeField;
    }

    public void setStartTimeField(String startTimeField) {
        this.startTimeField = startTimeField;
    }

    public String getEndTimeField() {
        return endTimeField;
    }

    public void setEndTimeField(String endTimeField) {
        this.endTimeField = endTimeField;
    }

    public String getTrackIdField() {
        return trackIdField;
    }

    public void setTrackIdField(String trackIdField) {
        this.trackIdField = trackIdField;
    }

    public Number getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Number timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getTimeIntervalUnits() {
        return timeIntervalUnits;
    }

    public void setTimeIntervalUnits(String timeIntervalUnits) {
        this.timeIntervalUnits = timeIntervalUnits;
    }

    public Number getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(Number timeWindow) {
        this.timeWindow = timeWindow;
    }

    
    
    public String getTimeRelation() {
        return timeRelation;
    }

    public void setTimeRelation(String timeRelation) {
        this.timeRelation = timeRelation;
    }

    public Number getDefaultTimeInterval() {
        return defaultTimeInterval;
    }

    public void setDefaultTimeInterval(Number defaultTimeInterval) {
        this.defaultTimeInterval = defaultTimeInterval;
    }

    public String getDefaultTimeIntervalUnits() {
        return defaultTimeIntervalUnits;
    }

    public void setDefaultTimeIntervalUnits(String defaultTimeIntervalUnits) {
        this.defaultTimeIntervalUnits = defaultTimeIntervalUnits;
    }

    public Number getDefaultTimeWindow() {
        return defaultTimeWindow;
    }

    public void setDefaultTimeWindow(Number defaultTimeWindow) {
        this.defaultTimeWindow = defaultTimeWindow;
    }

    public boolean isHasLiveData() {
        return hasLiveData;
    }

    public void setHasLiveData(boolean hasLiveData) {
        this.hasLiveData = hasLiveData;
    }

    public Date[] getTimeExtent() {
        return timeExtent;
    }

    public void setTimeExtent(Date[] timeExtent) {
        this.timeExtent = timeExtent;
    }

    public TimeReference getTimeReference() {
        return timeReference;
    }

    public void setTimeReference(TimeReference timeReference) {
        this.timeReference = timeReference;
    }
}
