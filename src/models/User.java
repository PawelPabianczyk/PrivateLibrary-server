package models;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
    private String gender;
    private String favouriteGenre;
    private String favouriteAuthor;

    public User() {
    }

    public User(String username, String password, String firstName, String lastName,
                String country, String gender, String favouriteGenre, String favouriteAuthor) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.gender = gender;
        this.favouriteGenre = favouriteGenre;
        this.favouriteAuthor = favouriteAuthor;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFavouriteGenre() {
        return favouriteGenre;
    }

    public void setFavouriteGenre(String favouriteGenre) {
        this.favouriteGenre = favouriteGenre;
    }

    public String getFavouriteAuthor() {
        return favouriteAuthor;
    }

    public void setFavouriteAuthor(String favouriteAuthor) {
        this.favouriteAuthor = favouriteAuthor;
    }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName=" + lastName +
                ", country=" + country +
                ", gender='" + gender + '\'' +
                ", favouriteGenre='" + favouriteGenre + '\'' +
                ", favouriteAuthor='" + favouriteAuthor + '\'' +
                '}';
    }
}
