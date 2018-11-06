package com.example.kindler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import Database.Book;

public class BookListActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout bookshelf;
    private ArrayList<Integer> bookList;
    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //fake data to test layout
        bookList = LoadBookList();
        books = LoadBooks();

        //create horizontal linear layout for books
        ScrollView bookLayout = findViewById(R.id.bookDisplay);
        //bookshelf = findViewById(R.id.bookLayout);
        bookshelf = new LinearLayout(this);
        bookshelf.setOrientation(LinearLayout.VERTICAL);

        //add books to scroll bar
        for (int i = 0; i < bookList.size(); i++) {
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
            bookImage.setBackgroundColor(Color.BLUE);
            bookImage.setMaxHeight(maxHeight);
            bookImage.setMinimumHeight(maxHeight);
            bookImage.setMinimumWidth(maxWidth);
            bookImage.setMaxWidth(maxWidth);
            Picasso.with(getBaseContext()).load("http://i.imgur.com/DvpvklR.png").into(bookImage);

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
        String name = books.get(bookList.get(pos)).getBookName();

        //remove book at ownedBooks[pos]

        //remove book associated with button
        bookshelf.removeView((View)v.getParent());

        //display message to user
        String message = "Removed : " + name;
        Toast.makeText(getApplicationContext(), message , Toast.LENGTH_SHORT ).show();
    }


    //load indices of books
    protected ArrayList<Integer> LoadBookList()
    {
        ArrayList<Integer> books = new ArrayList<Integer>();
        for(int i=0 ;i < 10; i++)
        {
            books.add(i);
        }
        return books;
    }

    //load book pictures
    protected ArrayList<Book> LoadBooks()
    {
        //create array list for books
        ArrayList<Book> books = new ArrayList<Book>();

        //get random url's for pictures
        ArrayList<String> urls = new ArrayList<String>();

        for(int i=0 ;i<10;i++) {
            urls.add("https://vignette.wikia.nocookie.net/blogclan-2/images/b/b9/Random-image-15.jpg/revision/latest?cb=20160706220047");
        }

        for(int i=0 ; i < 10 ; i++)
        {
            Integer book_id = i;
            String title = "FakeBook" + i;
            String pic = urls.get(i);

            Book fake = new Book(title,pic);
            books.add(fake);
        }

        return books;
    }

}