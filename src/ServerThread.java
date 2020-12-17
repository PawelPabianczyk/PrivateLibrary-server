import models.Book;
import models.User;

import java.io.*;
import java.net.Socket;
import java.sql.*;

public class ServerThread extends Thread {
    Socket socket;
    Connection connection;
    ServerThread(Socket socket) {
        this.socket=socket;
        String dbURL = "jdbc:mysql://localhost:3306/privlib";
        String username = "privlib";
        String password = "privlib";
        try {
            this.connection = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected to MySQL database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            String typeOfConnection = inputStream.readObject().toString();

            if(typeOfConnection.equals("sending login data")){
                inputStream = new ObjectInputStream(socket.getInputStream());
                User user = (User) inputStream.readObject();

                Boolean isConnectionValid;
                if(validLoginData(user)){
                    isConnectionValid=true;
                }
                else{
                    isConnectionValid = false;
                }

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(isConnectionValid);

                socket.close();
            }

            if(typeOfConnection.equals("sending book data")){
                inputStream = new ObjectInputStream(socket.getInputStream());
                Book book = (Book) inputStream.readObject();
                System.out.println(book.toString());
                addBook(book);
                socket.close();
            }


        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client closed connection.");
            e.printStackTrace();
        }
    }

    private void addBook(Book book){
        try {
            String query = " insert into books (title, author, genre, publisher, language, description, publish_date,date_of_return,status)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, book.title);
            preparedStmt.setString (2, book.author);
            preparedStmt.setString (3, book.genre);
            preparedStmt.setString (4, book.publisher);
            preparedStmt.setString (5, book.language);
            preparedStmt.setString (6, book.description);
            preparedStmt.setDate   (7, java.sql.Date.valueOf(book.publishDate));
            if(book.returnDate == null){
                preparedStmt.setDate   (8, null);
                preparedStmt.setString (9, "own");
            }
            else
            {
                preparedStmt.setDate   (8, java.sql.Date.valueOf(book.returnDate));
                preparedStmt.setString (9, "borrowed");
            }
            preparedStmt.execute();

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    private Boolean validLoginData(User user){
        try {
            Statement stmt=connection.createStatement();
            String query = "select password from users where username=\'" + user.username + "\'";
            ResultSet rs=stmt.executeQuery(query);
            String dbPassword = "";
            while (rs.next()) {
                dbPassword = rs.getString("password");
            }

            if(user.password.equals(dbPassword))
                return true;
            else
                return false;

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return false;
    }
}
