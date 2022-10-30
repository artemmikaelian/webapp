package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotBlank
    @Size(min = 2, max = 15)
    private String name;
    @NotBlank
    @Size(min = 2, max = 15)
    private String surname;
    private String patronymic;
    @Size(max = 45)
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer() {
    }

    public Customer(int id, String name, String surname, String patronymic, String description) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.description = description;
    }
}
