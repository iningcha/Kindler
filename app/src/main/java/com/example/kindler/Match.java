package com.example.kindler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.*;


public class Match extends AppCompatActivity {

    int sampleImage = R.drawable.test;

    String[] bookIDs = {"Harry Potter", "A Wrinkle in Time", "1984", "Animal Farm", "Gone Girl", "To All the Boys I've Loved Before", "Leen In"};

    String[] userId = {"User1", "User2", "User3", "User4", "User5", "User6", "User7"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        final ListView matchList = findViewById(R.id.matchList);

        CustomAdapter customAdapter = new CustomAdapter();

        matchList.setAdapter(customAdapter);

        matchList.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){

                Intent intent = new Intent(Match.this, MatchDetail.class);
                String matchUser = userId[position];
                String bookName = bookIDs[position];
                intent.putExtra("matchUser", matchUser);
                intent.putExtra("bookName", bookName);
                intent.putExtra("image", Integer.toString(sampleImage));

                startActivity(intent);

            }

        });

    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount(){
            return userId.length;
        }

        @Override
        public Object getItem(int i){
            return null;
        }

        @Override
        public long getItemId(int i){
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup){
            view = getLayoutInflater().inflate(R.layout.customlist, null);
            ImageView matchImage = view.findViewById(R.id.matchImage);
            TextView matchUserId = view.findViewById(R.id.matchUserId);
            TextView matchBookTitle = view.findViewById(R.id.matchBookTitle);

            matchImage.setImageResource(sampleImage);
            matchUserId.setText(userId[i]);
            matchBookTitle.setText(bookIDs[i]);

            return view;
        }


    }


}
