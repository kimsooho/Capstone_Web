package com.example.capstone.Popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone.Activity.ConferenceActivity;
import com.example.capstone.Activity.LoginActivity;
import com.example.capstone.R;

import org.json.JSONException;
import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

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
        final Intent conferenceIntent = new Intent(PasswordPopup.this, ConferenceActivity.class);

        //비밀번호 맞는지 체크
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/login")
                .contentType("application/json; charset=utf-8")
                .type("POST")
                .data(jsonObject.toString())
                .context(this)
                .success(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        String result = objects[0].toString();
                        Log.d("test", objects[0].toString());
                        if (result.equals("success")) {
                            conferenceIntent.putExtra("userID", userID);
                            conferenceIntent.putExtra("RoomNum", roomNum);
                            conferenceIntent.putExtra("makeMember", makeMember);
                            conferenceIntent.putExtra("where",false);
                            startActivity(conferenceIntent);
                        } else {
                            Toast.makeText(PasswordPopup.this, "PW를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        Log.d("test", objects[0].toString());
                    }
                }));
    }
    public void btn_room_cancel(View v)
    {
        super.onBackPressed();
    }


}
