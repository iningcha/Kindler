package com.example.kindler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Database.Book;
import Database.UserViewModel;

public class OtherBookList extends AppCompatActivity implements View.OnClickListener {
    private static List<Book> books;
    private int mUserID;
    private ListView mBookList;
    private UserViewModel mUserViewModel;
    private ArrayAdapter<Book> bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_book_list);

        //get user view model and load books
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUserID = mUserViewModel.getCurrUserId();
        books = LoadBooks();

        //get list from display
        mBookList = findViewById(R.id.otherBookListDisplay);

        //create adapter and bind with list view
        this.bookAdapter = new OtherBookListAdapter(this, R.layout.customlist, new ArrayList<Book>());
        this.mBookList.setAdapter(bookAdapter);

        //clear adapter and add books
        bookAdapter.clear();
        bookAdapter.addAll(books);
    }

    //onclick listener for for button
    @Override
    public void onClick(View v){

    }

    //load book pictures
    protected ArrayList<Book> LoadBooks()
    {
        //create array list for books
        ArrayList<Book> books = new ArrayList<Book>();

        Log.d("BookListActivityLog", "Book List Size: " + Integer.toString(mUserViewModel.getWishlist().size()));
        for (int i : mUserViewModel.getUserById(mUserID).getOwnedList()) {
            Log.d("BookListActivityLog", "Book List Item Book Id: " + Integer.toString(i));
            if (mUserViewModel.getBook(i) != null) {
                books.add(mUserViewModel.getBook(i));
            }
        }

        return books;
    }

    //adapter for book display
    class OtherBookListAdapter extends ArrayAdapter<Book> implements View.OnClickListener {
        public OtherBookListAdapter(Context context, int resource, List<Book> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Book book = getItem(position);

            if(convertView == null) {
                convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.search_list_item, parent, false);
            }

            ImageView matchImage = convertView.findViewById(R.id.searchMatchImage);
            TextView matchUserId = convertView.findViewById(R.id.searchMatchUserId);
            TextView matchBookTitle = convertView.findViewById(R.id.searchMatchBookTitle);
            Button bookButton = convertView.findViewById(R.id.searchAddBookListButton);
            Button wishButton = convertView.findViewById(R.id.searchAddWishListButton);

            if (book.getBookPic() != null && !book.getBookPic().equals("")) {
                Picasso.with(getContext()).load(book.getBookPic()).into(matchImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("Successfully loaded " + book.getBookPic());
                    }

                    @Override
                    public void onError() {
                        System.out.println("Could not load " + book.getBookPic());
                    }
                });
            }

            //set book name
            matchUserId.setText(book.getBookName());

            //make author textView invisible
            matchBookTitle.setVisibility(View.INVISIBLE);

            OtherBookListButtonHandler buttonHandler = new OtherBookListButtonHandler(book);

            //add onclick listener to buttons
            bookButton.setOnClickListener(buttonHandler);
            wishButton.setOnClickListener(buttonHandler);

            //make buttons invisible if book is already in a user list
            if(book.getOwnedUser().contains(mUserID) || book.getWishUser().contains(mUserID)){
                bookButton.setVisibility(View.INVISIBLE);
                wishButton.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }

        @Override
        public void onClick(View v) {

        }
    }

    class OtherBookListButtonHandler implements View.OnClickListener {
        private Book mBook;
        public OtherBookListButtonHandler(Book b) {
            mBook = b;
        }

        @Override
        public void onClick(View v) {

            ViewGroup container = (ViewGroup)v.getParent();

            switch (v.getId()) {
                case R.id.searchAddBookListButton:
                    //add book to user book list
                    mUserViewModel.addOwnedList(mBook.getBookId());
                    mUserViewModel.addOwnedUser(mBook.getBookId(), mUserID );

                    //make add and wish button invisible
                    v.setVisibility(View.INVISIBLE);
                    container.getChildAt(4).setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(), "Added book to Book List!", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.searchAddWishListButton:
                    //add book to user book list
                    mUserViewModel.addWishList(mBook.getBookId());
                    mUserViewModel.addWishUser(mBook.getBookId(), mUserID );

                    //make add and wish button invisible
                    v.setVisibility(View.INVISIBLE);
                    container.getChildAt(3).setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(), "Added book to Wish List!", Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    }
}
