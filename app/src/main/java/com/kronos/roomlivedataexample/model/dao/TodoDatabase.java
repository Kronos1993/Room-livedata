package com.kronos.roomlivedataexample.model.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.kronos.roomlivedataexample.model.Todo;
import com.kronos.roomlivedataexample.model.User;

@Database(entities = {Todo.class,User.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase instance;

    public abstract TodoDao getTodoDao();
    public abstract UserDao getUserDao();

    public static synchronized TodoDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),TodoDatabase.class,"todo_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    //callback para cuando se crea la base de datos
    private static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //llamar para iniciar los datos de prueba
            new PopulateDatabaseAsyncTask(instance).execute();
        }
    };


    //job para insertar datos al crear la bd
    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void,Void,Void> {

        private TodoDao todoDao;
        private UserDao userDao;

        private PopulateDatabaseAsyncTask (TodoDatabase todoDatabase){
            this.todoDao = todoDatabase.getTodoDao();
            this.userDao = todoDatabase.getUserDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 5; i++) {
                User user = new User("User "+i,"9302042186"+i);
                userDao.insert(user);
                user = userDao.findByName(user.getName());
                for (int j = 0 ; j < 5 ; j++){
                    Todo todo = new Todo("To-do "+j,"Description "+j,j,user.getId());
                    todoDao.insert(todo);
                }
            }
            return null;
        }
    }
}
