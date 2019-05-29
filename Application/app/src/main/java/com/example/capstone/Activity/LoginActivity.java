package com.example.capstone.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        final Intent goList = new Intent(LoginActivity.this, ChannelListActivity.class);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",editID.getText());
            jsonObject.put("pwd",editPW.getText());
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
                        String result=objects[0].toString();
                        Log.d("test",objects[0].toString());
                        if(result.equals("success")){
                            goList.putExtra("ID", editID.getText());
                            startActivity(goList);
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "ID / PW를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        /*JSONArray array=(JSONArray)objects[0];
                        //JSONArray는 JSONObject로 구성
                        //JSONArray.get(배열 인덱스)으로 각 오브젝트 전체를 구할 수 있음
                        //JSONObject.get(json key)로 원하는 값만 구할 수 있음
                        try {
                            JSONObject jo=array.getJSONObject(0);
                            Log.d("test", jo.);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        Log.d("test","서버 통신 에러");
                    }
                }));
    }
    public void JoinusClick(View v)
    {
        Intent goJoin = new Intent(LoginActivity.this, JoinActivity.class);
        //값보내기
        //       goList.putExtra("key", editID.getText().toString());
        startActivity(goJoin);
    }
}
