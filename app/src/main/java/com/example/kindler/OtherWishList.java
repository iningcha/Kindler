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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Database.Book;
import Database.User;
import Database.UserViewModel;

public class OtherWishList extends AppCompatActivity implements View.OnClickListener {
    private static List<Book> books;
    private Set<Integer> userBooks;
    private int mUserID;
    private int mListOwnerID;
    private ListView mBookList;
    private UserViewModel mUserViewModel;
    private ArrayAdapter<Book> bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_wish_list);

        //get list owner id from intent
        mListOwnerID = getIntent().getIntExtra("ownerID" , -1);

        //get user view model and load books
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUserID = mUserViewModel.getCurrUserId();
        LoadSet();
        books = LoadBooks();

        //get list from display
        mBookList = findViewById(R.id.otherWishListDisplay);

        //create adapter and bind with list view
        this.bookAdapter = new OtherWishListAdapter(this, R.layout.customlist, new ArrayList<Book>());
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

        List<Integer> wishlist = mUserViewModel.getUserById(mListOwnerID).getWishList();
        Log.d("BookListActivityLog", "Book List Size: " + Integer.toString(wishlist.size()));
        for (int i : wishlist) {
            Log.d("BookListActivityLog", "Book List Item Book Id: " + Integer.toString(i));
            if (mUserViewModel.getBook(i) != null) {
                books.add(mUserViewModel.getBook(i));
            }
        }

        return books;
    }

    protected void LoadSet()
    {
        userBooks = new HashSet<Integer>();
        List<Integer> booklist = mUserViewModel.getOwnedlist();
        List<Integer> wishlist = mUserViewModel.getWishlist();
        for(int i : booklist)
        {
            if(!userBooks.contains(i))
            {
                userBooks.add(i);
            }
        }

        for(int i : wishlist)
        {
            if(!userBooks.contains(i))
            {
                userBooks.add(i);
            }
        }

    }

    //adapter for book display
    class OtherWishListAdapter extends ArrayAdapter<Book> implements View.OnClickListener {
        public OtherWishListAdapter(Context context, int resource, List<Book> objects) {
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

            OtherWishListButtonHandler buttonHandler = new OtherWishListButtonHandler(book);

            //add onclick listener to buttons
            bookButton.setOnClickListener(buttonHandler);
            wishButton.setOnClickListener(buttonHandler);

            //make buttons invisible if book is already in a user list
            if(userBooks.contains(book.getBookId())){
                bookButton.setVisibility(View.INVISIBLE);
                wishButton.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }

        @Override
        public void onClick(View v) {

        }
    }




    class OtherWishListButtonHandler implements View.OnClickListener {
        private Book mBook;
        public OtherWishListButtonHandler(Book b) {
            mBook = b;
        }

        @Override
        public void onClick(View v) {

            ViewGroup container = (ViewGroup)v.getParent();
            int bookID = -1;

            switch (v.getId()) {
                case R.id.searchAddBookListButton:
                    //add book to user book list
                    bookID = mBook.getBookId();
                    mUserViewModel.addOwnedList(bookID);
                    mUserViewModel.addOwnedUser(bookID, mUserID );

                    //make add and wish button invisible
                    v.setVisibility(View.INVISIBLE);
                    container.getChildAt(4).setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(), "Added book to Book List!", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.searchAddWishListButton:
                    //add book to user book list
                    bookID = mBook.getBookId();
                    mUserViewModel.addWishList(bookID);
                    mUserViewModel.addWishUser(bookID, mUserID );

                    //make add and wish button invisible
                    v.setVisibility(View.INVISIBLE);
                    container.getChildAt(3).setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(), "Added book to Wish List!", Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    }
}
