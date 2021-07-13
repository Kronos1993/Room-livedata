package com.kronos.roomlivedataexample.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.kronos.roomlivedataexample.model.Todo;
import com.kronos.roomlivedataexample.model.UserTodo;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("delete from todo")
    void deleteAll();

    @Query("select * from todo order by priority desc")
    LiveData<List<Todo>> findAll();

    @Query("select * from todo where user_id = :user_id order by priority desc")
    LiveData<List<Todo>> findAllByUserId(long user_id);

    @Query("select * from todo where id = :id order by priority asc")
    Todo findById(long id);
}
