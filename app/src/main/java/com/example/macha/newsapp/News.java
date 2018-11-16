package com.example.macha.newsapp;

import android.graphics.Bitmap;

public class News {

    private String mSectionName;
    private String mWebPublicationDate;
    private String mWebTitle;
    private String mWebUrl;
    private String mAuthor;
    private String mtTrailText;

    public News(String sectionName,String webPublicationDate, String webTitle, String webUrl,
                String author,String trailText){
        mSectionName = sectionName;
        mWebPublicationDate = webPublicationDate;
        mWebTitle = webTitle;
        mWebUrl = webUrl;
        mAuthor = author;
        mtTrailText = trailText;

    }
    public String getmAuthor() {
        return mAuthor;
    }

    public String getmWebTitle() {
        return mWebTitle;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmWebUrl() {
        return mWebUrl;
    }

    public String getmWebPublicationDate() {
        return mWebPublicationDate;
    }

    public String getmTrailText() {
        return mtTrailText;
    }
}
