package models;

import java.io.Serializable;

public class User implements Serializable {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String country;
    public String gender;
    public String favouriteGenre;
    public String favouriteAuthor;

    public User() {
    }

    public User(String username, String password, String firstName, String lastName, String country,
                String gender, String favouriteGenre, String favouriteAuthor) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.gender = gender;
        this.favouriteGenre = favouriteGenre;
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
