package com.example.kindler;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;


public class MatchDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        Intent intent = getIntent();
        final String matchUser = intent.getStringExtra("matchUser");
        String bookName = intent.getStringExtra("bookName");
        int image = intent.getIntExtra("imageName", 0);

        TextView matchDetail_userName = findViewById(R.id.matchDetail_userName);
        TextView matchDetail_bookName = findViewById(R.id.matchDetail_bookName);
        ImageView matchDetail_profilePicture = findViewById(R.id.matchDetail_profilePicture);
        Button exchangeButton = findViewById(R.id.messageButton);

        matchDetail_userName.setText(matchUser);
        matchDetail_bookName.setText(bookName);
        matchDetail_profilePicture.setImageResource(image);
        exchangeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("userId", matchUser);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Copied User Email to Clipboard!" , Toast.LENGTH_SHORT ).show();
            }
        });

    }

}
