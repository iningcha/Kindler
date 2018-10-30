package com.example.kindler.models;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class BookItem {
    public String id;
    public String title;
    public List<String> authors = new ArrayList<>();
    public String description;
    public String imageLink;

    public String getAuthorsString() {
        String text = "";
        if (authors.size() == 0) {
            return "";
        }
        for (String author : authors) {
            text = text + author + ", ";
        }
        return text.substring(0, text.length() - 2);
    }
}
