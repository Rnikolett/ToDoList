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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.todolist.R;
import com.example.todolist.data.Task;
import com.example.todolist.viewmodel.TaskViewModel;

public class AddEditTaskFragment extends Fragment {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private TaskViewModel taskViewModel;
    private int taskId = -1; //Default new Task

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_edit_task, container, false);
        taskViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
                .create(TaskViewModel.class);

        titleEditText = view.findViewById(R.id.edit_text_title);
        descriptionEditText = view.findViewById(R.id.edit_text_description);
        Button saveButton = view.findViewById(R.id.button_save);
        Button cancelButton = view.findViewById(R.id.button_cancel);

        if (getArguments() != null) {
            taskId = AddEditTaskFragmentArgs.fromBundle(getArguments()).getTaskId();
            titleEditText.setText(AddEditTaskFragmentArgs.fromBundle(getArguments()).getTaskTitle());
            descriptionEditText.setText(AddEditTaskFragmentArgs.fromBundle(getArguments()).getTaskDescription());
        }

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            if (taskId == -1) {
                // New task
                Task newTask = new Task(title, description);
                taskViewModel.insert(newTask);
            } else {
                // Update existing task
                Task ediTask = new Task(title, description);
                ediTask.setId(taskId);
                taskViewModel.update(ediTask);
            }

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
