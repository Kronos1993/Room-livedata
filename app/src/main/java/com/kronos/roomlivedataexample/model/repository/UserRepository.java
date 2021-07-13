package com.kronos.roomlivedataexample.model.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.kronos.roomlivedataexample.model.User;
import com.kronos.roomlivedataexample.model.UserTodo;
import com.kronos.roomlivedataexample.model.dao.TodoDatabase;
import com.kronos.roomlivedataexample.model.dao.UserDao;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<UserTodo>> users;

    public UserRepository(Application application){
        TodoDatabase todoDatabase = TodoDatabase.getInstance(application);
        userDao = todoDatabase.getUserDao();
        users = userDao.findAll();
    }

    public void insert(User user){
        new UserRepository.InsertAsyncTask(userDao).execute(user);
    }

    public void update(User user){
        new UserRepository.UpdateAsyncTask(userDao).execute(user);
    }

    public void delete(User user){
        new UserRepository.DeleteAsyncTask(userDao).execute(user);
    }

    public void deleteAll(){
        new UserRepository.DeleteAllAsyncTask(userDao).execute();
    }

    public LiveData<List<UserTodo>> getUsers() {
        return users;
    }

    private static class InsertAsyncTask extends AsyncTask<User,Void,Void> {

        private UserDao userDao;

        private InsertAsyncTask (UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<User,Void,Void>{
        private UserDao userDao;

        private UpdateAsyncTask (UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<User,Void,Void>{

        private UserDao userDao;

        private DeleteAsyncTask (UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{

        private UserDao userDao;

        private DeleteAllAsyncTask (UserDao userDao){
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAll();
            return null;
        }
    }

}
