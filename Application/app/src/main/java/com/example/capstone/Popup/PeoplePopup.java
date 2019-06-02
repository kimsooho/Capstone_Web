package com.example.capstone.Popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.capstone.R;

public class PeoplePopup extends Activity {

    public int roomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_people);

        Intent intent = getIntent();
        roomNum = intent.getExtras().getInt("RomNum");

        /*현재 이방에 참여중인 인원 가져와서 아래 스트링 배열에 넣기*/
        String[] List ={"test1","test2"};

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, List);

        ListView listview = (ListView) findViewById(R.id.listView_people);
        listview.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }



}

