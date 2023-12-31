package com.moutamid.surveyappadmin;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.surveyappadmin.adapter.UserListAdapter;
import com.moutamid.surveyappadmin.helper.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class UsersActivity extends AppCompatActivity {
    ArrayList<String> list;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(UsersActivity.this));
        recyclerView.setHasFixedSize(false);
        list = new ArrayList<>();
        getData();

    }

    private void getData() {
        Dialog lodingbar = new Dialog(UsersActivity.this);

        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        Constants.UserReference.child(Stash.getString("survey_type"))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            list.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    list.add(dataSnapshot.getKey());
                            }
                        }

                        lodingbar.dismiss();
                        UserListAdapter adapter = new UserListAdapter(UsersActivity.this, list);
                        recyclerView.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        lodingbar.dismiss();
                        Toast.makeText(UsersActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}