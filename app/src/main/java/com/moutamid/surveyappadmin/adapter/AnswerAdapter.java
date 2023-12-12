package com.moutamid.surveyappadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.surveyappadmin.R;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ChatListVH> {
    Context context;
    ArrayList<String> list;
    ArrayList<String> answer;

    public AnswerAdapter(Context context, ArrayList<String> list, ArrayList<String> answer) {
        this.context = context;
        this.list = list;
        this.answer = answer;
    }

    @NonNull
    @Override
    public ChatListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListVH(LayoutInflater.from(context).inflate(R.layout.message_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListVH holder, int position)
    {
        if (position < list.size() && position < answer.size()) {
            String s = list.get(position);
            String answers = answer.get(position);

            // Check for null values
            if (s != null) {
                holder.name.setText(s);
            } else {
                holder.name.setText("N/A");
            }

            if (answers != null) {
                holder.answer.setText(answers);
            } else {
                holder.answer.setText("N/A");
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ChatListVH extends RecyclerView.ViewHolder {
        TextView name, answer;

        public ChatListVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
        }
    }

}
