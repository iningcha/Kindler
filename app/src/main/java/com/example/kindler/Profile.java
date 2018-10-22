package com.example.kindler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView profilePicture = findViewById(R.id.profilePicture);
        TextView name = findViewById(R.id.name);
        TextView biography = findViewById(R.id.biography);

        profilePicture.setImageResource(R.drawable.test);
        name.setText("Samantha Chang");
        biography.setText("Student studying CS at USC");

    }


}
