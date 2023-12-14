package com.moutamid.surveyappadmin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.surveyappadmin.AnswersActivity;
import com.moutamid.surveyappadmin.R;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ChatListVH> {
    Context context;
    ArrayList<String> list;
    public UserListAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
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
            holder.name.setText(result);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AnswersActivity.class);
                intent.putExtra("name", s);
                Toast.makeText(context, "name"+ list.get(holder.getAdapterPosition())+"  "+s, Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDeleteConfirmationDialog(holder.getAdapterPosition());
                return false;
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
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to delete this item?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Yes button
                        removeItem(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked No button
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    private void removeItem(int position) {
        String itemId = list.get(position); // Adjust this based on your data model
        removeItemFromFirebase(itemId);

        // Remove the item from the local data list
        list.remove(position);

        // Notify the adapter about the item removal
        notifyItemRemoved(position);
    }
    private void removeItemFromFirebase(String itemId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SurveyResponses").child(Stash.getString("survey_type")).child(itemId);
        databaseReference.removeValue();
    }

}
