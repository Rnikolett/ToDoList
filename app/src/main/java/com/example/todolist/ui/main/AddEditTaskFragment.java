package com.example.todolist.ui.main;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.todolist.R;

public class AddEditTaskFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText etTitle = view.findViewById(R.id.etTaskTitle);
        EditText etDesc  = view.findViewById(R.id.etTaskDescription);
        Button btnSave   = view.findViewById(R.id.btnSaveTask);

        btnSave.setOnClickListener(v -> {
            // TODO: save to database or ViewModel
           // requireActivity().onBackPressedDispatcher().onBackPressed();
        });
    }
}
