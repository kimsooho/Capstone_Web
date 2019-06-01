package com.example.capstone.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.capstone.Popup.PeoplePopup;
import com.example.capstone.R;

//회의대기 화면
public class WaitingActivity extends AppCompatActivity {
    public static Activity activity;
    public int roomNum;
    public String userID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        activity = WaitingActivity.this;

        //이전 액티비티 종료
        Intent intent = getIntent();
        roomNum = intent.getExtras().getInt("RoomNum");
        userID = intent.getStringExtra("ID");
        if (intent.getExtras().getBoolean("where")) { //채널 생성 액티비티에서 왓으면 생성 액티비티 종료
            MakeChannelActivity makeChannelActivity = (MakeChannelActivity) MakeChannelActivity.createChannelActivity;
            makeChannelActivity.finish();
            Log.d("test","wati");
        }



    }

    public void startConference(View view)//btn_play
    {
        Intent intent = new Intent(WaitingActivity.this, ConferenceActivity.class);
        intent.putExtra("RoomNum", roomNum);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void checkPeople_wait(View v) { //btn_people_waiting
        final Intent goCheck = new Intent(WaitingActivity.this, PeoplePopup.class);
        //값보내기
        goCheck.putExtra("RoomNum", roomNum);
        startActivity(goCheck);
    }

    @Override
    protected void onDestroy() {
        /*서버에 사용자가 나감을 알림*/
        super.onDestroy();
    }
}
