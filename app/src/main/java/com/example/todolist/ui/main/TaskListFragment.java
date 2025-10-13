package com.example.todolist.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;

public class TaskListFragment extends Fragment {
    public TaskListFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_task_list, container, false);

        Button addButton = view.findViewById(R.id.btnAddTask);
        addButton.setOnClickListener(v -> {
            NavDirections action = TaskListFragmentDirections.actionTaskListToAddEditTask(); // new task (default values)
            NavHostFragment.findNavController(this).navigate(action);
        });

        return view;
    }
   }
