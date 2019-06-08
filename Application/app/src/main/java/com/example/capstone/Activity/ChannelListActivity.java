package com.example.capstone.Activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.v4.content.ContextCompat;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.Adapter.ListViewAdapter;
import com.example.capstone.Item.ListViewItem;
import com.example.capstone.Popup.PasswordPopup;
import com.example.capstone.Popup.SettingPopup;
import com.example.capstone.PreferenceUtil;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;


public class ChannelListActivity extends AppCompatActivity {

    static public ChannelListActivity channelListActivity;
    String userID; //사용자 id

    ListViewAdapter adapter;

    TextView editSearch;
    ListView listView;
    CheckBox check_before;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        channelListActivity = ChannelListActivity.this;

        editSearch = (TextView) findViewById(R.id.edit_search);
        editSearch = (TextView) findViewById(R.id.edit_search);
        check_before = (CheckBox) findViewById(R.id.check_pre);

        //설정 초기값 저장
        //값 저장
        if (PreferenceUtil.getInstance(this).getIntExtra("SettingValue") == 101) {
            PreferenceUtil.getInstance(this).putIntExtra("SettingValue", 20);
        }
        //라인, 퍼센트 저장 0-퍼센트
        if (PreferenceUtil.getInstance(this).getIntExtra("Division") == 101) {
            PreferenceUtil.getInstance(this).putIntExtra("Division", 0);
        }

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listView = (ListView) findViewById(R.id.List);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                ListViewItem listViewItem = (ListViewItem) adapter.getItem(position);
                if (listViewItem.getStatus() == 0) //방 상태에 따라서 waitingActivity로 가거나 이미 종료한 방이면 결과를 볼수있는 CloseActivity로 가야함
                {
                    Intent passwordIntent = new Intent(ChannelListActivity.this, PasswordPopup.class);
                    passwordIntent.putExtra("userID", userID);
                    passwordIntent.putExtra("RoomNum", listViewItem.getRoomNum());
                    passwordIntent.putExtra("makeMember", listViewItem.getMakeMember());
                    startActivity(passwordIntent);
                } else {
                    Intent closeIntent = new Intent(ChannelListActivity.this, CloseActivity.class);
                    closeIntent.putExtra("RoomNum", listViewItem.getRoomNum());
                    closeIntent.putExtra("status", false);
                    startActivity(closeIntent);
                }
            }
        });
    }

    public void MakeChannel(View v) {
        Intent goMake = new Intent(ChannelListActivity.this, MakeChannelActivity.class);
        goMake.putExtra("userID", userID);
        //값보내기
        startActivity(goMake);
    }

    public void Setting(View v) {
        Intent goSetting = new Intent(ChannelListActivity.this, SettingPopup.class);
        //값보내기
        startActivity(goSetting);
    }

    public void search(View v)//검색 버튼 누르면 시작
    {
        adapter.getListViewItemList().removeAll(adapter.getListViewItemList());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", editSearch.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (check_before.isChecked()) {
            $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/room/roomlistfalse")
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
                            String title, makeMember;
                            Log.d("test1", "hello");
                            int roomID, roomStatus;
                            try {
                                JSONArray array = new JSONArray(objects[0].toString());
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jo = array.getJSONObject(i);
                                    title = jo.get("title").toString();
                                    makeMember = jo.get("make_member").toString();
                                    Log.d("test", makeMember);
                                    roomID = Integer.parseInt(jo.get("room_id").toString());
                                    roomStatus = Integer.parseInt(jo.get("status").toString());
                                    //상태도 받아와야함
                                    adapter.addItem(ContextCompat.getDrawable(ChannelListActivity.this, R.drawable.main),
                                            ContextCompat.getDrawable(ChannelListActivity.this, R.drawable.red),
                                            title, makeMember, roomID, roomStatus);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (adapter.getCount() == 0) { //위 에이잭스 동기로 해야할듯
                                    Toast.makeText(ChannelListActivity.this, "검색된 방이 없습니다!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    })
                    .error(new Function() {
                        @Override
                        public void invoke($ $, Object... objects) {
                            Log.d("test", "서버 통신 에러");
                        }
                    })
                    .complete(new Function() {
                        @Override
                        public void invoke($ $, Object... objects) {
                            if (adapter.getCount() == 0) { //위 에이잭스 동기로 해야할듯
                                Toast.makeText(ChannelListActivity.this, "검색된 방이 없습니다!", Toast.LENGTH_SHORT).show();
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }));

        } else {
            Log.d("debug", "룸리스트 트루");
            $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/room/roomlisttrue")
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
                            String title, makeMember;
                            int roomID, roomStatus;
                            try {
                                JSONArray array = new JSONArray(objects[0].toString());
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jo = array.getJSONObject(i);
                                    title = jo.get("title").toString();
                                    makeMember = jo.get("make_member").toString();
                                    roomID = Integer.parseInt(jo.get("room_id").toString());
                                    roomStatus = Integer.parseInt(jo.get("status").toString()); //상태 받아와서 상태값도 저장
                                    adapter.addItem(ContextCompat.getDrawable(ChannelListActivity.this, R.drawable.main),
                                            ContextCompat.getDrawable(ChannelListActivity.this, R.drawable.green), //룸상태 0
                                            title, makeMember, roomID, roomStatus);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    })
                    .error(new Function() {
                        @Override
                        public void invoke($ $, Object... objects) {
                            Log.d("test", "서버 통신 에러");
                        }
                    }).complete(new Function() {
                        @Override
                        public void invoke($ $, Object... objects) {
                            if (adapter.getCount() == 0) { //위 에이잭스 동기로 해야할듯
                                Toast.makeText(ChannelListActivity.this, "검색된 방이 없습니다!", Toast.LENGTH_SHORT).show();
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.getListViewItemList().clear();
        adapter.notifyDataSetChanged();
        editSearch.setText("");
    }
}
