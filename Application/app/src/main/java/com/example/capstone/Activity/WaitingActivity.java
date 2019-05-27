package com.example.capstone.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.capstone.R;

//회의대기 화면
public class WaitingActivity extends AppCompatActivity {
    public static Activity activity;
    public int roomNum;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        activity = WaitingActivity.this;

        //이전 액티비티 종료
       Intent intent = getIntent();
       roomNum = intent.getExtras().getInt("RoomNum");

/*
       if(intent.getExtras().getString("구분") == "formMakeChannel" )
       {
           Log.d("Debug","---");
           MakeChannelActivity makeChannelActivity = (MakeChannelActivity)MakeChannelActivity.createChannelActivity;
           makeChannelActivity.finish();
       }*/



        //Button btn=(Button)findViewById(R.id.button2);

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WaitingActivity.this,ConferenceActivity.class);
                startActivity(intent);
            }
        });*/
    }

    public void startConference(View view)
    {
        Intent intent=new Intent(WaitingActivity.this,ConferenceActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}
