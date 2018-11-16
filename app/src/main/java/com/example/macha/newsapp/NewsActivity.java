package com.example.macha.newsapp;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>> {

    // URL Base
    private static final String URL_BASE = "https://content.guardianapis.com/search?";

    // Extras at the end of the URL string
    private static final String URL_EXTRAS = "&show-fields=headline,trailText,shortUrl,thumbnail,byline";

    /**
     * API Key Value which you need to store in your gradle.properties file as:
     * NewsApp_ApiKey="ApiKey"
     */
    private static final String apiKey = BuildConfig.ApiKey;
    private static final String URL_API_KEY = "&api-key=" + apiKey;


    private NewsAdapter mAdapter;

    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    private ListView newsListView;


    public static final String LOG_TAG = NewsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST: News activity oncreate called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        newsListView = findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        // Create a new adapter that takes an empty list of news as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected news article.

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //find the current news item that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getmWebUrl());

                // Create a new intent to view the news URI
                Intent webIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(webIntent);

            }
        });

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        //loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            // android.app.LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }

    @Override
    public android.content.Loader<List<News>> onCreateLoader(int id, Bundle args) {
        // Start the StringBuilder and add the URL Base to the beginning
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(URL_BASE);

        // Add the extras to the query
        stringBuilder.append(URL_EXTRAS);

        // Add the API Key to the end of the query
        stringBuilder.append(URL_API_KEY);

        Uri baseUri = Uri.parse(URL_BASE);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("format","geoJson");

        // LOG the API URL
        Log.i(LOG_TAG, "API GUARDIAN_REQUEST_URL: " + stringBuilder.toString());

        return new NewsLoader(this,stringBuilder.toString());


    }

    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> data) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous news data
        mAdapter.clear();

        // If there is a valid list of {@link News}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        // If there is a valid list of newsFeed, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        } else {
            if (QueryUtils.isConnected(getBaseContext())) {
                // Set empty state text to display "No articles found."
                mEmptyStateTextView.setText(R.string.no_articles);
            } else {
                // Update toast with no connection error message
                Toast.makeText(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();

    }

}


