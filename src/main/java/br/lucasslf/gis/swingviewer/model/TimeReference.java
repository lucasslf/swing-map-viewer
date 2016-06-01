package br.lucasslf.gis.swingviewer.model;

/**
 *
 */
public class TimeReference {

    private String timeZone;
    private boolean respectsDaylightSaving;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isRespectsDaylightSaving() {
        return respectsDaylightSaving;
    }

    public void setRespectsDaylightSaving(boolean respectsDaylightSaving) {
        this.respectsDaylightSaving = respectsDaylightSaving;
    }
    
    
}
