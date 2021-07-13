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
import com.kronos.roomlivedataexample.model.User;
import com.kronos.roomlivedataexample.model.UserTodo;

public class UserRecyclerAdapter extends ListAdapter<UserTodo, UserRecyclerAdapter.UserViewHolder> {

    private OnItemClickListener onItemClickListener;
    private OnLongItemClickListener onLongItemClickListener;

    public UserRecyclerAdapter() {
        super(diffCallback);
    }

    private static final DiffUtil.ItemCallback<UserTodo> diffCallback = new DiffUtil.ItemCallback<UserTodo>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserTodo oldItem, @NonNull UserTodo newItem) {
            return oldItem.getUser().getId() == newItem.getUser().getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserTodo oldItem, @NonNull UserTodo newItem) {
            return oldItem.getUser().equals(newItem.getUser());
        }
    };

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row,parent,false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserTodo user = getItem(position);
        holder.textViewName.setText(user.getUser().getName());
        holder.textViewCi.setText(user.getUser().getCi());
    }

    public UserTodo getUserAt(int position) {
        return getItem(position);
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewName,textViewCi;

        public UserViewHolder(View view){
            super(view);
            textViewName = view.findViewById(R.id.textViewUserName);
            textViewCi = view.findViewById(R.id.textViewUserCi);

            view.setOnClickListener(view1 -> {
                int pos = getAdapterPosition();
                if (onItemClickListener != null && pos != RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(getItem(pos));
            });

            view.setOnLongClickListener(view1 -> {
                int pos = getAdapterPosition();
                if (onLongItemClickListener != null && pos != RecyclerView.NO_POSITION)
                    onLongItemClickListener.onLongItemClick(getItem(pos));
                return false;
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(UserTodo user);
    }

    public interface OnLongItemClickListener{
        void onLongItemClick(UserTodo user);
    }

    public void setOnLongItemClickListener(OnLongItemClickListener onLongItemClickListener){
        this.onLongItemClickListener = onLongItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
