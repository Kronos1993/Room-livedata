package com.kronos.roomlivedataexample.model.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.kronos.roomlivedataexample.model.Todo;
import com.kronos.roomlivedataexample.model.UserTodo;
import com.kronos.roomlivedataexample.model.dao.TodoDao;
import com.kronos.roomlivedataexample.model.dao.TodoDatabase;
import java.util.List;

public class TodoRepository {

    private TodoDao todoDao;
    private LiveData<List<Todo>> todos;
    private LiveData<List<UserTodo>> todosByUser;

    public TodoRepository(Application application){
        TodoDatabase todoDatabase = TodoDatabase.getInstance(application);
        todoDao = todoDatabase.getTodoDao();
        todos = todoDao.findAll();
    }

    public void insert(Todo todo){
        new InsertAsyncTask(todoDao).execute(todo);
    }

    public void update(Todo todo){
        new UpdateAsyncTask(todoDao).execute(todo);
    }

    public void delete(Todo todo){
        new DeleteAsyncTask(todoDao).execute(todo);
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(todoDao).execute();
    }

    public LiveData<List<Todo>> getTodos() {
        return todos;
    }

    public LiveData<List<Todo>> getTodosByUser(long user_id) {
        return todoDao.findAllByUserId(user_id);
    }



    private static class InsertAsyncTask extends AsyncTask<Todo,Void,Void>{

        private TodoDao todoDao;

        private InsertAsyncTask (TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.insert(todos[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Todo,Void,Void>{
        private TodoDao todoDao;

        private UpdateAsyncTask (TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.update(todos[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Todo,Void,Void>{

        private TodoDao todoDao;

        private DeleteAsyncTask (TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Todo... todos) {
            todoDao.delete(todos[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{

        private TodoDao todoDao;

        private DeleteAllAsyncTask (TodoDao todoDao){
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.deleteAll();
            return null;
        }
    }
}
