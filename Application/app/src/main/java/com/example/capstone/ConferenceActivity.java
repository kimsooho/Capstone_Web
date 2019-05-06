package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

//회의화면
public class ConferenceActivity extends AppCompatActivity {


    DialogueViewAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference);
        //이전 액티비티 종료
        WaitingActivity waitingActivity = (WaitingActivity)WaitingActivity.activity;
        waitingActivity.finish();
        ListView listview ;


        // Adapter 생성
        adapter = new DialogueViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView_dialogue);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addDialogue("조영태","04/10","ㅇㅇㅇ"); ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //이부분에 채널 목록을 클릭했을때 이벤트 작성하면됨    채널목록->회의대기 상태
               // waiting(v);
                // get item 해당 채널 데이터 받아오는 구문  수정 필요 일단 지우지망
/*            ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                Drawable iconDrawable = item.getIcon() ;
*/

                //Log.d("debug", "dd");
            }
        });
    }

    public void stopConference(View v) {

        Log.d("debug","znffkr");
        EditText edit = (EditText) findViewById(R.id.editText4);

        adapter.addDialogue("조영태","04/10",edit.getText().toString()); ;

        adapter.notifyDataSetChanged(); //갱신
        /*Intent goMake = new Intent(ChannelListActivity.this, MakeChannelActivity.class);
        //값보내기
        //       goList.putExtra("key", editID.getText().toString());
        startActivity(goMake);*/
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
