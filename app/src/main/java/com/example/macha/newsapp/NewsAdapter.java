package com.example.macha.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, List<News> newsList) {
        super(context,0, newsList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        News currentNews = getItem(position);

        // Get and display the article's Section
        String newsSection = currentNews.getmSectionName();
        TextView sectionNameView = listItemView.findViewById(R.id.section);
        sectionNameView.setText(newsSection);

        // Get and display the news Title
        String newsTitle = currentNews.getmWebTitle();
        TextView titleView = listItemView.findViewById(R.id.news_title);
        titleView.setText(newsTitle);


        // Get and display the article's Trail Text
        String newsTrail = currentNews.getmTrailText();
        TextView trailView = listItemView.findViewById(R.id.trailtext);
        // Display the trailtext for the current article in that TextView or hide it if null
        if (newsTrail != null && !newsTrail.isEmpty()) {
            newsTrail = newsTrail + ".";
            trailView.setText(newsTrail);
        } else {
            trailView.setVisibility(View.GONE);
        }

        // Create a new Date object from the time in milliseconds of the article
        // Format the article_date string (i.e. "Apr 17, '13")
        String formattedDate = formatDate(currentNews.getmWebPublicationDate());
        // Find and display the article's Date
        TextView dateView = listItemView.findViewById(R.id.date);
        dateView.setText(formattedDate);

        // Format the time string (i.e. "9:30 AM")
        String formattedTime = formatTime(currentNews.getmWebPublicationDate());
        // Find and display the article's Time
        TextView timeView = listItemView.findViewById(R.id.time);
        timeView.setText(formattedTime);

        // Get and display the article's Author
        String newsAuthor = currentNews.getmAuthor() + " ";
        TextView authorView = listItemView.findViewById(R.id.author);
        authorView.setText(newsAuthor);

        // Return the list item view that is now showing the appropriate data
        return listItemView;

    }


    /**
     * Return a formatted time string (i.e. "Apr 17, '13") from a Date object.
     */
    private String formatDate(String date) {
        final SimpleDateFormat inputParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());

        Date date_out = null;
        try {
            date_out = inputParser.parse(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        final SimpleDateFormat outputFormatter = new SimpleDateFormat("MMM dd ''yy", Locale.US);
        return outputFormatter.format(date_out);
    }

    /**
     * Return a formatted date string (i.e. "9:30 AM") from a Date object.
     */
    private String formatTime(String date) {
        final SimpleDateFormat inputParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());

        Date date_out = null;
        try {
            date_out = inputParser.parse(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        final SimpleDateFormat outputFormatter = new SimpleDateFormat("h:mm a", Locale.US);
        return outputFormatter.format(date_out);
    }
}
