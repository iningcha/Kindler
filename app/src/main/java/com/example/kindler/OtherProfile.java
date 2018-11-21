package com.example.kindler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import Database.User;
import Database.UserViewModel;

import static com.example.kindler.Profile.decodeBase64;

public class OtherProfile extends AppCompatActivity implements View.OnClickListener{
    private UserViewModel mUserViewModel;
    private int mProfileOwnerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        ImageView profilePicture = findViewById(R.id.profilePicture);
        TextView name = findViewById(R.id.otherName);
        TextView biography = findViewById(R.id.otherBiography);
        Button wish = findViewById(R.id.otherWishListButton);
        Button book = findViewById(R.id.otherBookListButton);
        Database.Profile p = mUserViewModel.getCurrProfile();

        String username = getIntent().getStringExtra("USER");
        if (username != null && !username.equals("")) {
            User u = mUserViewModel.getUser(username);
            mProfileOwnerID = u.getUserId();
            p = u.getProfile();
        }

        //set image
        String imageStr = p.getProfilePicture();
        if (imageStr.length()>0) {
            profilePicture.setImageBitmap(decodeBase64(imageStr));
        }else {
            profilePicture.setImageResource(R.drawable.test);
        }

        //set text for name and bio
        name.setText(p.getProfileName());
        biography.setText(p.getProfileBiography());

        //set onclick listener for booklist / wishlist
        wish.setOnClickListener(this);
        book.setOnClickListener(this);


    }

    @Override
    public void onClick(View v){
        Intent intent = null;

        //create intent to correct list
        switch(v.getId()){
            case R.id.otherWishListButton:
                intent = new Intent(this,OtherWishList.class);
                break;
            case R.id.otherBookListButton:
                intent = new Intent(this, OtherBookList.class);
                break;
        }

        //add owner ID to intent and start intent
        if(intent != null){
            intent.putExtra("ownerID",mProfileOwnerID);
            startActivity(intent);
        }
    }
}
