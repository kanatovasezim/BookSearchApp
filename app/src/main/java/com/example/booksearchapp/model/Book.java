package com.example.booksearchapp.model;

public class Book {
    private String mTitle;
    private String mAuthors;
    private String mPublishedDate;
    private String mDescription;
    private String mThumbnail;
    private String mPreview;
    private Integer pageCount;
    public Book(String mTitle, String mAuthors, String mPublishedDate, String mDescription,  String mThumbnail,
                 String mPreview , int pageCount ) {
        this.mTitle = mTitle;
        this.mAuthors = mAuthors;
        this.mPublishedDate = mPublishedDate;
        this.mDescription = mDescription;
        this.mThumbnail = mThumbnail;
        this.mPreview = mPreview;
        this.pageCount = pageCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public String getThumbnail() {
        return mThumbnail;
    }
    public String getPreview() {
        return mPreview;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getPageCount() {
        if (pageCount == null){
            return 0;
        }
        return pageCount;
    }
}
