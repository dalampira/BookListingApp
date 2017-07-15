package com.example.android.booklistingapp;

import java.util.ArrayList;

/**
 * Created by sissy on 10/7/2017.
 */

public class Book {

    private String mTitle;

    private ArrayList<String> mAuthor;


    public Book(String title, ArrayList<String> author) {
        mTitle = title;
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }


    public ArrayList<String> getAuthor() {
        return mAuthor;
    }


}


