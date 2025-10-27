package com.example.todolist.ui.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.data.Priority;
import com.example.todolist.data.Status;
import com.example.todolist.data.SubTask;
import com.example.todolist.data.Task;
import com.example.todolist.viewmodel.TaskViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private List<Task> tasks = new ArrayList<>();
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final TaskViewModel taskViewModel;
    private final LifecycleOwner lifecycleOwner;

    public TaskAdapter(TaskViewModel viewModel, LifecycleOwner owner) {
        this.taskViewModel = viewModel;
        this.lifecycleOwner = owner;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task currentTask = getTaskAt(position);
        holder.textViewTitle.setText(currentTask.getTitle());
        if (!currentTask.getDescription().isEmpty()){
            holder.textViewDescription.setText(currentTask.getDescription()); //only show description if not empty
        }
        holder.textPriority.setText(currentTask.getPriority().name());
        switch (currentTask.getPriority()){
            case HIGH:
                holder.textPriority.setTextColor(holder.itemView.getResources().getColor(R.color.red));
                break;
            case AVERAGE:
                holder.textPriority.setTextColor(holder.itemView.getResources().getColor(R.color.orange));
                break;
            case LOW:
                holder.textPriority.setTextColor(holder.itemView.getResources().getColor(R.color.green));
                break;
        }
        holder.completedCheckBox.setOnCheckedChangeListener(null); // avoid triggering on bind
        holder.textDueDate.setText(dateFormat.format(currentTask.getDueDate()));
        holder.completedCheckBox.setChecked(currentTask.getStatus() == Status.COMPLETED);

        // ✅ Handle checkbox toggle
        holder.completedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentTask.setStatus(Status.COMPLETED);
            } else {
                currentTask.setStatus(Status.PENDING);
            }
            taskViewModel.update(currentTask);

            // Update visuals instantly
            notifyItemChanged(holder.getAbsoluteAdapterPosition());
        });

        // Subtasks section
        SubTaskAdapter subTaskAdapter = new SubTaskAdapter(new SubTaskAdapter.OnSubTaskInteractionListener() {
            @Override
            public void onSubTaskChecked(SubTask subTask, boolean isChecked) {
                subTask.setCompleted(isChecked);
                taskViewModel.updateSubTask(subTask);
            }

            @Override
            public void onSubTaskDeleted(SubTask subTask) {
                taskViewModel.deleteSubTask(subTask);
            }
            @Override
            public void showEditSubTaskDialog(View view, SubTask subTask) {
                Context context = view.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit Subtask");

                final EditText input = new EditText(context);
                input.setText(subTask.getTitle());
                builder.setView(input);

                builder.setPositiveButton("Save", (dialog, which) -> {
                    String title = input.getText().toString().trim();
                    if (!title.isEmpty()) {
                        subTask.setTitle(title);
                        taskViewModel.updateSubTask(subTask);
                    }
                });

                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
            }
        });

        holder.recyclerSubTasks.setAdapter(subTaskAdapter);
        holder.recyclerSubTasks.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));

        taskViewModel.getSubTasksForTask(currentTask.getId())
                .observe(lifecycleOwner, subTaskAdapter::setSubTasks);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

   @SuppressLint("NotifyDataSetChanged")
   public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public Task getTaskAt(int position) {
        return tasks.get(position);
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDescription;
        private final ImageButton deleteButton;
        private final TextView textPriority;
        private final TextView textDueDate;
        private final RecyclerView recyclerSubTasks;
        private CheckBox completedCheckBox;


        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            deleteButton = itemView.findViewById(R.id.button_delete);
            textDueDate = itemView.findViewById(R.id.textDueDate);
            textPriority = itemView.findViewById(R.id.textPriority);
            recyclerSubTasks = itemView.findViewById(R.id.recyclerSubTasks);
            completedCheckBox = itemView.findViewById(R.id.checkbox_task_completed);


            itemView.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(tasks.get(position));
                }
            });

            deleteButton.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                if (deleteListener != null && position != RecyclerView.NO_POSITION) {
                    deleteListener.onDeleteClick(tasks.get(position));
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Task task);
    }
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteListener = listener;
    }
}
