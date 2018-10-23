package com.example.kindler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Search extends AppCompatActivity implements View.OnClickListener {

    EditText _search;
    Button _button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this._search = findViewById(R.id.searchText);
        this._button = findViewById(R.id.searchButton);

        if (this._button != null) {
            this._button.setOnClickListener(this);
        }
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
            JSONObject data = new JSONObject(response);
            JSONArray items = data.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                JSONArray authorsJSONArray = volumeInfo.getJSONArray("authors");
                ArrayList<String> authorsArray = new ArrayList<>();
                for (int j = 0; j < authorsJSONArray.length(); j++) {
                    authorsArray.add(authorsJSONArray.getString(j));
                }

                String id = item.getString("id");
                String title = volumeInfo.getString("title");
                String authors = String.join(", ", authorsArray);
                String description = volumeInfo.optString("description");
                String imageLink = volumeInfo.getJSONObject("imageLinks").optString("thumbnail");

                System.out.println(id);
                System.out.println(title);
                System.out.println(authors);
                System.out.println(description);
                System.out.println(imageLink);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
