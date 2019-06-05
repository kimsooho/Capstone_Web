package com.example.capstone.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ListView;

import com.example.capstone.Activity.ConferenceActivity;
import com.example.capstone.Adapter.DialogueViewAdapter;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

public class TabDialogueFragment extends Fragment {
    public TabDialogueFragment() {
    }

    DialogueViewAdapter adapter;
    public int roomNum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomNum = getArguments().getInt("num");
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ListView listview;
        // Adapter 생성
        adapter = new DialogueViewAdapter();
        View v = inflater.inflate(R.layout.tab_dialogue_item, container, false);
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) v.findViewById(R.id.close_listview);
        listview.setAdapter(adapter);

        //서버에 방전호 전송 모든 데이터 받기
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("room_id", roomNum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/room/chat")
                .contentType("application/json; charset=utf-8")
                .type("POST")
                .data(jsonObject.toString())
                .dataType("json")
                .context(getActivity())
                .success(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        JSONArray array = (JSONArray) objects[0];

                        for (int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject object = array.getJSONObject(i);
                                String now = object.getString("chat_date");
                               Log.d("debug", now);
                                 String date = now.substring(11, 19);
                                adapter.addDialogue(object.getString("member_id"), date, object.getString("contents"));
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        Log.d("test1", objects[0].toString());
                    }
                }));

        // 첫 번째 아이템 추가.

        return v;
    }

}
