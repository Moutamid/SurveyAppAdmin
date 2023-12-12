package com.moutamid.surveyappadmin;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.surveyappadmin.adapter.AnswerAdapter;
import com.moutamid.surveyappadmin.helper.Constants;

import java.util.ArrayList;
import java.util.Objects;

public class AnswersActivity extends AppCompatActivity {
    ArrayList<String> questions;
    ArrayList<String> answers;
    RecyclerView recyclerView;
    String type;
    TextView comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        comments = findViewById(R.id.comments);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(AnswersActivity.this));
        recyclerView.setHasFixedSize(false);
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        String name = getIntent().getStringExtra("name");
        type = Stash.getString("survey_type");
        getData(name, type);

    }

    private void getData(String name, String type) {
        Dialog lodingbar = new Dialog(AnswersActivity.this);

        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        Constants.UserReference.child(type).child(name)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            questions.clear();
                            answers.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.child("questionText").exists()) {
                                    Log.d("dataa", dataSnapshot.toString());
//                                if (dataSnapshot.child("questionText").exists()) {
                                    String questionText = dataSnapshot.child("questionText").getValue().toString();
                                    String selectedOptionText = dataSnapshot.child("selectedOptionText").getValue().toString();
                                    questions.add(questionText);
                                    answers.add(selectedOptionText);
                                }

                                if(dataSnapshot.child("comments").exists())
                                {
                                    comments.setText(dataSnapshot.child("comments").getValue().toString());
                                }
                            }
                        }
                        lodingbar.dismiss();
                        AnswerAdapter adapter = new AnswerAdapter(AnswersActivity.this, questions, answers);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        lodingbar.dismiss();
                        Toast.makeText(AnswersActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}