package br.lucasslf.gis.swingviewer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentInfo {

    private String title;
    private String author;
    private String category;
    private String comments;
    private String credits;
    private String keywords;
    private String subject;
    private String AntialiasingMode;
    private String TextAntialiasingMode;

    @JsonProperty(value = "Author")
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty(value = "Category")
    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty(value = "Comments")
    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @JsonProperty(value = "Credits")
    public String getCredits() {
        return this.credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    @JsonProperty(value = "Keywords")
    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @JsonProperty(value = "Subject")
    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @JsonProperty(value = "Title")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @JsonProperty(value = "AntialiasingMode")
    public String getAntialiasingMode() {
        return AntialiasingMode;
    }

    public void setAntialiasingMode(String AntialiasingMode) {
        this.AntialiasingMode = AntialiasingMode;
    }

    @JsonProperty(value = "TextAntialiasingMode")
    public String getTextAntialiasingMode() {
        return TextAntialiasingMode;
    }

    public void setTextAntialiasingMode(String TextAntialiasingMode) {
        this.TextAntialiasingMode = TextAntialiasingMode;
    }
    
    
    
}
