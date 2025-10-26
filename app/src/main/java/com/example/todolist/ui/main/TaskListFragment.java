package com.example.todolist.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.example.todolist.viewmodel.TaskViewModel;

public class TaskListFragment extends Fragment {
    private TaskViewModel taskViewModel;

    public TaskListFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_task_list, container, false);

        Button addButton = view.findViewById(R.id.btnAddTask);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerTasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setHasFixedSize(true);


        // ViewModel setup
        taskViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())
                .create(TaskViewModel.class);

        TaskAdapter adapter = new TaskAdapter(taskViewModel, getViewLifecycleOwner());
        recyclerView.setAdapter(adapter);
        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), adapter::setTasks);

        //Creates a new Task with default values
        addButton.setOnClickListener(v -> {
            NavDirections action = TaskListFragmentDirections.actionTaskListToAddEditTask();
            NavHostFragment.findNavController(this).navigate(action);
        });

        // When user taps a task, navigate to edit it
        adapter.setOnItemClickListener(task -> {
            NavDirections action = TaskListFragmentDirections
                    .actionTaskListToAddEditTask()
                    .setTaskId(task.getId())
                    .setTaskTitle(task.getTitle())
                    .setTaskDescription(task.getDescription())
                    .setDueDate(task.getDueDate().getTime());
            NavHostFragment.findNavController(this).navigate(action);
        });
        //When user taps the delete button, delete the task
        adapter.setOnDeleteClickListener(task -> {taskViewModel.delete(task);
        });

        return view;
    }
   }
