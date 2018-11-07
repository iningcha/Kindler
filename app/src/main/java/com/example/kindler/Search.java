package com.example.kindler;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kindler.models.BookItem;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import Database.Book;
import Database.UserViewModel;

public class Search extends AppCompatActivity implements View.OnClickListener {

    private UserViewModel mUserViewModel;
    EditText _mSearch;
    Button _mButton;
    ListView _mSearchResultList;
    ArrayAdapter<BookItem> _mBookListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        this._mSearch = findViewById(R.id.searchText);
        this._mButton = findViewById(R.id.searchButton);
        this._mSearchResultList = findViewById(R.id.searchResultsList);

        this._mBookListAdapter = new SearchListAdapter(this, R.layout.customlist, new ArrayList<BookItem>());
        this._mSearchResultList.setAdapter(_mBookListAdapter);

        this._mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchButton:
                this.handleSearch();
                break;
        }
    }

    public String getRequestUrl(String query) {
        try {
            return "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Couldn't encode URL");
        }
        return null;
    }

    public String convertToHTTPS(String url) {
        return url.replaceFirst("http://", "https://");
    }

    public void handleSearch() {
        String query = this._mSearch.getText().toString();
        String url = getRequestUrl(query);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<BookItem> books = parseSearchResponse(response);

                        _mBookListAdapter.clear();
                        _mBookListAdapter.addAll(books);
                        _mBookListAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(stringRequest);
    }

    public List<BookItem> parseSearchResponse(String response) {
        List<BookItem> books = new ArrayList<>();
        try {

            JSONObject data = new JSONObject(response);
            JSONArray items = data.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                JSONArray authorsJSONArray = volumeInfo.optJSONArray("authors");
                BookItem book = new BookItem();
                book.id = item.getString("id");
                book.title = volumeInfo.getString("title");
                if (authorsJSONArray != null) {
                    for (int j = 0; j < authorsJSONArray.length(); j++) {
                        book.authors.add(authorsJSONArray.getString(j));
                    }
                }
                book.description = volumeInfo.optString("description");
                JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
                if (imageLinks != null) {
                    String thumbnail = imageLinks.optString("thumbnail");
                    book.imageLink = convertToHTTPS(thumbnail);
                } else {
                    book.imageLink = "";
                }

                books.add(book);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

    class SearchListAdapter extends ArrayAdapter<BookItem> implements View.OnClickListener {
        public SearchListAdapter(Context context, int resource, List<BookItem> objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final BookItem book = getItem(position);

            if(convertView == null) {
                convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.search_list_item, parent, false);
            }

            ImageView matchImage = convertView.findViewById(R.id.searchMatchImage);
            TextView matchUserId = convertView.findViewById(R.id.searchMatchUserId);
            TextView matchBookTitle = convertView.findViewById(R.id.searchMatchBookTitle);
            Button addBookListButton = convertView.findViewById(R.id.searchAddBookListButton);
            Button addWishListButton = convertView.findViewById(R.id.searchAddWishListButton);

            if (book.imageLink != null && !book.imageLink.equals("")) {
                Picasso.with(getContext()).load(book.imageLink).into(matchImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("Successfully loaded " + book.imageLink);
                    }

                    @Override
                    public void onError() {
                        System.out.println("Could not load " + book.imageLink);
                    }
                });
            }

            matchUserId.setText(book.title);
            matchBookTitle.setText(book.getAuthorsString());

            SearchListButtonHandler buttonHandler = new SearchListButtonHandler(book);

            addBookListButton.setOnClickListener(buttonHandler);
            addWishListButton.setOnClickListener(buttonHandler);

            return convertView;
        }

        @Override
        public void onClick(View v) {

        }
    }

    class SearchListButtonHandler implements View.OnClickListener {
        private BookItem mBook;
        public SearchListButtonHandler(BookItem b) {
            mBook = b;
        }

        @Override
        public void onClick(View v) {
            Book b = new Book(mBook.title, mBook.imageLink);
            b.setBookId(mBook.id.hashCode());

            switch (v.getId()) {
                case R.id.searchAddBookListButton:
                    mUserViewModel.insertBook(b);
                    mUserViewModel.addOwnedList(b.getBookId());
                    break;
                case R.id.searchAddWishListButton:
                    mUserViewModel.insertBook(b);
                    mUserViewModel.addWishList(b.getBookId());
                    break;
            }
        }
    }
}