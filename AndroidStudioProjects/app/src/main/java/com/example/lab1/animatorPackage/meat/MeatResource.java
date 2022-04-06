package com.example.lab1.animatorPackage.meat;

import android.widget.ImageView;

public class MeatResource {
    private ImageView view;
    private int icon;

    public MeatResource(int icon, ImageView view){
        this.icon = icon;
        this.view = view;
    }

    public ImageView getView() {
        return view;
    }

    public void setView(ImageView view) {
        this.view = view;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


}
