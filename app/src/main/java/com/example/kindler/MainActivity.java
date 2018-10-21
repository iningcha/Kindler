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
    }

    //go to bookList page
    public void seeBookList(View view){
        Intent intent = new Intent(this , BookListActivity.class);
        startActivity(intent);
    }

}
