package com.kronos.roomlivedataexample.activities.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kronos.roomlivedataexample.model.Todo;
import com.kronos.roomlivedataexample.model.User;
import com.kronos.roomlivedataexample.model.UserTodo;
import com.kronos.roomlivedataexample.model.repository.TodoRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {

    private TodoRepository todoRepository;
    private LiveData<List<Todo>> todos;
    private LiveData<List<Todo>> todosByUser;
    private User user;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        todoRepository = new TodoRepository(application);
        todos = todoRepository.getTodos();
    }

    public void insert(Todo todo){
        todoRepository.insert(todo);
    }

    public void update(Todo todo){
        todoRepository.update(todo);
    }

    public void delete(Todo todo){
        todoRepository.delete(todo);
    }

    public void deleteAll(){
        todoRepository.deleteAll();
    }

    public LiveData<List<Todo>> getTodos() {
        return todos;
    }

    public LiveData<List<Todo>> getTodosByUser() {
        todosByUser = todoRepository.getTodosByUser(user.getId());
        return todosByUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
