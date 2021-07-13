package com.kronos.roomlivedataexample.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kronos.roomlivedataexample.R;
import com.kronos.roomlivedataexample.activities.adapter.TodoRecyclerAdapter;
import com.kronos.roomlivedataexample.activities.viewmodels.TodoViewModel;
import com.kronos.roomlivedataexample.model.Todo;
import com.kronos.roomlivedataexample.model.User;
import com.kronos.roomlivedataexample.model.UserTodo;

import java.util.List;

public class ListTodosFragment extends Fragment {

    private TodoViewModel todoViewModel;
    private User user;

    private RecyclerView recyclerView;
    private TodoRecyclerAdapter todoRecyclerAdapter;

    private FloatingActionButton fab;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        Bundle bundle = getArguments();
        user = (User) bundle.get("user");

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.setUser(user);
        todoViewModel.getTodosByUser().observe(getViewLifecycleOwner(), new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                todoRecyclerAdapter.submitList(todos);
            }
        });
    }

    public void initViews(View view){

        recyclerView = view.findViewById(R.id.recyclerTodos);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);

        todoRecyclerAdapter = new TodoRecyclerAdapter();
        recyclerView.setAdapter(todoRecyclerAdapter);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user);
                NavHostFragment.findNavController(ListTodosFragment.this)
                        .navigate(R.id.action_ListTodoFragment_to_addTodoFragment,bundle);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                todoViewModel.delete(todoRecyclerAdapter.getTodoAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        todoRecyclerAdapter.setOnItemClickListener(new TodoRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Todo todo) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("todo",todo);
                bundle.putSerializable("user",user);
                NavHostFragment.findNavController(ListTodosFragment.this).navigate(R.id.action_ListTodoFragment_to_addTodoFragment, bundle);
            }
        });

    }

}
