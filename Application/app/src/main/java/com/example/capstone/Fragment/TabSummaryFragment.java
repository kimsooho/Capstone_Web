package com.example.capstone.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.Activity.ChannelListActivity;
import com.example.capstone.PreferenceUtil;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.AjaxTask;
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
        View v = inflater.inflate(R.layout.tab_summary_item, container, false);


        textSummary = v.findViewById(R.id.text_summary);
        textSummary.setMovementMethod(new ScrollingMovementMethod());
        //서버에 설정값 전송 후 데이터 받기
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("roomid", roomNum);
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
                        String temp = (String)objects[0];
                        temp = temp.substring(1,temp.length()-1);
                        Log.d("debug",temp);
                        String[] result = temp.split(",");

                        for(int i=0; i<result.length; i++)
                        {
                            textSummary.append(result[i].toString().substring(1,result[i].length()-1)+"\n\n");
                            Log.d("chat",result[i].toString().substring(1,result[i].length()-1));
                        }

                        if(textSummary.getText().toString().equals("\n\n"))
                        {
                            Toast.makeText(getContext(),"대화 내용이 부족하여 요약 할 수 없습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        if((int)objects[1] == 0)
                        {
                            Toast.makeText(getContext(),"대화 내용이 부족하여 요약 할 수 없습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }));

        return v;

    }
}
