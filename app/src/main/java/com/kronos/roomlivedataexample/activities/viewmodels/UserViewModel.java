package com.kronos.roomlivedataexample.activities.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kronos.roomlivedataexample.model.Todo;
import com.kronos.roomlivedataexample.model.User;
import com.kronos.roomlivedataexample.model.UserTodo;
import com.kronos.roomlivedataexample.model.repository.TodoRepository;
import com.kronos.roomlivedataexample.model.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<UserTodo>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getUsers();
    }

    public void insert(User user){
        userRepository.insert(user);
    }

    public void update(User user){
        userRepository.update(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public void deleteAll(){
        userRepository.deleteAll();
    }

    public LiveData<List<UserTodo>> getUsers() {
        return users;
    }

}
