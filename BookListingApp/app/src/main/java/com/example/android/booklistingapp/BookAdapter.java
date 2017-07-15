package com.example.android.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sissy on 10/7/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {


    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);


        TextView author = (TextView) listItemView.findViewById(R.id.first_text_view);

        String listRepresentation = "";
        for (String choice : currentBook.getAuthor()) {
            listRepresentation = choice;
        }
        author.setText(listRepresentation);

        TextView title = (TextView) listItemView.findViewById(R.id.second_text_view);

        title.setText(currentBook.getTitle());


        return listItemView;
    }


//s((CharSequence) currentBook.getAuthor());
}