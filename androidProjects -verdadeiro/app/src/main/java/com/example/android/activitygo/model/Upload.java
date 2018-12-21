package com.example.android.activitygo.model;

public class Upload {
    private String mUsername;
    private String mName;
    private String mImageUrl;

    public Upload() {
    }

    public Upload(String username, String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mUsername = username;
        mName = name;
        mImageUrl = imageUrl;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}