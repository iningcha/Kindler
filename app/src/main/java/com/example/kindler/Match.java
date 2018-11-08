package com.example.kindler;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Database;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.*;

import java.util.ArrayList;
import java.util.List;

import Database.UserViewModel;
import Database.MatchRepository;
import Database.User;


public class Match extends AppCompatActivity {

    private UserViewModel mUserViewModel;
    private List<Integer> mMatchList;
    private MatchRepository mMatchRepository;
    private List<String> bookTitles;
    private List<String> userNames;

//    String[] bookTitles = {"Harry Potter", "A Wrinkle in Time", "1984", "Animal Farm", "Gone Girl", "To All the Boys I've Loved Before"};
//    String[] userNames = {"User1", "User2", "User3", "User4", "User5", "User6"};

    int[] images = new int[6];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        final ListView matchList = findViewById(R.id.matchList);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mMatchList = mUserViewModel.getMatches();

        Log.d("@@@@CHECKMATCHLIST", Integer.toString(mMatchList.size()));

        for(int m: mMatchList) {
            bookTitles.add(mUserViewModel.getBook(mUserViewModel.getMatchById(m).getMatchBookId()).getBookName());
            userNames.add(mUserViewModel.getUserById(mUserViewModel.getMatchById(m).getMatchWisher()).getUsername());
        }

        images[0] = R.drawable.harry;
        images[1] = R.drawable.wrinkle;
        images[2] = R.drawable.orwell;
        images[3] = R.drawable.animal;
        images[4] = R.drawable.gone;
        images[5] = R.drawable.to;

        //getting the bookID, userId, and images from the database

        CustomAdapter customAdapter = new CustomAdapter();

        matchList.setAdapter(customAdapter);

        matchList.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){

            Intent intent = new Intent(Match.this, MatchDetail.class);
            String matchUser = userNames.get(position);
            String bookName = bookTitles.get(position);
            int imageName = images[position];
            intent.putExtra("matchUser", matchUser);
            intent.putExtra("bookName", bookName);
            intent.putExtra("image", imageName);

            startActivity(intent);

            }

        });

    }

    public int getMatchListSize(){
        return bookTitles.size();
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount(){
            return bookTitles.size();
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
            ImageView matchImage = view.findViewById(R.id.searchMatchImage);
            TextView matchUserId = view.findViewById(R.id.searchMatchUserId);
            TextView matchBookTitle = view.findViewById(R.id.matchBookTitle);

            matchImage.setImageResource(images[i]);
            matchUserId.setText(userNames.get(i));
            matchBookTitle.setText(bookTitles.get(i));

            return view;
        }


    }


}
