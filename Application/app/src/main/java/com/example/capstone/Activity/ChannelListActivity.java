package com.example.capstone.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.v4.content.ContextCompat;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.capstone.Adapter.ListViewAdapter;
import com.example.capstone.Popup.SettingPopup;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;


public class ChannelListActivity extends AppCompatActivity {

    TextView editSearch;
    ListView listView;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        editSearch=(TextView)findViewById(R.id.edit_search);
        Intent intent=new Intent(this.getIntent());
        //값 받아오기
        //String s=intent.getExtras().getString("key");
        //로그찍기
        //Log.d("debug", s);

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listView = (ListView) findViewById(R.id.List);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent waitingIntent=new Intent(ChannelListActivity.this,WaitingActivity.class);
                waitingIntent.putExtra("RoomNum",1);
                startActivity(waitingIntent);
            }
        });
    }

    public void waiting(View v){
        Intent goWaiting=new Intent(ChannelListActivity.this,WaitingActivity.class);
        startActivity(goWaiting);
    }

    public void MakeChannel(View v) {
        Intent goMake = new Intent(ChannelListActivity.this, MakeChannelActivity.class);
        //값보내기
        //       goList.putExtra("key", editID.getText().toString());
        startActivity(goMake);
    }
    public void Setting(View v) {
        Intent goSetting = new Intent(ChannelListActivity.this, SettingPopup.class);
        //값보내기
        //       goList.putExtra("key", editID.getText().toString());
        startActivity(goSetting);
    }

    public void search(View v)//검색 버튼 누르면 시작
    {
        /*검색어가 포함된 방들의 타이틀, 방 만든사람, 방번호 다 가져와야함*/
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("title",editSearch.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/room/roomlist")
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
                        String title,makeMember;
                        int roomID;
                        /*try {
                            for(int i=0;i<array.length();i++) {
                                JSONObject jo = array.getJSONObject(0);
                                title = jo.get("title").toString();
                                makeMember=jo.get("make_member").toString();
                                roomID=Integer.parseInt(jo.get("room_id").toString());
                                adapter.addItem(ContextCompat.getDrawable(ChannelListActivity.this, R.drawable.main),
                                        ContextCompat.getDrawable(ChannelListActivity.this, R.drawable.green),
                                        title, makeMember, roomID) ;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                        try {

                            JSONArray array=new JSONArray(objects[0].toString());
                            for(int i=0;i<array.length();i++){
                                JSONObject jo=array.getJSONObject(i);
                                title = jo.get("title").toString();
                                makeMember=jo.get("make_member").toString();
                                Log.d("test",makeMember);
                                roomID=Integer.parseInt(jo.get("room_id").toString());
                                adapter.addItem(ContextCompat.getDrawable(ChannelListActivity.this, R.drawable.main),
                                        ContextCompat.getDrawable(ChannelListActivity.this, R.drawable.green),
                                        title, makeMember, roomID) ;
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
}
