package com.example.todolist.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnAdd = view.findViewById(R.id.btnAddTask);
        RecyclerView recycler = view.findViewById(R.id.recyclerTasks);

        btnAdd.setOnClickListener(v ->
                NavHostFragment.findNavController(TaskListFragment.this)
                        .navigate(R.id.action_taskList_to_addEditTask)
        );

        // TODO: load tasks into RecyclerView
    }
}
