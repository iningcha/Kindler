package com.example.kindler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
    }

    public void seeMatch(View view){
        Intent intent = new Intent(this, Match.class);
        startActivity(intent);
    }

    public void seeMatchDetail(View view){
        Intent intent = new Intent(this, BookListActivity.class);
        startActivity(intent);
    }

    public void seeProfile(View view){
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
    }

    public void seeSearch(View view){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void seeDiscover(View view){
        Intent intent = new Intent(this,DiscoverActivity.class);
        startActivity(intent);
    }
}
