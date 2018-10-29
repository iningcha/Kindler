package com.example.kindler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Search extends AppCompatActivity implements View.OnClickListener {

    EditText _search;
    Button _button;
    ListView _searchResultList;
    CustomAdapter _bookListAdapter;

    ArrayList<BookItem> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this._search = findViewById(R.id.searchText);
        this._button = findViewById(R.id.searchButton);
        this._searchResultList = findViewById(R.id.searchResultsList);

        _bookListAdapter = new CustomAdapter();
        this._searchResultList.setAdapter(_bookListAdapter);

        this._button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchButton:
                this.handleSearch();
                break;
        }
    }

    private void handleSearch() {
        String query = this._search.getText().toString();
        String requestUrl = "https://www.googleapis.com/books/v1/volumes?q=" + query;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Search.this.handleResponse(response);
//                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);
    }

    private void handleResponse(String response) {
        try {
            _bookListAdapter.notifyDataSetInvalidated();
            results.clear();

            JSONObject data = new JSONObject(response);
            JSONArray items = data.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                JSONArray authorsJSONArray = volumeInfo.getJSONArray("authors");

                BookItem book = new BookItem();
                book.id = item.getString("id");
                book.title = volumeInfo.getString("title");
                for (int j = 0; j < authorsJSONArray.length(); j++) {
                    book.authors.add(authorsJSONArray.getString(j));
                }
                book.description = volumeInfo.optString("description");
                book.imageLink = volumeInfo.getJSONObject("imageLinks").optString("thumbnail");

                results.add(book);
            }

            _bookListAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount(){
            if (results == null) {
                return 0;
            }
            return results.size();
        }

        @Override
        public BookItem getItem(int i){
            return results.get(i);
        }

        @Override
        public long getItemId(int i){
            return getItem(i).id.hashCode();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup){
            view = getLayoutInflater().inflate(R.layout.customlist, null);
            ImageView matchImage = view.findViewById(R.id.matchImage);
            TextView matchUserId = view.findViewById(R.id.matchUserId);
            TextView matchBookTitle = view.findViewById(R.id.matchBookTitle);

            BookItem item = getItem(i);

            Picasso.with(getBaseContext()).load(item.imageLink).into(matchImage);

            matchUserId.setText(item.title);
            matchBookTitle.setText(item.getAuthorsString());

            return view;
        }


    }
}
