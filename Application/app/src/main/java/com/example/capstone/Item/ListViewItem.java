package com.example.capstone.Item;


import android.graphics.drawable.Drawable;

public class ListViewItem {

    private Drawable iconDrawable ;
    private Drawable stateDrawable ;
    private String titleStr ;
    private String makeMember ;

    private int roomNum; //0 true
    private int status;


    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setState(Drawable icon) {
        stateDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setMakeMember(String people) {
        makeMember = people;
    }
    public void setRoomNum(int num){roomNum = num;}
    public void setStatus(int b){status = b;}

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public Drawable getState() {
        return this.stateDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getMakeMember() {
        return this.makeMember ;
    }

    public int getStatus(){return this.status;}
    public int getRoomNum(){return this.roomNum;}
}


