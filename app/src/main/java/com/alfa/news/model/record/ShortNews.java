package com.alfa.news.model.record;

import android.os.Parcel;
import android.os.Parcelable;

public class ShortNews implements Parcelable {

    public static final Creator<ShortNews> CREATOR = new Creator<ShortNews>() {
        public ShortNews createFromParcel(Parcel in) {
            return new ShortNews(in);
        }

        public ShortNews[] newArray(int size) {
            return new ShortNews[size];
        }
    };
    private String url;
    private String date;

    public ShortNews() {
    }

    public ShortNews(String url, String date) {
        this.url = url;
        this.date = date;
    }

    protected ShortNews(Parcel in) {
        url = in.readString();
        date = in.readString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(date);
    }
}
