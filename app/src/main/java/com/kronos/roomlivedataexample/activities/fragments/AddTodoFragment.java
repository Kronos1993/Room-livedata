package com.kronos.roomlivedataexample.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.kronos.roomlivedataexample.R;
import com.kronos.roomlivedataexample.activities.viewmodels.TodoViewModel;
import com.kronos.roomlivedataexample.model.Todo;
import com.kronos.roomlivedataexample.model.User;

import me.angrybyte.numberpicker.view.ActualNumberPicker;

public class AddTodoFragment extends Fragment {

    private EditText editTextTitle,editTextDescription;
    private ActualNumberPicker numberPickerPriority;
    private TodoViewModel todoViewModel;
    private Button buttonSave,buttonCancel;
    private Todo todo;
    private User user;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_todo, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*arguments?.getString("username")*/
        //get argument from navigation
        Bundle bundle = getArguments();
        if (bundle.get("todo")!=null){
            todo = (Todo) bundle.get("todo");
        }
        user = (User) bundle.get("user");
        initViews(view);
        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.setUser(user);
    }

    private void initViews(View view){
        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        numberPickerPriority = view.findViewById(R.id.numberPickerPriority);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(view1 -> saveTodo());
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(view1->cancel());
        numberPickerPriority.setMaxValue(10);
        numberPickerPriority.setMinValue(0);

        if ( todo != null){
            editTextDescription.setText(todo.getDescription());
            editTextTitle.setText(todo.getTitle());
            numberPickerPriority.setValue(todo.getPriority());
        }

    }

    private void saveTodo(){
        if (editTextDescription.getText().toString().trim().isEmpty() || editTextTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Empty fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if(todo == null)
                todoViewModel.insert(new Todo(editTextTitle.getText().toString(),editTextDescription.getText().toString(),numberPickerPriority.getValue(),user.getId()));
            else{
                todo.setTitle(editTextTitle.getText().toString());
                todo.setDescription(editTextDescription.getText().toString());
                todo.setPriority(numberPickerPriority.getValue());
                todoViewModel.update(todo);
            }

            Bundle bundle = new Bundle();
            bundle.putSerializable("user",user);

            NavHostFragment.findNavController(AddTodoFragment.this).navigate(R.id.action_addFragment_to_listTodoFragment,bundle);

        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cancel(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        NavHostFragment.findNavController(AddTodoFragment.this)
                .navigate(R.id.action_addFragment_to_listTodoFragment,bundle);
    }
}
