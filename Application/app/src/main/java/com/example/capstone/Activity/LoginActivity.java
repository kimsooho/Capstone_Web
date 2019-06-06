package com.example.capstone.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
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
    String id, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},0);

        }

        editID = (EditText) findViewById(R.id.edit_id);
        editPW = (EditText) findViewById(R.id.edit_pw);


    }

    public void LoginClick(View v) {

        final Intent goList = new Intent(LoginActivity.this, ChannelListActivity.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", editID.getText().toString());
            jsonObject.put("pwd", editPW.getText().toString());
            Log.d("test", editID.getText().toString());

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

                            goList.putExtra("userID", editID.getText().toString());

                            startActivity(goList);
                        } else {
                            Toast.makeText(LoginActivity.this, "userID / PW를 확인해주세요.", Toast.LENGTH_SHORT).show();
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

    public void JoinusClick(View v) {
        Intent goJoin = new Intent(LoginActivity.this, JoinActivity.class);
        startActivity(goJoin);
    }
}
