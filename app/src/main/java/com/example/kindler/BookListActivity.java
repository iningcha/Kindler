package com.example.kindler;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Database.Book;
import Database.UserViewModel;

public class BookListActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout bookshelf;
    private static List<Book> books;
    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        books = LoadBooks();

        //create horizontal linear layout for books
        ScrollView bookLayout = findViewById(R.id.bookDisplay);
        //bookshelf = findViewById(R.id.bookLayout);
        bookshelf = new LinearLayout(this);
        bookshelf.setOrientation(LinearLayout.VERTICAL);

        //add books to scroll bar
        for (int i = 0; i < books.size(); i++) {
            Log.d("BookListActivityLog", "Wish List Item Book Id: " + Integer.toString(i));

            //create horizontal linear layout to hold book and remove button
            LinearLayout bookView = new LinearLayout(BookListActivity.this);
            bookView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            bookView.setOrientation(LinearLayout.HORIZONTAL);
            bookView.setGravity(Gravity.CENTER);

            //create book view
            LinearLayout bookArea = new LinearLayout(BookListActivity.this);
            bookArea.setOrientation(LinearLayout.VERTICAL);
            bookArea.setPadding(16,0,16,0);
            bookArea.setLayoutParams( new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f));

            //create text view for book title
            TextView bookTitle = new TextView(BookListActivity.this);
            bookTitle.setText(books.get(i).getBookName());
            bookTitle.setTextSize(30);

            //create Image view for book picture
            ImageView bookImage = new ImageView( BookListActivity.this);
            int maxHeight = 300;
            int maxWidth = 200;
            bookImage.setBackgroundColor(Color.WHITE);
            bookImage.setMaxHeight(maxHeight);
            bookImage.setMinimumHeight(maxHeight);
            bookImage.setMinimumWidth(maxWidth);
            bookImage.setMaxWidth(maxWidth);
            Picasso.with(getBaseContext()).load(books.get(i).getBookPic()).into(bookImage);

            //add text view and image view to book linear layout
            bookArea.addView(bookTitle);
            bookArea.addView(bookImage);

            //create button
            Button button = new Button(BookListActivity.this);
            button.setText("Remove");
            button.setTag(i);
            button.setOnClickListener(BookListActivity.this);

            //add book area and button to bookView
            bookView.addView(bookArea);
            bookView.addView(button);

            //add bookView to book shelf
            bookshelf.addView(bookView);
        }

        //add to scroll view
        bookLayout.addView(bookshelf);
    }

    //onclick listener for for button
    @Override
    public void onClick(View v){
        //get button tag
        int pos = (int) v.getTag();

        //store book name for message
        Log.d("BookListActivityLog", "Remove Owned List at : " + Integer.toString(pos));
        String name = books.get(pos).getBookName();
        int bid = books.get(pos).getBookId();
        Log.d("BookListActivityLog", "Remove Book Id: " + Integer.toString(bid));

        //remove book at ownedBooks[pos]
        mUserViewModel.removeOwnedList(bid);

        //remove book associated with button
        bookshelf.removeView((View)v.getParent());

        //display message to user
        String message = "Removed : " + name;
        Toast.makeText(getApplicationContext(), message , Toast.LENGTH_SHORT ).show();
        books = LoadBooks();
    }

    //load book pictures
    protected ArrayList<Book> LoadBooks()
    {
        //create array list for books
        ArrayList<Book> books = new ArrayList<Book>();

        Log.d("BookListActivityLog", "Book List Size: " + Integer.toString(mUserViewModel.getOwnedlist().size()));
        for (int i : mUserViewModel.getOwnedlist()) {
            Log.d("BookListActivityLog", "Book List Item Book Id: " + Integer.toString(i));
            books.add(mUserViewModel.getBook(i));
        }

        return books;
    }

}