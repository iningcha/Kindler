package com.example.kindler;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Database.Book;

public class BookListActivity extends AppCompatActivity {
    private LinearLayout bookshelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //fake data to test layout
        ArrayList<Integer> bookList = LoadBookList();
        ArrayList<Book> books = LoadBooks();

        //create horizontal linear layout for books
        ScrollView bookLayout = findViewById(R.id.bookDisplay);
        //bookshelf = findViewById(R.id.bookLayout);
        bookshelf = new LinearLayout(this);
        bookshelf.setOrientation(LinearLayout.VERTICAL);

        //add books to scroll bar
        for (int i = 0; i < bookList.size(); i++) {
            //create book view
            LinearLayout bookArea = new LinearLayout(BookListActivity.this);
            bookArea.setOrientation(LinearLayout.VERTICAL);
            bookArea.setPadding(16,0,16,0);
            bookArea.setLayoutParams( new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

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
            //Picasso.with(getBaseContext()).load("http://i.imgur.com/DvpvklR.png").into(bookImage);

            //add text view and image view to linear layout
            bookArea.addView(bookTitle);
            bookArea.addView(bookImage);

            //add bookArea to book shelf
            bookshelf.addView(bookArea);

        }

        bookLayout.addView(bookshelf);
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

        urls.add("https://vignette.wikia.nocookie.net/blogclan-2/images/b/b9/Random-image-15.jpg/revision/latest?cb=20160706220047");
        urls.add("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiosZqQl5beAhXLhlQKHU9UAQkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fanimalsinrandomplaces%2F&psig=AOvVaw3Tqa9_Q8qi97HLY35fSqx9&ust=1540164795277287");
        urls.add("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiosZqQl5beAhXLhlQKHU9UAQkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fanimalsinrandomplaces%2F&psig=AOvVaw3Tqa9_Q8qi97HLY35fSqx9&ust=1540164795277287");
        urls.add("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiosZqQl5beAhXLhlQKHU9UAQkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fanimalsinrandomplaces%2F&psig=AOvVaw3Tqa9_Q8qi97HLY35fSqx9&ust=1540164795277287");
        urls.add("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiosZqQl5beAhXLhlQKHU9UAQkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fanimalsinrandomplaces%2F&psig=AOvVaw3Tqa9_Q8qi97HLY35fSqx9&ust=1540164795277287");
        urls.add("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiosZqQl5beAhXLhlQKHU9UAQkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fanimalsinrandomplaces%2F&psig=AOvVaw3Tqa9_Q8qi97HLY35fSqx9&ust=1540164795277287");
        urls.add("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiosZqQl5beAhXLhlQKHU9UAQkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fanimalsinrandomplaces%2F&psig=AOvVaw3Tqa9_Q8qi97HLY35fSqx9&ust=1540164795277287");
        urls.add("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiosZqQl5beAhXLhlQKHU9UAQkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fanimalsinrandomplaces%2F&psig=AOvVaw3Tqa9_Q8qi97HLY35fSqx9&ust=1540164795277287");
        urls.add("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiosZqQl5beAhXLhlQKHU9UAQkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fanimalsinrandomplaces%2F&psig=AOvVaw3Tqa9_Q8qi97HLY35fSqx9&ust=1540164795277287");
        urls.add("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiosZqQl5beAhXLhlQKHU9UAQkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fanimalsinrandomplaces%2F&psig=AOvVaw3Tqa9_Q8qi97HLY35fSqx9&ust=1540164795277287");
        urls.add("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiosZqQl5beAhXLhlQKHU9UAQkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.facebook.com%2Fanimalsinrandomplaces%2F&psig=AOvVaw3Tqa9_Q8qi97HLY35fSqx9&ust=1540164795277287");

        for(int i=0 ; i < 10 ; i++)
        {
            Integer book_id = i;
            String title = "FakeBook" + i;
            String pic = urls.get(i);

            Book fake = new Book(title,pic, new ArrayList<Integer>(), new ArrayList<Integer>() );
            books.add(fake);
        }
        return books;
    }

}
