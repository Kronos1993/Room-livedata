package com.kronos.roomlivedataexample.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.kronos.roomlivedataexample.model.User;
import com.kronos.roomlivedataexample.model.UserTodo;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("delete from user")
    void deleteAll();

    @Transaction
    @Query("select * from user order by ci desc")
    LiveData<List<UserTodo>> findAll();

    @Query("select * from user where id == :id")
    User findById(long id);

    @Query("select * from user where name == :name")
    User findByName(String name);

}
