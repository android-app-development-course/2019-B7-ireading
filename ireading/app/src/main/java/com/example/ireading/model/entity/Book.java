package com.example.ireading.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book_table")
public class Book {
    public Book(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "uri")
    private String uri;


    public String getUri() {
        return uri;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
