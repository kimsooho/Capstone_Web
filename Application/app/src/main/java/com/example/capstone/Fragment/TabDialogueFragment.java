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
                .context(getActivity())
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
                }));


        // 첫 번째 아이템 추가.

        return v;
    }

}
