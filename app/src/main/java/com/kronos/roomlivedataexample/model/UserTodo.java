package com.kronos.roomlivedataexample.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserTodo {

    @Embedded
    private User user;

    @Relation(parentColumn = "id",entityColumn = "user_id")
    private List<Todo> todos;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }
}
