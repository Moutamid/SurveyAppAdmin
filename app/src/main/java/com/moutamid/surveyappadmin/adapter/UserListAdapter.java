package com.moutamid.surveyappadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.surveyappadmin.AnswersActivity;
import com.moutamid.surveyappadmin.R;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ChatListVH> {
    Context context;
    ArrayList<String> list;
    String type;
    public UserListAdapter(Context context, ArrayList<String> list, String type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @NonNull
    @Override
    public ChatListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListVH(LayoutInflater.from(context).inflate(R.layout.answers_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListVH holder, int position) {
        String s = list.get(holder.getAdapterPosition());
        String[] parts = s.split("___");
        if (parts.length >= 2) {
            String result = parts[1];
            holder.name.setText(s);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AnswersActivity.class);
                intent.putExtra("name", s);
                intent.putExtra("type", type);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChatListVH extends RecyclerView.ViewHolder {
        TextView name;

        public ChatListVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }

}
