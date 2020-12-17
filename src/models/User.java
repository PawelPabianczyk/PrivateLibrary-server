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
