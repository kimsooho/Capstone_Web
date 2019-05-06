package com.example.capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v4.content.ContextCompat;
import android.widget.AdapterView;
import android.widget.ListView;

public class ChannelListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
        Intent intent=new Intent(this.getIntent());
        //값 받아오기
        //String s=intent.getExtras().getString("key");
        //로그찍기
        //Log.d("debug", s);

        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.List);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.main), ContextCompat.getDrawable(this, R.drawable.green),
                "노터 마케팅 1팀", 6,"최유진 조영태 등등") ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                //이부분에 채널 목록을 클릭했을때 이벤트 작성하면됨    채널목록->회의대기 상태
                waiting(v);
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
}
