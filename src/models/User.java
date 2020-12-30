package models;

import java.io.Serializable;

public class User extends Person implements Serializable {
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password, String firstName, String lastName,
                String country, String gender) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.gender = gender;
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

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName=" + lastName +
                ", country=" + country +
                ", gender='" + gender + '\'' +
                '}';
    }
}
