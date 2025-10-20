package com.example.todolist.ui.main;

import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.todolist.R;
import com.example.todolist.data.Priority;
import com.example.todolist.data.Task;
import com.example.todolist.viewmodel.TaskViewModel;
import com.google.firebase.auth.FirebaseAuth;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditTaskFragment extends Fragment {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private TaskViewModel taskViewModel;
    private Spinner prioritySpinner;
    private Date dueDate;
    private int taskId = -1; //Default new Task

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

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
        prioritySpinner = view.findViewById(R.id.spinner_priority);
        Button dueDateButton = view.findViewById(R.id.button_due_date);

        //Setup Priority Spinner
        ArrayAdapter<Priority> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, Priority.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);

        if (getArguments() != null) {
            taskId = AddEditTaskFragmentArgs.fromBundle(getArguments()).getTaskId();
            titleEditText.setText(AddEditTaskFragmentArgs.fromBundle(getArguments()).getTaskTitle());
            descriptionEditText.setText(AddEditTaskFragmentArgs.fromBundle(getArguments()).getTaskDescription());
        }

        //Due Date picker
        dueDateButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(requireContext(),
                    (view1, year, month, day) -> {
                        Calendar selected = Calendar.getInstance();
                        selected.set(year, month, day);
                        dueDate = selected.getTime();
                        dueDateButton.setText("Due: " + dateFormat.format(dueDate));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000); // Prevent past dates
            dialog.show();
        });

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Priority priority;

            switch (prioritySpinner.getSelectedItemPosition()) {
                case 0:
                    priority = Priority.HIGH;
                    break;
                case 1:
                    priority = Priority.AVERAGE;
                    break;
                default:
                    priority = Priority.LOW;
            }
            //task creation
            if (taskId == -1) {
                // New task
                if (title.isEmpty()) {
                    titleEditText.setError("Title is required");
                    return;
                }
                //if due date not set, set it to tomorrow
                if (dueDate == null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    dueDate = calendar.getTime();
                }
                Task newTask = new Task(title, description, userId, priority, dueDate);
                taskViewModel.insert(newTask);
            } else {
                // Update existing task
                Task ediTask = new Task(title, description, userId, priority,dueDate);
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
