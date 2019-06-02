package com.example.capstone.Popup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.capstone.Activity.ChannelListActivity;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

public class PeoplePopup extends Activity {

    public int roomNum;
    Vector<String> List;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_people);

        Intent intent = getIntent();
        roomNum = intent.getExtras().getInt("RoomNum");
        List = new Vector<String>();
        //String[] List;
        /*현재 이방에 참여중인 인원 가져와서 List Vector에 넣기*/


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("room_id", roomNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/join/joinusers")
                .contentType("application/json; charset=utf-8")
                .type("POST")
                .data(jsonObject.toString())
                .context(this)
                .success(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        //JSONArray array=(JSONArray)objects[0];
                        //JSONArray는 JSONObject로 구성
                        //JSONArray.get(배열 인덱스)으로 각 오브젝트 전체를 구할 수 있음
                        //JSONObject.get(json key)로 원하는 값만 구할 수 있음
                        String userID;
                        try {

                            JSONArray array = new JSONArray(objects[0].toString());
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jo = array.getJSONObject(i);
                                userID = jo.get("member_id").toString();
                                //  Log.d("debug", userID);
                                List.add(userID);
                            }
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        Log.d("debug", "서버 통신 에러");
                    }
                }));


        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, List);

        ListView listview = (ListView) findViewById(R.id.listView_people);
        listview.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }


}

