package com.example.capstone;


import android.graphics.drawable.Drawable;

public class DialogueViewItem {

    private String name;
    private String date;
    private String talk;

    public void setName(String icon) {
        name = icon ;
    }
    public void setDate(String  icon) {
        date = icon ;
    }
    public void setTalk(String title) {
        talk = title ;
    }

    public String getName() {
        return this.name;
    }
    public String getDate() {
        return this.date;
    }
    public String getTalk() {
        return this.talk;
    }

}


