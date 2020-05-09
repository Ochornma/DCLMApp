package org.dclm.live.ui.ondemand.search;

import android.os.Build;
import android.text.Html;

public class Search {

    private String id;
    private String title;
    private String subtitle;
    private String audioUrl;
    private String urlHigh;
    private String urlLow;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return String.valueOf(Html.fromHtml("<p align=\"justify\">"+
                    " " + title + "</p>", Html.FROM_HTML_MODE_COMPACT));
        } else {
           return String.valueOf(Html.fromHtml("<p align=\"justify\">"+
                    " " +title + "</p>"));
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getUrlHigh() {
        return urlHigh;
    }

    public void setUrlHigh(String urlHigh) {
        this.urlHigh = urlHigh;
    }

    public String getUrlLow() {
        return urlLow;
    }

    public void setUrlLow(String urlLow) {
        this.urlLow = urlLow;
    }

    public Search(String id, String title, String subtitle, String audioUrl, String urlHigh, String urlLow) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.audioUrl = audioUrl;
        this.urlHigh = urlHigh;
        this.urlLow = urlLow;
    }

    public Search() {
    }

}
