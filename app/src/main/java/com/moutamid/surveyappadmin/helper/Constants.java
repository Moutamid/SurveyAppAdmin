package com.moutamid.surveyappadmin.helper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Constants {
    public static String db_path = "SurveyResponses";


    public static DatabaseReference UserReference = FirebaseDatabase.getInstance().getReference(db_path);
  }
