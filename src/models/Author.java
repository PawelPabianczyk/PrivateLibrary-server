package models;

import java.io.Serializable;

public class Author extends Person implements Serializable {
    private String biography;

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
