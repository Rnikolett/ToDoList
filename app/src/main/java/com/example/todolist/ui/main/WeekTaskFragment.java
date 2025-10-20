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

public class WeekTaskFragment extends Fragment {
    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.week_task_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerWeek);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        Button backtoTaskListButton = view.findViewById(R.id.toTaskListButton);
        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(requireActivity()).get(TaskViewModel.class);
        taskViewModel.getTasksForThisWeek().observe(getViewLifecycleOwner(), adapter::setTasks);


        backtoTaskListButton.setOnClickListener(v->{
            NavDirections action = WeekTaskFragmentDirections.actionWeekListToTaskTask();
            NavHostFragment.findNavController(this).navigate(action);
        });
        return view;
    }
}
