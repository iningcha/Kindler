package com.example.kindler;

import java.util.ArrayList;

public class BookItem {
    public String id;
    public String title;
    public ArrayList<String> authors;
    public String description;
    public String imageLink;

    public BookItem() {
        this.authors = new ArrayList<>();
    }

    public String getAuthorsString() {
        return String.join(", ", this.authors);
    }
}
