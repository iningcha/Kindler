package com.example.kindler;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import Database.User;
import Database.UserViewModel;

public class Profile extends AppCompatActivity {

    private TextView name;
    private ImageView profilePicture;
    private TextView biography;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePicture = findViewById(R.id.profilePicture);
        name = findViewById(R.id.name);
        biography = findViewById(R.id.biography);

        //populate by getting the data from the database

        profilePicture.setImageResource(R.drawable.test);
        name.setText("Samantha Chang");
        biography.setText("Student studying CS at USC");

    }


}
