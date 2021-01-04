package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
    protected String firstName;
    protected String lastName;
    protected String gender;
    protected String country;

    /*with MongoDB*/
    /*array contain ids*/
    protected ArrayList<String> books;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ArrayList<String> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<String> books) {
        this.books = books;
    }
}
