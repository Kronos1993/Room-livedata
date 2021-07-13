package com.kronos.roomlivedataexample.activities.fragments;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kronos.roomlivedataexample.R;
import com.kronos.roomlivedataexample.activities.adapter.UserRecyclerAdapter;
import com.kronos.roomlivedataexample.activities.viewmodels.UserViewModel;
import com.kronos.roomlivedataexample.model.UserTodo;

import java.util.List;

public class ListUserFragment extends Fragment {

    private UserViewModel userViewModel;

    private RecyclerView recyclerView;
    private UserRecyclerAdapter userRecyclerAdapter;

    private FloatingActionButton fab;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<UserTodo>>() {
            @Override
            public void onChanged(List<UserTodo> users) {
                userRecyclerAdapter.submitList(users);
            }
        });
    }

    public void initViews(View view){

        recyclerView = view.findViewById(R.id.recyclerUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        userRecyclerAdapter = new UserRecyclerAdapter();
        recyclerView.setAdapter(userRecyclerAdapter);

        fab = view.findViewById(R.id.fab_add_user);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ListUserFragment.this)
                        .navigate(R.id.action_homeFragment_to_addUserFragment);

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                userViewModel.delete(userRecyclerAdapter.getUserAt(viewHolder.getAdapterPosition()).getUser());
            }
        }).attachToRecyclerView(recyclerView);

        userRecyclerAdapter.setOnItemClickListener(new UserRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserTodo user) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user.getUser());
                NavHostFragment.findNavController(ListUserFragment.this).navigate(R.id.action_homeFragment_to_ListTodoFragmentFragment, bundle);
            }
        });

        userRecyclerAdapter.setOnLongItemClickListener(new UserRecyclerAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(UserTodo user) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",user.getUser());
                NavHostFragment.findNavController(ListUserFragment.this).navigate(R.id.action_homeFragment_to_addUserFragment, bundle);
            }
        });

    }

}
