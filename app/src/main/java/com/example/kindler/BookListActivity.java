package com.example.kindler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kindler.models.BookItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Database.Book;
import Database.UserViewModel;

public class BookListActivity extends AppCompatActivity implements View.OnClickListener{
    //private LinearLayout bookshelf;
    private static List<Book> books;
    private ListView mBookList;
    private UserViewModel mUserViewModel;
    private ArrayAdapter<Book> bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //get user view model and load books
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        books = LoadBooks();

        //get list from display
        mBookList = findViewById(R.id.bookListDisplay);

        //create adapter and bind with list view
        this.bookAdapter = new OwnedListAdapter(this, R.layout.customlist, new ArrayList<Book>());
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

        List<Integer> booklist = mUserViewModel.getOwnedlist();
        Log.d("BookListActivityLog", "Book List Size: " + Integer.toString(booklist.size()));
        for (int i : booklist) {
            Log.d("BookListActivityLog", "Book List Item Book Id: " + Integer.toString(i));
            Book book = mUserViewModel.getBook(i);
            if (book != null) {
                books.add(book);
            }
        }

        return books;
    }

    //adapter for book display
    class OwnedListAdapter extends ArrayAdapter<Book> implements View.OnClickListener {
        public OwnedListAdapter(Context context, int resource, List<Book> objects) {
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
            Button removeBookListButton = convertView.findViewById(R.id.searchAddBookListButton);
            Button invisibleButton = convertView.findViewById(R.id.searchAddWishListButton);

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

            BookListButtonHandler buttonHandler = new BookListButtonHandler(book);

            //edit remove button
            removeBookListButton.setText("-");
            removeBookListButton.setOnClickListener(buttonHandler);
            invisibleButton.setVisibility(View.INVISIBLE);

            return convertView;
        }

        @Override
        public void onClick(View v) {

        }
    }

    class BookListButtonHandler implements View.OnClickListener {
        private Book mBook;
        public BookListButtonHandler(Book b) {
            mBook = b;
        }

        @Override
        public void onClick(View v) {
            int bookID;
            switch (v.getId()) {
                case R.id.searchAddBookListButton:
                    //remove book from local book list and database
                    bookID = mBook.getBookId();
                    mUserViewModel.removeOwnedList(bookID);

                    int bookPos = -1;
                    for(int i=0; i<books.size();i++){
                        if(books.get(i).getBookId() == bookID){
                            bookPos = i;
                            break;
                        }
                    }

                    bookAdapter.remove(books.get(bookPos));
                    books.remove(bookPos);

                    Toast.makeText(getApplicationContext(), "Removed book from Owned List!", Toast.LENGTH_SHORT).show();
            }

        }
    }

}