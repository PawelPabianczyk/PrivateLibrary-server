import models.*;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServerThread extends Thread {
    Socket socket;
    Connection connection;

    ServerThread(Socket socket) {
        this.socket = socket;
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

            switch (typeOfConnection) {
                case "PUT login": {
                    User user = (User) inputStream.readObject();
                    boolean isConnectionValid = validLoginData(user);
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(isConnectionValid);
                    break;
                }
                case "GET user data": {
                    String username = (String) inputStream.readObject();
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getUser(username));
                    break;
                }
                case "GET favourite genre": {
                    String username = (String) inputStream.readObject();
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getFavouriteGenre(username));
                    break;
                }
                case "GET favourite author": {
                    String username = (String) inputStream.readObject();
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getFavouriteAuthor(username));
                    break;
                }
                case "POST books": {
                    Book book = (Book) inputStream.readObject();
                    String username = (String) inputStream.readObject();
                    addBook(book, username);
                    break;
                }
                case "POST register": {
                    User user = (User) inputStream.readObject();
                    addUser(user);
                    break;
                }
                case "POST personal data": {
                    User user = (User) inputStream.readObject();
                    updateUser(user);
                    break;
                }
                case "POST new genre": {
                    Genre genre = (Genre) inputStream.readObject();
                    addGenre(genre);
                    break;
                }
                case "POST new publisher": {
                    Publisher publisher = (Publisher) inputStream.readObject();
                    addPublisher(publisher);
                    break;
                }
                case "POST new author": {
                    Author author = (Author) inputStream.readObject();
                    addAuthor(author);
                    break;
                }
                case "GET all books": {
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getAllBooks());
                    break;
                }
                case "GET all books with phrase": {
                    String category = (String) inputStream.readObject();
                    String phrase = (String) inputStream.readObject();
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getAllBooks(category, phrase));
                    break;
                }
                case "GET user books": {
                    String username = (String) inputStream.readObject();
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getBooks(username));
                    break;
                }
                case "GET user books with phrase": {
                    String category = (String) inputStream.readObject();
                    String phrase = (String) inputStream.readObject();
                    String username = (String) inputStream.readObject();
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getBooks(username, category, phrase));
                    break;
                }
                case "GET genres list": {
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getAllGenres());
                    break;
                }
                case "GET publishers list": {
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getAllPublishers());
                    break;
                }
                case "GET authors list": {
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(getAllAuthors());
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

    private ArrayList<String> getAllGenres() {
        try {
            Statement stmt = connection.createStatement();
            String query = "select name from genres";
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<String> genres = new ArrayList<>();
            while (rs.next()) {
                genres.add(rs.getString("name"));
            }
            return genres;
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<String> getAllPublishers() {
        try {
            Statement stmt = connection.createStatement();
            String query = "select name from publishers";
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<String> publishers = new ArrayList<>();
            while (rs.next()) {
                publishers.add(rs.getString("name"));
            }
            return publishers;
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<String> getAllAuthors() {
        try {
            Statement stmt = connection.createStatement();
            String query = "select first_name, last_name from authors";
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<String> publishers = new ArrayList<>();
            while (rs.next()) {
                String authorName = rs.getString("first_name") +" "+ rs.getString("last_name");
                publishers.add(authorName);
            }
            return publishers;
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

    private void updateUser(User user) {
        try {
            String query = "UPDATE users u SET u.first_name=?, u.last_name=?, u.country=?, " +
                    "u.gender=? WHERE username='" + user.getUsername() + "'";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, user.getFirstName());
            preparedStmt.setString(2, user.getLastName());
            preparedStmt.setString(3, user.getCountry());
            preparedStmt.setString(4, user.getGender());
            preparedStmt.execute();
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    public User getUser(String username) {
        try {
            Statement stmt = connection.createStatement();
            String query = "select * from users where username='" + username + "'";
            ResultSet rs = stmt.executeQuery(query);
            User user = null;
            while (rs.next()) {
                String userName = rs.getString("username");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String country = rs.getString("country");
                String gender = rs.getString("gender");
                user = new User(userName, password, firstName, lastName, country, gender);
            }
            return user;
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

    public String getFavouriteGenre(String username) {
        try {
            Statement stmt = connection.createStatement();

            String query = "select b.id_genre, count(*) quantity from books b " +
                    "NATURAL JOIN user_book ub " +
                    "NATURAL JOIN users u WHERE u.username='" + username + "' " +
                    "GROUP BY b.id_genre ORDER BY quantity DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(query);
            int genreId = 0;
            while (rs.next()) {
                genreId = rs.getInt("id_genre");
            }
            if(genreId == 0)
                return "no genre";
            else
                return getGenreName(genreId);
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

    private String getFavouriteAuthor(String username) {
        try {
            Statement stmt = connection.createStatement();

            String query = "select ab.id_author, count(*) quantity from author_book ab " +
                    "NATURAL JOIN user_book ub " +
                    "NATURAL JOIN users u WHERE u.username='" + username + "' " +
                    "GROUP BY ab.id_author ORDER BY quantity DESC LIMIT 1";
            ResultSet rs = stmt.executeQuery(query);
            int authorId = 0;
            while (rs.next()) {
                authorId = rs.getInt("id_author");
            }
            if(authorId == 0)
                return "no author";
            else
                return getAuthorName(authorId);
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Book> getBooks(String username) {
        try {
            Statement stmt = connection.createStatement();
            String query = "select b.title, ab.id_author, b.id_genre, b.publish_date, b.status from books b " +
                    "natural join author_book ab " +
                    "natural join user_book ub " +
                    "natural join users u where u.username='" + username + "'";
            ResultSet rs = stmt.executeQuery(query);
            Book book;
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {
                String title = rs.getString("title");
                String author = getAuthorName(rs.getInt("id_author"));
                String genre = getGenreName(rs.getInt("id_genre"));
                LocalDate publishDate = rs.getDate("publish_date").toLocalDate();
                String status = rs.getString("status");
                book = new Book(title, author, genre, publishDate, status);
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

    private ArrayList<Book> getBooks(String username, String category, String phrase) {
        try {
            Statement stmt = connection.createStatement();
            String query;
            switch (category){
                case "Title":
                    query = "select b.title, ab.id_author, b.id_genre, b.publish_date, b.status from books b " +
                            "natural join author_book ab " +
                            "natural join user_book ub " +
                            "natural join users u where u.username='" + username + "' and b.title='"+ phrase +"'";
                    break;
                case "Author":
                    query = "select b.title, ab.id_author, b.id_genre, b.publish_date, b.status from books b " +
                            "natural join author_book ab " +
                            "natural join user_book ub " +
                            "natural join users u where u.username='" + username + "' and ab.id_author='"+ getAuthorId(phrase) +"'";
                    break;
                case "Genre":
                    query = "select b.title, ab.id_author, b.id_genre, b.publish_date, b.status from books b " +
                            "natural join author_book ab " +
                            "natural join user_book ub " +
                            "natural join users u where u.username='" + username + "' and b.id_genre='"+ getGenreId(phrase) +"'";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + category);
            }
            ResultSet rs = stmt.executeQuery(query);
            Book book;
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {
                String title = rs.getString("title");
                String author = getAuthorName(rs.getInt("id_author"));
                String genre = getGenreName(rs.getInt("id_genre"));
                LocalDate publishDate = rs.getDate("publish_date").toLocalDate();
                String status = rs.getString("status");
                book = new Book(title, author, genre, publishDate, status);
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

    private ArrayList<Book> getAllBooks() {
        try {
            Statement stmt = connection.createStatement();
            String query = "select b.title, ab.id_author, b.id_genre, u.username, ub.date_added from books b " +
                    "natural join author_book ab " +
                    "natural join user_book ub " +
                    "natural join users u";
            ResultSet rs = stmt.executeQuery(query);
            Book book;
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {
                String title = rs.getString("title");
                String author = getAuthorName(rs.getInt("id_author"));
                String genre = getGenreName(rs.getInt("id_genre"));
                String owner = rs.getString("username");
                LocalDate dateAdded = rs.getDate("date_added").toLocalDate();

                book = new Book(title, author, genre, owner, dateAdded);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Book> getAllBooks(String category, String phrase) {
        try {
            Statement stmt = connection.createStatement();
            String query;
            switch (category){
                case "Title":
                    query = "select b.title, ab.id_author, b.id_genre, u.username, ub.date_added from books b " +
                            "natural join author_book ab " +
                            "natural join user_book ub " +
                            "natural join users u where b.title='" + phrase + "'";
                    break;
                case "Author":
                    query = "select b.title, ab.id_author, b.id_genre, u.username, ub.date_added from books b " +
                            "natural join author_book ab " +
                            "natural join user_book ub " +
                            "natural join users u where ab.id_author='" + getAuthorId(phrase) + "'";
                    break;
                case "Genre":
                    query = "select b.title, ab.id_author, b.id_genre, u.username, ub.date_added from books b " +
                            "natural join author_book ab " +
                            "natural join user_book ub " +
                            "natural join users u where b.id_genre='" + getGenreId(phrase) + "'";
                    break;
                case "Owner":
                    query = "select b.title, ab.id_author, b.id_genre, u.username, ub.date_added from books b " +
                            "natural join author_book ab " +
                            "natural join user_book ub " +
                            "natural join users u where u.username='" + phrase + "'";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + category);
            }

            ResultSet rs = stmt.executeQuery(query);
            Book book;
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {
                String title = rs.getString("title");
                String author = getAuthorName(rs.getInt("id_author"));
                String genre = getGenreName(rs.getInt("id_genre"));
                String owner = rs.getString("username");
                LocalDate dateAdded = rs.getDate("date_added").toLocalDate();

                book = new Book(title, author, genre, owner, dateAdded);
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

    private void addBook(Book book, String username) {
        try {
            String query = " insert into books (title, id_genre, id_publisher, language, description, publish_date,date_of_return,status)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, book.getTitle());
            preparedStmt.setInt(2, getGenreId(book.getGenre()));
            preparedStmt.setInt(3, getPublisherId(book.getPublisher()));
            preparedStmt.setString(4, book.getLanguage());
            preparedStmt.setString(5, book.getDescription());
            preparedStmt.setDate(6, java.sql.Date.valueOf(book.getPublishDate()));
            if (book.getReturnDate() == null) {
                preparedStmt.setDate(7, null);
                preparedStmt.setString(8, "own");
            } else {
                preparedStmt.setDate(7, java.sql.Date.valueOf(book.getReturnDate()));
                preparedStmt.setString(8, "borrowed");
            }
            preparedStmt.execute();

            int userId = getUserId(username);
            int bookId = getBookId(book.getTitle(), book.getPublisher());
            int authorId = getAuthorId(book.getAuthor());

            query = "insert into author_book (id_author, id_book, date_added) values(?,?,now())";
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, authorId);
            preparedStmt.setInt(2, bookId);
            preparedStmt.execute();

            query = "insert into user_book (id_user, id_book, date_added) values(?,?,now())";
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, userId);
            preparedStmt.setInt(2, bookId);
            preparedStmt.execute();



        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    private void addUser(User user) {
        try {
            String query = " insert into users (username, password, first_name, last_name, country, gender)"
                    + " values (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, user.getUsername());
            preparedStmt.setString(2, user.getPassword());
            preparedStmt.setString(3, user.getFirstName());
            preparedStmt.setString(4, user.getLastName());
            preparedStmt.setString(5, user.getCountry());
            preparedStmt.setString(6, user.getGender());
            preparedStmt.execute();

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    private void addGenre(Genre genre) {
        try {
            String query = " insert into genres (name, type, description)"
                    + " values (?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, genre.getName());
            preparedStmt.setString(2, genre.getType());
            preparedStmt.setString(3, genre.getDescription());
            preparedStmt.execute();

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    private void addPublisher(Publisher publisher) {
        try {
            String query = " insert into publishers (name, date_of_foundation, description)"
                    + " values (?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, publisher.getName());
            preparedStmt.setDate(2, java.sql.Date.valueOf(publisher.getDateOfFoundation()));
            preparedStmt.setString(3, publisher.getDescription());
            preparedStmt.execute();

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    private void addAuthor(Author author) {
        try {
            String query = " insert into authors (first_name, last_name, country, gender, biography)"
                    + " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, author.getFirstName());
            preparedStmt.setString(2, author.getLastName());
            preparedStmt.setString(3, author.getCountry());
            preparedStmt.setString(4, author.getGender());
            preparedStmt.setString(5, author.getBiography());
            preparedStmt.execute();

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }

    private Boolean validLoginData(User user) {
        try {
            Statement stmt = connection.createStatement();
            String query = "select password from users where username='" + user.getUsername() + "'";
            ResultSet rs = stmt.executeQuery(query);
            String dbPassword = "";
            while (rs.next()) {
                dbPassword = rs.getString("password");
            }
            return user.getPassword().equals(dbPassword);

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return false;
    }

    private int getUserId(String username) {
        try {
            Statement stmt = connection.createStatement();
            String query = "select id_user from users where username='" + username + "'";
            ResultSet rs = stmt.executeQuery(query);
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

    private int getBookId(String title, String publisher) {
        try {
            Statement stmt = connection.createStatement();
            String query = "select id_book from books " +
                    "where title='" + title + "' and id_publisher=" + getPublisherId(publisher);
            ResultSet rs = stmt.executeQuery(query);
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

    private int getAuthorId(String name) {
        String[] parts = name.split(" ");
        String firstName = parts[0];
        String lastName = parts[1];
        try {
            Statement stmt = connection.createStatement();
            String query = "select id_author from authors where first_name='" + firstName + "' and last_name='" + lastName + "'";
            ResultSet rs = stmt.executeQuery(query);
            int id = -1;
            while (rs.next()) {
                id = rs.getInt("id_author");
            }

            return id;

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return -1;
    }

    private int getGenreId(String genre) {
        try {
            Statement stmt = connection.createStatement();
            String query = "select id_genre from genres where name='" + genre + "'";
            ResultSet rs = stmt.executeQuery(query);
            int id = -1;
            while (rs.next()) {
                id = rs.getInt("id_genre");
            }

            return id;

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return -1;
    }

    private int getPublisherId(String publisher) {
        try {
            Statement stmt = connection.createStatement();
            String query = "select id_publisher from publishers where name='" + publisher + "'";
            ResultSet rs = stmt.executeQuery(query);
            int id = -1;
            while (rs.next()) {
                id = rs.getInt("id_publisher");
            }

            return id;

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return -1;
    }

    private String getGenreName(int id) {
        try {
            Statement stmt = connection.createStatement();
            String query = "select name from genres where id_genre=" + id;
            ResultSet rs = stmt.executeQuery(query);
            String name = null;
            while (rs.next()) {
                name = rs.getString("name");
            }

            return name;

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

    private String getAuthorName(int id) {
        try {
            Statement stmt = connection.createStatement();
            String query = "select first_name, last_name from authors where id_author=" + id;
            ResultSet rs = stmt.executeQuery(query);
            String firstName = null;
            String lastName = null;
            while (rs.next()) {
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
            }

            return firstName+" "+lastName;

        } catch (SQLException e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }
        return null;
    }

}
