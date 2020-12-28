package models;

import java.io.Serializable;
import java.time.LocalDate;

public class Publisher implements Serializable {
    public String name;
    public LocalDate yearOfFoundation;
    public String description;
}
