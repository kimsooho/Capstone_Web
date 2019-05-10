package com.example.capstone.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

public class LoginActivity extends AppCompatActivity {
    EditText editID, editPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editID = (EditText)findViewById(R.id.edit_id);
        editPW = (EditText)findViewById(R.id.edit_pw);
    }
    public void LoginClick(View v)
    {
        Log.d("test","클릭");
        Intent goList = new Intent(LoginActivity.this, ChannelListActivity.class);
        //값보내기
        //       goList.putExtra("key", editID.getText().toString());
        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/member")
                .type("GET")
                .dataType("json")
                .context(this)
                .success(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        JSONArray array=(JSONArray)objects[0];
                        //JSONArray는 JSONObject로 구성
                        //JSONArray.get(배열 인덱스)으로 각 오브젝트 전체를 구할 수 있음
                        //JSONObject.get(json key)로 원하는 값만 구할 수 있음
                        try {
                            JSONObject jo=array.getJSONObject(0);
                            Log.d("test",jo.get("u_id").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        Log.d("test","error");
                    }
                }));
        startActivity(goList);
    }
    public void JoinusClick(View v)
    {
        Intent goJoin = new Intent(LoginActivity.this, JoinActivity.class);
        //값보내기
        //       goList.putExtra("key", editID.getText().toString());
        startActivity(goJoin);
    }
}
