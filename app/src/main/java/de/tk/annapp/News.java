package de.tk.annapp;

import android.graphics.drawable.Drawable;

/**
 * Created by Petrus on 28.03.2018.
 */

public class News {
    private String title;
    private String link;
    private String discription;
    private Drawable image;

    public News(String title, String link , String discription, Drawable image) {
        this.title = title;
        this.link = link;
        this.discription = discription;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
