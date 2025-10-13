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
import androidx.navigation.fragment.NavHostFragment;

import com.example.todolist.R;

public class AddEditTaskFragment extends Fragment {
    private EditText titleEditText;
    private EditText descriptionEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_edit_task, container, false);

        titleEditText = view.findViewById(R.id.edit_text_title);
        descriptionEditText = view.findViewById(R.id.edit_text_description);
        Button saveButton = view.findViewById(R.id.button_save);
        Button cancelButton = view.findViewById(R.id.button_cancel);

        if (getArguments() != null) {
            int taskId = AddEditTaskFragmentArgs.fromBundle(getArguments()).getTaskId();
            titleEditText.setText(AddEditTaskFragmentArgs.fromBundle(getArguments()).getTaskTitle());
            descriptionEditText.setText(AddEditTaskFragmentArgs.fromBundle(getArguments()).getTaskDescription());
        }

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            // TODO: save task to database or ViewModel
            // If taskId == -1 → new task; else → update existing

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_addEditTask_to_taskList);
        });

        // CANCEL button → just go back
        cancelButton.setOnClickListener(v ->
                NavHostFragment.findNavController(this).popBackStack()
        );
        return view;
    }
}
