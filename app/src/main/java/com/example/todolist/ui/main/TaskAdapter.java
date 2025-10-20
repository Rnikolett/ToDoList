package com.example.todolist.ui.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.data.Priority;
import com.example.todolist.data.Task;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private List<Task> tasks = new ArrayList<>();
    private OnItemClickListener listener;
    private OnDeleteClickListener deleteListener;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task currentTask = tasks.get(position);
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
        holder.textDueDate.setText(dateFormat.format(currentTask.getDueDate()));
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


        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            deleteButton = itemView.findViewById(R.id.button_delete);
            textDueDate = itemView.findViewById(R.id.textDueDate);
            textPriority = itemView.findViewById(R.id.textPriority);


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
