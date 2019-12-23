package com.example.ireading.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ireading.model.entity.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insert(Book word);

    @Query("select * from book_table order by id desc")
    List<Book> queryAll();

    @Query("delete from book_table")
    void deleteAll();

    // @Query("delete from book_table where uri = :uri")
    // void delete(String uri);
    @Delete
    int delete(Book book);
}

