package com.example.capstone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v4.content.ContextCompat;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.capstone.Adapter.ListViewAdapter;
import com.example.capstone.Popup.SettingPopup;
import com.example.capstone.R;



public class ChannelListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        Intent intent=new Intent(this.getIntent());
        //값 받아오기
        //String s=intent.getExtras().getString("key");
        //로그찍기
        //Log.d("debug", s);

        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.List);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.main), ContextCompat.getDrawable(this, R.drawable.green),
                "노터 마케팅 1팀", "최유진 조영태 등등", 5) ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent waitingIntent=new Intent(ChannelListActivity.this,WaitingActivity.class);
                waitingIntent.putExtra("구분", "fromChannelList");
                startActivity(waitingIntent);
            }
        });
    }

    public void waiting(View v){
        Intent goWaiting=new Intent(ChannelListActivity.this,WaitingActivity.class);
        startActivity(goWaiting);
    }

    public void MakeChannel(View v) {
        Intent goMake = new Intent(ChannelListActivity.this, MakeChannelActivity.class);
        //값보내기
        //       goList.putExtra("key", editID.getText().toString());
        startActivity(goMake);
    }
    public void Setting(View v) {
        Intent goSetting = new Intent(ChannelListActivity.this, SettingPopup.class);
        //값보내기
        //       goList.putExtra("key", editID.getText().toString());
        startActivity(goSetting);
    }

    public void search(View v)
    {
        
    }
}
