package com.example.kindler;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import Database.UserViewModel;

public class Profile extends AppCompatActivity {

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        ImageView profilePicture = findViewById(R.id.profilePicture);
        TextView name = findViewById(R.id.name);
        TextView biography = findViewById(R.id.biography);
        /*
        profilePicture.setImageResource(R.drawable.test);
        name.setText("S");
        biography.setText("Something");
        */

        profilePicture.setImageResource(R.drawable.test);
        name.setText(mUserViewModel.getCurrProfile().getProfileName());
        biography.setText(mUserViewModel.getCurrProfile().getProfileBiography());


    }


}
