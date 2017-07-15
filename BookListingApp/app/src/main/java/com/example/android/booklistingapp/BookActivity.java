package com.example.android.booklistingapp;

/**
 * Created by sissy on 10/7/2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;


public class BookActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    private static final String LOG_TAG = BookActivity.class.getName();

    private static final String USGS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String MAX_RESULTS = "&maxResults=10";


    private static final int BOOK_LOADER_ID = 1;

    private BookAdapter mAdapter;

    private TextView mEmptyStateTextView;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        ListView bookListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        bookListView.setAdapter(mAdapter);


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();


            loaderManager.initLoader(BOOK_LOADER_ID, null, this);
        } else {

            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {

        Bundle b = getIntent().getExtras();
        String a = b.getString("answer");
        return new BookLoader(this, USGS_REQUEST_URL + a + MAX_RESULTS);
    }

    @Override
    public void onLoadFinished
            (Loader<List<Book>> loader, List<Book> books) {

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);


        mEmptyStateTextView.setText(R.string.no_books);

        mAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

        mAdapter.clear();
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Book Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}

