package com.example.capstone.Item;


import android.graphics.drawable.Drawable;

public class ListViewItem {

    private Drawable iconDrawable ;
    private Drawable stateDrawable ;
    private String titleStr ;
    private String peopleStr ;
    private int roomNum;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setState(Drawable icon) {
        stateDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setPeople(String people) {
        peopleStr = people;
    }
    public void setRoomNum(int num){roomNum = num;}

    public void modPeople(String people) {
        people += ", "+people;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public Drawable getState() {
        return this.stateDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getPeople() {
        return this.peopleStr ;
    }
}


