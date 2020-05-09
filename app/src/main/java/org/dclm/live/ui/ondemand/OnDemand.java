package org.dclm.live.ui.ondemand;

import android.os.Build;
import android.text.Html;

public class OnDemand {

    private String title;
    private String date;
    private String urlHigh;
    private String urlLow;
    private String urlAudio;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          return String.valueOf(Html.fromHtml("<p align=\"justify\">"+
                    " " + title + "</p>", Html.FROM_HTML_MODE_COMPACT));
        } else {
          return String.valueOf((Html.fromHtml("<p align=\"justify\">"+
                    " " + title + "</p>")));
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }


    public OnDemand() {
    }

    public OnDemand(String title, String date, String urlHigh, String urlLow, String urlAudio, String category) {
        this.title = title;
        this.date = date;
        this.urlHigh = urlHigh;
        this.urlLow = urlLow;
        this.urlAudio = urlAudio;
        this.category = category;
    }

    public static class CheckState {
        private int position;
        private boolean check;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public boolean isCheck() {
            return check;
        }



        public void setCheck(boolean check) {


            this.check = check;
        }

        public CheckState() {
        }

        public  CheckState(int position, boolean check) {
            this.position = position;
            this.check = check;
        }
    }

    public static class Category{

        private int number;
        private String category;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Category() {
        }

        public Category(int number, String category) {
            this.number = number;
            this.category = category;
        }
    }
}
