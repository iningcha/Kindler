package com.example.kindler;

import com.example.kindler.models.BookItem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnit4.class)
public class SearchUnitTest {
    private final Search searchActivity = new Search();

    @Test
    public void testGetRequestUrl() {
        String url = searchActivity.getRequestUrl("Harry Potter");
        assertThat(url, is("https://www.googleapis.com/books/v1/volumes?q=Harry+Potter"));
    }

    @Test
    public void testGetRequestUrl2() {
        String url = searchActivity.getRequestUrl("Crime & Punishment");
        assertThat(url, is("https://www.googleapis.com/books/v1/volumes?q=Crime+%26+Punishment"));

        url = searchActivity.getRequestUrl("ü, ä, ö é, è, â");
        assertThat(url, is("https://www.googleapis.com/books/v1/volumes?q=%C3%BC%2C%20%C3%A4%2C%20%C3%B6%20%C3%A9%2C%20%C3%A8%2C%20%C3%A2"));

        url = searchActivity.getRequestUrl("C++ Programming");
        assertThat(url, is("https://www.googleapis.com/books/v1/volumes?q=C%2B%2B%20Programming"));
    }

    @Test
    public void testParseSearchResponse() {
        String searchResponse = "{\n" +
                "\t\"items\": [{\n" +
                "\t\t\t\"id\": \"0\",\n" +
                "\t\t\t\"volumeInfo\": {\n" +
                "\t\t\t\t\"authors\": [\"Sam Altman\"],\n" +
                "\t\t\t\t\"title\": \"Startup Playbook\",\n" +
                "\t\t\t\t\"description\": \"Startup advice\",\n" +
                "\t\t\t\t\"imageLinks\": {\n" +
                "\t\t\t\t\t\"thumbnail\": \"http://imageurl.png\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": \"1\",\n" +
                "\t\t\t\"volumeInfo\": {\n" +
                "\t\t\t\t\"authors\": [\"J.K. Rowling\"],\n" +
                "\t\t\t\t\"title\": \"Harry Potter\",\n" +
                "\t\t\t\t\"description\": \"Wizards\",\n" +
                "\t\t\t\t\"imageLinks\": {\n" +
                "\t\t\t\t\t\"thumbnail\": \"https://imageurl.png\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": \"2\",\n" +
                "\t\t\t\"volumeInfo\": {\n" +
                "\t\t\t\t\"authors\": [\"Somebody\", \"Second Author\"],\n" +
                "\t\t\t\t\"title\": \"Some Book\"\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";
        List<BookItem> books = searchActivity.parseSearchResponse(searchResponse);
        assertThat(books.size(), is(3));
        assertThat(books.get(0).id, is ("0"));
        assertThat(books.get(0).getAuthorsString(), is("Sam Altman"));
        assertThat(books.get(0).title, is("Startup Playbook"));
        assertThat(books.get(0).description, is("Startup advice"));
        assertThat(books.get(0).imageLink, is("https://imageurl.png"));
        assertThat(books.get(1).id, is ("1"));
        assertThat(books.get(1).getAuthorsString(), is("J.K. Rowling"));
        assertThat(books.get(1).title, is("Harry Potter"));
        assertThat(books.get(1).description, is("Wizards"));
        assertThat(books.get(1).imageLink, is("https://imageurl.png"));
        assertThat(books.get(2).id, is ("2"));
        assertThat(books.get(2).getAuthorsString(), is("Somebody, Second Author"));
        assertThat(books.get(2).title, is("Some Book"));
        assertThat(books.get(2).description, is(""));
        assertThat(books.get(2).imageLink, is(""));
    }

    @Test
    public void testAuthorsJoin() {
        BookItem book = new BookItem();
        assertThat(book.getAuthorsString(), is(""));
        book.authors.add("Author 1");
        book.authors.add("Author 2");
        book.authors.add("Author 3");
        assertThat(book.authors.size(), is(3));
        assertThat(book.getAuthorsString(), is("Author 1, Author 2, Author 3"));
    }

}
