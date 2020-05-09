package org.dclm.live.ui.blog;

import android.os.Build;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "blog")
public class Blog {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String date;
    private String body;

    public String getTitle() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return String.valueOf(Html.fromHtml("<p align=\"justify\">"+
                    " " + title + "</p>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            return String.valueOf(Html.fromHtml("<p align=\"justify\">"+
                    " " +title+ "</p>"));
        }
    }



    public String getDate() {
        return date;
    }


    public String getBody() {
        return body;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Ignore
    public Blog() {
    }

    public Blog(String title, String date, String body) {
        this.title = title;
        this.date = date;
        this.body = body;
    }
}
