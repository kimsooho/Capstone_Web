package com.example.capstone.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstone.R;

import org.json.JSONException;
import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

public class MakeChannelActivity extends Activity {
    public static Activity createChannelActivity; //액티비티 넘어갈때 종료를 위한 변수
    EditText editTitle,editPW;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_channel);
        createChannelActivity = MakeChannelActivity.this; //해당 액티비티 할당
        editTitle=(EditText)findViewById(R.id.edit_title);
        editPW=(EditText)findViewById(R.id.edit_pw_create);
        Intent intent=new Intent(this.getIntent());
        id=intent.getStringExtra("ID");
        //값 받아오기
        //String s=intent.getExtras().getString("key");
        //로그찍기
        //Log.d("debug", s);
    }

    public void MakeChannel(View view) //activity_make_channel 의 btn_create
    {
        final Intent waitingIntent=new Intent(MakeChannelActivity.this,WaitingActivity.class);
        //서버로 id 보내주기 ChannelListActivity.id
        //서버에서 방 번호 가져오기
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("make_member",id);
            jsonObject.put("title",editTitle.getText());
            jsonObject.put("pwd",editPW.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("test",id);
        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/room")
                .contentType("application/json; charset=utf-8")
                .type("POST")
                .data(jsonObject.toString())
                .context(this)
                .success(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        String result=objects[0].toString();
                        waitingIntent.putExtra("RoomNum",Integer.parseInt(result));
                        startActivity(waitingIntent);
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        Log.d("test","서버 통신 에러");
                    }
                }));
    }
    public void MakeCancel(View view) //activity_make_channel 의 btn_cancel
    {
        finish();
    }

}
