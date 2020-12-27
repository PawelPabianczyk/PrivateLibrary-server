import models.Book;
import models.User;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

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

            switch (typeOfConnection){
                case "PUT login":{
                    User user = (User) inputStream.readObject();
                    boolean isConnectionValid= validLoginData(user);
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(isConnectionValid);
                    break;
                }
                case "POST books":{
                    Book book = (Book) inputStream.readObject();
                    String username = (String)inputStream.readObject();
                    addBook(book, username);
                    break;
                }
                case "POST register":{
                    User user = (User) inputStream.readObject();
                    addUser(user);
                    break;
                }

                case "GET all books":{
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getAllBooks());
                    break;
                }

                case "GET user books":{
                    String username = (String)inputStream.readObject();
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getBooks(username));
                    break;
                }
                default:
                    System.out.println("Type of connection is not correct.");
                    break;
            }
            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client closed connection.");
            e.printStackTrace();
        }
    }

    private ArrayList<Book> getBooks(String username){
        try {
            Statement stmt=connection.createStatement();
            String query = "select b.title, b.author, b.genre, b.publish_date, b.status from books b " +
                    "natural join user_book ub " +
                    "natural join users u where u.username='" + username + "'";
            ResultSet rs=stmt.executeQuery(query);
            Book book;
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                LocalDate publishDate = rs.getDate("publish_date").toLocalDate();
                String status = rs.getString("status");
                book = new Book(title,author,genre,publishDate, status);
                book.setOwner(username);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Book> getAllBooks(){
        try {
            Statement stmt=connection.createStatement();
            String query = "select b.title, b.author, b.genre, u.username, ub.date_added from books b " +
                    "natural join user_book ub " +
                    "natural join users u";
            ResultSet rs=stmt.executeQuery(query);
            Book book;
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                String owner = rs.getString("username");
                LocalDate dateAdded = rs.getDate("date_added").toLocalDate();

                book = new Book(title,author,genre,owner, dateAdded);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

    private void addBook(Book book, String username){
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

            int userId = getUserId(username);
            int bookId = getBookId(book.title, book.author);

            query ="insert into user_book (id_user, id_book, date_added) values(?,?,now())";
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt (1, userId);
            preparedStmt.setInt (2, bookId);
            preparedStmt.execute();

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    private void addUser(User user){
        try {
            String query = " insert into users (username, password, first_name, last_name, country, gender, favourite_genre,favourite_author)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString (1, user.username);
            preparedStmt.setString (2, user.password);
            preparedStmt.setString (3, user.firstName);
            preparedStmt.setString (4, user.lastName);
            preparedStmt.setString (5, user.country);
            preparedStmt.setString (6, user.gender);
            preparedStmt.setString (7, user.favouriteGenre);
            preparedStmt.setString (8, user.favouriteAuthor);
            preparedStmt.execute();

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    private Boolean validLoginData(User user){
        try {
            Statement stmt=connection.createStatement();
            String query = "select password from users where username='" + user.username + "'";
            ResultSet rs=stmt.executeQuery(query);
            String dbPassword = "";
            while (rs.next()) {
                dbPassword = rs.getString("password");
            }
            return user.password.equals(dbPassword);

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return false;
    }

    private int getUserId(String username){
        try {
            Statement stmt=connection.createStatement();
            String query = "select id_user from users where username='" + username + "'";
            ResultSet rs=stmt.executeQuery(query);
            int id = -1;
            while (rs.next()) {
                id = rs.getInt("id_user");
            }

            return id;

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return -1;
    }

    private int getBookId(String title, String author){
        try {
            Statement stmt=connection.createStatement();
            String query = "select id_book from books where title='" + title + "' and author='" + author + "'";
            ResultSet rs=stmt.executeQuery(query);
            int id = -1;
            while (rs.next()) {
                id = rs.getInt("id_book");
            }

            return id;

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return -1;
    }
}
