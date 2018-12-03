package com.example.kindler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Database.User;
import Database.UserRepository;
import Database.UserViewModel;

public class UserListActivity extends AppCompatActivity {
    private ListView mUserListView;
    private UserListAdapter mUserListAdapter;
    private List<User> mUserList;
    private UserRepository mUserRepository;
    private UserViewModel mUserViewModel;
    private int mCurrUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUserRepository = ViewModelProviders.of(this).get(UserRepository.class);
        mUserList = mUserRepository.getAllUser();
        mCurrUserID = mUserViewModel.getCurrUserId();

        mUserListView = findViewById(R.id.usersListView);
        mUserListAdapter = new UserListAdapter(this, R.layout.customlist, new ArrayList<User>());
        mUserListView.setAdapter(mUserListAdapter);

        mUserListAdapter.clear();
        mUserListAdapter.addAll(mUserList);
    }

    class UserListAdapter extends ArrayAdapter<User> {
        public UserListAdapter(Context context, int resource, List<User> objects){
            super(context, resource, objects);
        }

        public Bitmap decodeBase64(String input) {
            byte[] decodedByte = Base64.decode(input, 0);
            return BitmapFactory
                    .decodeByteArray(decodedByte, 0, decodedByte.length);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final User user = getItem(position);
            final Database.Profile profile = user.getProfile();
            final String imageRaw = profile.getProfilePicture();

            if (convertView == null) {
                convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.search_list_item, parent, false);
            }

            ImageView image = convertView.findViewById(R.id.searchMatchImage);
            TextView titleLine = convertView.findViewById(R.id.searchMatchUserId);
            TextView secondLine = convertView.findViewById(R.id.searchMatchBookTitle);
            Button button1 = convertView.findViewById(R.id.searchAddBookListButton);
            Button button2 = convertView.findViewById(R.id.searchAddWishListButton);

            if (imageRaw.length() > 0) {
                image.setImageBitmap(decodeBase64(imageRaw));
            } else {
                image.setImageResource(R.drawable.test);
            }

            titleLine.setText(profile.getProfileName());
            secondLine.setText("Owns " + Integer.toString(user.getOwnedList().size()) + " books");

            button1.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentUsers = null;
                    Integer userID = user.getUserId();

                    //create intent to correct type of profile
                    if(userID.equals(mCurrUserID)) {
                        intentUsers = new Intent(getContext(), Profile.class);
                    }
                    else
                    {
                        intentUsers = new Intent(getContext(), OtherProfile.class);
                    }

                    //place username in Intent
                    intentUsers.putExtra("userProfileID", userID);
                    getContext().startActivity(intentUsers);
                }
            });

            return convertView;
        }
    }
}
