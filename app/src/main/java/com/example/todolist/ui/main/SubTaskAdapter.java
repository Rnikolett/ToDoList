package com.example.todolist.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todolist.R;
import com.example.todolist.data.SubTask;
import java.util.ArrayList;
import java.util.List;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.ViewHolder> {
    private List<SubTask> subTasks = new ArrayList<>();
    private final OnSubTaskInteractionListener listener;

    public interface OnSubTaskInteractionListener {
        void onSubTaskChecked(SubTask subTask, boolean isChecked);
        void onSubTaskDeleted(SubTask subTask);
    }

    public SubTaskAdapter(OnSubTaskInteractionListener listener) {
        this.listener = listener;
    }

    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subtask, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubTask subTask = subTasks.get(position);
        holder.checkBox.setText(subTask.getTitle());
        holder.checkBox.setChecked(subTask.isCompleted());
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                listener.onSubTaskChecked(subTask, isChecked)
        );
        holder.deleteButton.setOnClickListener(v -> listener.onSubTaskDeleted(subTask));
    }

    @Override
    public int getItemCount() {
        return subTasks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageButton deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox_subtask);
            deleteButton = itemView.findViewById(R.id.button_delete_subtask);
        }
    }
}
