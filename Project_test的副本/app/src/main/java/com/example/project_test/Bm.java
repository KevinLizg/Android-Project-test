package com.example.project_test;

import android.graphics.Bitmap;

public class Bm {
    private Bitmap test;

    private String tag;

    public Bm(Bitmap test,String tag){
        this.test=test;
        this.tag=tag;
    }




    public Bitmap getImage(){
        return test;
    }

    public String getTag(){return tag;}
}
