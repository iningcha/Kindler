package com.example.kindler;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Database.Book;
import Database.User;
import Database.UserViewModel;

public class ExchangeActivity extends AppCompatActivity {
    int mOwnerId;
    int mOwnerBookId;
    int mOtherOwnerId;
    int mOtherOwnerBookId;

    ImageView mLeftBookView;
    ImageView mRightBookView;
    ImageView mLeftBookOwnerView;
    ImageView mRightBookOwnerView;
    TextView mLeftBookOwnerName;
    TextView mRightBookOwnerName;
    Button mExchangeButton;

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mOwnerId = getIntent().getIntExtra("ownerId", -1);
        mOwnerBookId = getIntent().getIntExtra("ownerBookId", -1);
        mOtherOwnerId = getIntent().getIntExtra("otherOwnerId", -1);
        mOtherOwnerBookId = getIntent().getIntExtra("otherOwnerBookId", -1);


        final User owner = mUserViewModel.getUserById(mOwnerId);
        final Book ownerBook = mUserViewModel.getBook(mOwnerBookId);
        final User other = mUserViewModel.getUserById(mOtherOwnerId);
        final Book otherBook = mUserViewModel.getBook(mOtherOwnerBookId);

        Log.d("owner:", owner.getProfile().getProfileName());
        Log.d("ownerBook:", ownerBook.getBookName());
        Log.d("other:", other.getProfile().getProfileName());
        Log.d("otherBook:", otherBook.getBookName());

        mLeftBookView = findViewById(R.id.leftBookView);
        mLeftBookOwnerView = findViewById(R.id.leftBookOwner);
        mLeftBookOwnerName = findViewById(R.id.leftBookOwnerName);

        Picasso.with(this).load(mUserViewModel.getBook(mOwnerBookId).getBookPic()).into(mLeftBookView);
        if (decodeBase64(owner.getProfile().getProfilePicture()) != null) {
            mLeftBookOwnerView.setImageBitmap(decodeBase64(owner.getProfile().getProfilePicture()));
        }else {
            mLeftBookOwnerView.setImageResource(R.drawable.test);
        }
        mLeftBookOwnerName.setText(mUserViewModel.getUserById(mOwnerId).getProfile().getProfileName());

        mRightBookView = findViewById(R.id.rightBookView);
        mRightBookOwnerView = findViewById(R.id.rightBookOwner);
        mRightBookOwnerName = findViewById(R.id.rightBookOwnerName);

        Picasso.with(this).load(mUserViewModel.getBook(mOtherOwnerBookId).getBookPic()).into(mRightBookView);
        if (decodeBase64(other.getProfile().getProfilePicture()) != null) {
            mRightBookOwnerView.setImageBitmap(decodeBase64(other.getProfile().getProfilePicture()));
        }else {
            mRightBookOwnerView.setImageResource(R.drawable.test);
        }
        mRightBookOwnerName.setText(mUserViewModel.getUserById(mOtherOwnerId).getProfile().getProfileName());

        mExchangeButton = findViewById(R.id.exchangeButton);
        mExchangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                owner.removeOwnedList(ownerBook.getBookId());
                ownerBook.removeOwnedUser(owner.getUserId());
                other.removeOwnedList(otherBook.getBookId());
                otherBook.removeOwnedUser(other.getUserId());
                owner.addOwnedList(otherBook.getBookId());
                otherBook.addOwnedUser(owner.getUserId());
                other.addOwnedList(ownerBook.getBookId());
                ownerBook.addOwnedUser(other.getUserId());
            }
        });
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
