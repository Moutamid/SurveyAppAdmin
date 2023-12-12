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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(AnswersActivity.this));
        recyclerView.setHasFixedSize(false);
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        String name = getIntent().getStringExtra("name");
        getData(name);

    }

    private void getData(String name) {
        Dialog lodingbar = new Dialog(AnswersActivity.this);

        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        Constants.Abschlussfragebogen.child(name)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            questions.clear();
                            answers.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String questionText = dataSnapshot.child("questionText").getValue().toString();
                                String selectedOptionText = dataSnapshot.child("selectedOptionText").getValue().toString();
                                if (!questions.contains(questionText))
                                {
                                    questions.add(questionText);
                                }
                                if (!answers.contains(selectedOptionText))
                                {
                                    answers.add(selectedOptionText);
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