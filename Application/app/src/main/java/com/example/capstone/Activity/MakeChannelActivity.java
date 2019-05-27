package com.example.capstone.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.capstone.R;

public class MakeChannelActivity extends Activity {
    public static Activity createChannelActivity; //액티비티 넘어갈때 종료를 위한 변수



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_channel);
        createChannelActivity = MakeChannelActivity.this; //해당 액티비티 할당

        Intent intent=new Intent(this.getIntent());
        //값 받아오기
        //String s=intent.getExtras().getString("key");
        //로그찍기
        //Log.d("debug", s);

    }

    public void MakeChannel(View view) //activity_make_channel 의 btn_create
    {
        Intent waitingIntent=new Intent(MakeChannelActivity.this,WaitingActivity.class);
        //서버에서 방 번호 가져오기
        waitingIntent.putExtra("RoomNum",1/*가져온 방번호 int 형으로*/);
        startActivity(waitingIntent);
    }
    public void MakeCancel(View view) //activity_make_channel 의 btn_cancel
    {
        finish();
    }

}
