package co.com.sofka.libraryapi.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

import java.time.LocalDate;

@Document
public class Resource {
    @Id
    private String id;
    private String title;
    private String type;
    private String subjectArea;
    private boolean isLent;
    private LocalDate dateLent;

    public Resource() {
    }

    public Resource(String id, String title, String type, String subjectArea) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.type = type;
        this.subjectArea = subjectArea;
        this.isLent = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public boolean isLent() {
        return isLent;
    }

    public void setLent(boolean lent) {
        isLent = lent;
    }

    public LocalDate getDateLent() {
        return dateLent;
    }

    public void setDateLent(LocalDate dateLent) {
        this.dateLent = dateLent;
    }
}
