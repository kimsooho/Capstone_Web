package com.example.capstone.Popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.capstone.Activity.ConferenceActivity;
import com.example.capstone.R;

public class PasswordPopup extends Activity {
    public static PasswordPopup passwordPopup;

    EditText editRoomPw;

    String userID;
    String makeMember;
    int roomNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_password);
        editRoomPw = (EditText)findViewById(R.id.edit_pw_room);

        passwordPopup = PasswordPopup.this;

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        roomNum = intent.getIntExtra("RoomNum",0);
        makeMember = intent.getStringExtra("makeMember");
    }


    public void btn_room_check(View v)
    {
        //비밀번호 맞는지 체크
        Intent conferenceIntent = new Intent(PasswordPopup.this, ConferenceActivity.class);
        conferenceIntent.putExtra("userID", userID);
        conferenceIntent.putExtra("RoomNum", roomNum);
        conferenceIntent.putExtra("makeMember", makeMember);
        conferenceIntent.putExtra("where",false);
        startActivity(conferenceIntent);
    }
    public void btn_room_cancel(View v)
    {
        super.onBackPressed();
    }
}
