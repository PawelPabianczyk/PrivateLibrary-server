package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {
    private String title;
    private String author;
    private String genre;
    private String publisher;
    private String language;
    private String description;
    private LocalDate publishDate;
    private LocalDate returnDate;
    private String status;
    private String owner;
    private LocalDate dateAdded;

    /*with MongoDB*/
    /*arrays contain ids*/
    private String[] genres;
    private String[] publishers;
    private String[] authors;
    private String[] owners;

    public Book() {
    }

    public Book(String title, String author, String genre, LocalDate publishDate, String status){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publishDate = publishDate;
        this.status = status;
    }

    public Book(String title, String author, String genre, String owner, LocalDate dateAdded){
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.owner = owner;
        this.dateAdded = dateAdded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOwners(String[] owners) {
        this.owners = owners;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String[] getPublishers() {
        return publishers;
    }

    public void setPublishers(String[] publishers) {
        this.publishers = publishers;
    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String[] getOwners() {
        return owners;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", owner='" + owner + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publishDate=" + publishDate +
                ", returnDate=" + returnDate +
                ", language='" + language + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
