package com.example.lab1.animatorPackage.cross;

import android.widget.ImageView;

public class CrossResource {

    private ImageView cross_view;
    private int icon;

    public CrossResource(int icon ,ImageView cross_view){
        this.cross_view = cross_view;
        this.icon = icon;
    }

    public ImageView getCross_view() {
        return cross_view;
    }

    public void setCross_view(ImageView cross_view) {
        this.cross_view = cross_view;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
