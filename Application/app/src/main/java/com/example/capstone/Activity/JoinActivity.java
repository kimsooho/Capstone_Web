package com.example.capstone.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

public class JoinActivity extends Activity {

    TextView editID,editPW;
    Button btnCheckID;
    boolean check=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        Button btnJoin=(Button)findViewById(R.id.btn_joinStart);
        btnCheckID=(Button)findViewById(R.id.button3);
        editID=(TextView)findViewById(R.id.edit__id);
        editPW=(TextView)findViewById(R.id.edit_pw);
        Intent intent=new Intent(this.getIntent());


        btnCheckID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkID(v);
            }
        });
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(v);
            }
        });
        //값 받아오기
        //String s=intent.getExtras().getString("key");
        //로그찍기
        //Log.d("debug", s);

    }

    public void checkID(View view){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userID",editID.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/member/checkid")
                .contentType("application/json; charset=utf-8")
                .type("POST")
                .data(jsonObject.toString())
                .context(this)
                .success(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        String result=objects[0].toString();
                        if(result.equals("success")){
                            Toast.makeText(JoinActivity.this,"중복체크완료",Toast.LENGTH_LONG).show();
                            check=true;
                        }
                        else{
                            Toast.makeText(JoinActivity.this,"사용할 수 없는 ID입니다.",Toast.LENGTH_LONG).show();
                            check=false;
                        }
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        Log.d("test","서버 통신 에러");
                    }
                }));
    }

    public void start(View view){
        if(!check) return;
        final Intent intent=new Intent(JoinActivity.this,LoginActivity.class);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userID",editID.getText());
            jsonObject.put("pwd",editPW.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/member")
                .contentType("application/json; charset=utf-8")
                .type("POST")
                .data(jsonObject.toString())
                .context(this)
                .success(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        Log.d("test","가입성공");
                        startActivity(intent);
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
}
