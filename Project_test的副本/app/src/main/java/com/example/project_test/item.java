package com.example.project_test;

import android.media.Image;

public class item {
    private String name;
    private int ImageId;


    public item(String name,int ImageId) {
        this.name = name;
        this.ImageId= ImageId;
    }


    public String getName() {
        return name;
    }

    public int getImageId(){
        return ImageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(int ImageId){
        this.ImageId=ImageId;
    }
}
