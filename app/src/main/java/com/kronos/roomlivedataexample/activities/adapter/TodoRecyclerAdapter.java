package com.kronos.roomlivedataexample.activities.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.kronos.roomlivedataexample.R;
import com.kronos.roomlivedataexample.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoRecyclerAdapter extends ListAdapter<Todo,TodoRecyclerAdapter.TodoViewHolder> {

    private OnItemClickListener onItemClickListener;

    public TodoRecyclerAdapter() {
        super(diffCallback);
    }

    private static final DiffUtil.ItemCallback<Todo> diffCallback = new DiffUtil.ItemCallback<Todo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row,parent,false);
        return new TodoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = getItem(position);
        holder.textViewTitle.setText(todo.getTitle());
        holder.textViewDescription.setText(todo.getDescription());
        holder.textViewPriority.setText(String.valueOf(todo.getPriority()));
    }

    public Todo getTodoAt(int position) {
        return getItem(position);
    }

    class TodoViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle,textViewDescription,textViewPriority;

        public TodoViewHolder(View view){
            super(view);
            textViewTitle = view.findViewById(R.id.textViewTitle);
            textViewDescription = view.findViewById(R.id.textViewDescription);
            textViewPriority = view.findViewById(R.id.textViewPriority);

            view.setOnClickListener(view1 -> {
                int pos = getAdapterPosition();
                if (onItemClickListener != null && pos != RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(getItem(pos));
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Todo todo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
