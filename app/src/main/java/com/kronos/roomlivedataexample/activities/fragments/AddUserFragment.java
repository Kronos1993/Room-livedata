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
import com.kronos.roomlivedataexample.activities.viewmodels.UserViewModel;
import com.kronos.roomlivedataexample.model.Todo;
import com.kronos.roomlivedataexample.model.User;

public class AddUserFragment extends Fragment {

    private EditText editTextUserName,editTextCi;
    private UserViewModel userViewModel;
    private Button buttonSave,buttonCancel;
    private User user;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*arguments?.getString("username")*/
        //get argument from navigation
        Bundle bundle = getArguments();
        if (bundle.get("user")!=null){
            user = (User) bundle.get("user");
        }
        initViews(view);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    private void initViews(View view){
        editTextUserName = view.findViewById(R.id.editTextUserName);
        editTextCi = view.findViewById(R.id.editTextCi);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(view1 -> saveUser());
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(view1->cancel());

        if ( user != null){
            editTextUserName.setText(user.getName());
            editTextCi.setText(user.getCi());
        }

    }

    private void saveUser(){
        if (editTextUserName.getText().toString().trim().isEmpty() || editTextCi.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Empty fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(user == null)
            userViewModel.insert(new User(editTextUserName.getText().toString(),editTextCi.getText().toString()));
        else{
            user.setName(editTextUserName.getText().toString());
            user.setCi(editTextCi.getText().toString());
            userViewModel.update(user);
        }

        NavHostFragment.findNavController(AddUserFragment.this)
                .navigate(R.id.action_addUserFragment_to_homeFragment);

    }

    private void cancel(){
        NavHostFragment.findNavController(AddUserFragment.this)
                .navigate(R.id.action_addUserFragment_to_homeFragment);
    }
}
