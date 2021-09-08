package com.github.zjjfly.readinglist.model;

import javax.persistence.*;

/**
 * Created by zjjfly on 2017/7/2.
 */
@Entity
@Table(name="reading_list")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column
    private String reader;

    @Column(length = 15 ,nullable = false)
    private String isbn;

    @Column(length = 50 ,nullable = false)
    private String title;

    @Column(length = 50 ,nullable = false)
    private String author;

    @Column(length = 400)
    private String description;


    public String getReader() {
        return reader;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
