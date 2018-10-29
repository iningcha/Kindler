package com.example.kindler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;


public class MatchDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        Intent intent = getIntent();
        String matchUser = intent.getStringExtra("matchUser");
        String bookName = intent.getStringExtra("bookName");
        int image = intent.getIntExtra("imageName", 0);

        TextView matchDetail_userName = findViewById(R.id.matchDetail_userName);
        TextView matchDetail_bookName = findViewById(R.id.matchDetail_bookName);
        ImageView matchDetail_profilePicture = findViewById(R.id.matchDetail_profilePicture);


        matchDetail_userName.setText(matchUser);
        matchDetail_bookName.setText(bookName);
        matchDetail_profilePicture.setImageResource(image);


    }
}
