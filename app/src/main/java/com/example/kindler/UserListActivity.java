package com.example.kindler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Database.User;
import Database.UserRepository;

public class UserListActivity extends AppCompatActivity {
    private ListView mUserListView;
    private UserListAdapter mUserListAdapter;
    private List<User> mUserList;
    private UserRepository mUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        System.out.println("SOMETHING 1");
        mUserRepository = ViewModelProviders.of(this).get(UserRepository.class);
        System.out.println("SOMETHING 2");
        mUserList = mUserRepository.getAllUser();
        System.out.println("SOMETHING 3");

        mUserListView = findViewById(R.id.usersListView);
        mUserListAdapter = new UserListAdapter(this, R.layout.customlist, new ArrayList<User>());
        mUserListView.setAdapter(mUserListAdapter);
        System.out.println("SOMETHING 4");

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

            if (imageRaw.length() > 0) {
                image.setImageBitmap(decodeBase64(imageRaw));
            } else {
                image.setImageResource(R.drawable.test);
            }

            titleLine.setText(profile.getProfileName());
            secondLine.setText("Owns " + Integer.toString(user.getOwnedList().size()) + " books");

            return convertView;
        }
    }
}