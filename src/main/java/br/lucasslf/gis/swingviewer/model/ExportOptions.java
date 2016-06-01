package br.lucasslf.gis.swingviewer.model;

/**
 *
 */
public class ExportOptions {

    private boolean useTime;
    private boolean timeDataCumulative;
    private Number timeOffset;
    private String timeOffsetUnits;

    public boolean isUseTime() {
        return useTime;
    }

    public void setUseTime(boolean useTime) {
        this.useTime = useTime;
    }

    public boolean isTimeDataCumulative() {
        return timeDataCumulative;
    }

    public void setTimeDataCumulative(boolean timeDataCumulative) {
        this.timeDataCumulative = timeDataCumulative;
    }

    public Number getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(Number timeOffset) {
        this.timeOffset = timeOffset;
    }

    public String getTimeOffsetUnits() {
        return timeOffsetUnits;
    }

    public void setTimeOffsetUnits(String timeOffsetUnits) {
        this.timeOffsetUnits = timeOffsetUnits;
    }
    
    
    
}
