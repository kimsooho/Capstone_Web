package com.example.capstone;


import android.graphics.drawable.Drawable;

public class ListViewItem {

    private Drawable iconDrawable ;
    private Drawable stateDrawable ;
    private String titleStr ;
    private int peopleNum;
    private String peopleStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setState(Drawable icon) {
        stateDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setPeopleNum(int num) {
        peopleNum = num;
    }
    public void setPeople(String people) {
        peopleStr = people;
    }

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
    public int getPeopleNum() {
        return this.peopleNum;
    }
    public String getPeople() {
        return this.peopleStr ;
    }
}


