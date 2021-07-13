package com.kronos.roomlivedataexample.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
foreignKeys = @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "user_id",onDelete = CASCADE)
)
public class Todo implements Serializable {

    private final static long serialVersionUid = 1L;

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title,description;
    private int priority;
    private long user_id;

    public Todo() {
    }

    public Todo(String title, String description, int priority,long user_id) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todo)) return false;
        Todo todo = (Todo) o;
        return getId() == todo.getId() &&
                getPriority() == todo.getPriority() &&
                getUser_id() == todo.getUser_id() &&
                getTitle().equals(todo.getTitle()) &&
                getDescription().equals(todo.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getPriority(), getUser_id());
    }
}
