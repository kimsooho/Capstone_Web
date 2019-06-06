package com.example.capstone.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstone.Activity.ChannelListActivity;
import com.example.capstone.PreferenceUtil;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

public class TabSummaryFragment extends Fragment {

    TextView textSummary;
    public TabSummaryFragment() {
    }


    public int roomNum;
    public String divsion;
    public int setValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomNum = getArguments().getInt("num");
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        textSummary = getActivity().findViewById(R.id.text_summary);
        //서버에 설정값 전송 후 데이터 받기
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("room_id", roomNum);
            jsonObject.put("summtype", PreferenceUtil.getInstance(getActivity()).getIntExtra("Division")); //percentage(0) or line(1)
            jsonObject.put("ratio", PreferenceUtil.getInstance(getActivity()).getIntExtra("SettingValue"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/users/do")
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
                                Log.d("qq",jo.toString());
                                textSummary.append(jo.toString()+"\n");
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

        return inflater.inflate(R.layout.tab_summary_item, container, false);

    }
}
