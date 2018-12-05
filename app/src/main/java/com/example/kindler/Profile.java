package com.example.kindler;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;

import Database.User;
import Database.UserViewModel;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        Toolbar mtoolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        ImageView profilePicture = findViewById(R.id.ProfilePicture);
        TextView name = findViewById(R.id.Name);
        TextView biography = findViewById(R.id.Biography);
        Button wish = findViewById(R.id.WishListButton);
        Button book = findViewById(R.id.BookListButton);
        Button edit = findViewById(R.id.EditProfileButton);
        Database.Profile p = mUserViewModel.getCurrProfile();

        //set text for name and bio
        name.setText(p.getProfileName());
        biography.setText(p.getProfileBiography());

        String imageStr = p.getProfilePicture();

        wish.setOnClickListener(this);
        book.setOnClickListener(this);
        edit.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_search:
                Intent intentsearch = new Intent(this, Search.class);
                this.startActivity(intentsearch);
                break;

            case R.id.menu_home:
                this.finish();
                break;
            case R.id.menu_profile:

                break;
            case R.id.menu_booklist:
                this.finish();
                Intent intentbooklist = new Intent(this, BookListActivity.class);
                this.startActivity(intentbooklist);
                break;
            case R.id.menu_wishlist:
                this.finish();
                Intent intentwishlist = new Intent(this,WishListActivity.class);
                this.startActivity(intentwishlist);
                break;
            case R.id.menu_matches:
                this.finish();
                Intent intentmatches = new Intent(this, Match.class);
                this.startActivity(intentmatches);
                break;
            case R.id.menu_users:
                this.finish();
                Intent intentUsers = new Intent(this, UserListActivity.class);
                this.startActivity(intentUsers);
                break;
            case R.id.menu_logout:
                this.finish();
                Toast.makeText(getApplicationContext(), "Logged Out!" , Toast.LENGTH_SHORT ).show();
                Intent intentlogout = new Intent(this, LoginActivity.class);
                startActivity(intentlogout);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }


    @Override
    public void onClick(View v){
        Intent intent = null;

        //create intent to correct list
        switch(v.getId()){
            case R.id.WishListButton:
                intent = new Intent(this, WishListActivity.class);
                break;
            case R.id.BookListButton:
                intent = new Intent(this, BookListActivity.class);
                break;
            case R.id.EditProfileButton:
                intent = new Intent(this, EditProfileActivity.class);
                break;
        }

        startActivity(intent);
    }


    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
