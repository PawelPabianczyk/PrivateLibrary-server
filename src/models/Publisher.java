package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Publisher implements Serializable {
    private String name;
    private LocalDate dateOfFoundation;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfFoundation() {
        return dateOfFoundation;
    }

    public void setDateOfFoundation(LocalDate dateOfFoundation) {
        this.dateOfFoundation = dateOfFoundation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
