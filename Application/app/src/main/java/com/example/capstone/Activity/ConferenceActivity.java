package com.example.capstone.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.Adapter.DialogueViewAdapter;
import com.example.capstone.Popup.PasswordPopup;
import com.example.capstone.Popup.PeoplePopup;
import com.example.capstone.R;
import com.kakao.sdk.newtoneapi.SpeechRecognizeListener;
import com.kakao.sdk.newtoneapi.SpeechRecognizerClient;
import com.kakao.sdk.newtoneapi.SpeechRecognizerManager;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;

//회의화면
public class ConferenceActivity extends AppCompatActivity implements View.OnClickListener, SpeechRecognizeListener {
    public static Activity activity;
    public int roomNum;
    public String userID;

    DialogueViewAdapter adapter;

    private SpeechRecognizerClient client;
    private static final int REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE = 0;

    Button btnSay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference);
        activity = ConferenceActivity.this;

        //주요 권한 사용자에게 다시 체크 받음
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE);
            } else {
                // 유저가 거부하면서 다시 묻지 않기를 클릭.. 권한이 없다고 유저에게 직접 알림.
                Toast.makeText(this,"dd",Toast.LENGTH_LONG).show();
            }
        } else {
            //startUsingSpeechSDK();
        }

        // SDK 초기화
        SpeechRecognizerManager.getInstance().initializeLibrary(this);

        // 클라이언트 생성 - 마이이크 아이콘에 동작하도록 하자.
        //SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().setServiceType(SpeechRecognizerClient.SERVICE_TYPE_WEB);

        btnSay=(Button)findViewById(R.id.btn_say);


        setButtonsStatus(true);
        //서버에게 방번호와 사용자 아이디를 서버에게 보내줘서 서버에게 사용자가 방에 접속했다는것을 알려야함

        //이전 액티비티 종료

        Intent intent = getIntent();
        roomNum = intent.getExtras().getInt("RoomNum");
        userID = intent.getStringExtra("userID");

        if (intent.getExtras().getBoolean("where")) { //채널 생성 액티비티에서 왓으면 생성 액티비티 종료
            MakeChannelActivity makeChannelActivity = (MakeChannelActivity) MakeChannelActivity.createChannelActivity;
            makeChannelActivity.finish();
        }
        else//일반 접속시 패스워드 팝업 종료
        {
            PasswordPopup passwordPopup = (PasswordPopup) PasswordPopup.passwordPopup;
            passwordPopup.finish();
        }

        ListView listview;

        // Adapter 생성
        adapter = new DialogueViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView_dialogue);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addDialogue("조영태", "04/10", "ㅇㅇㅇ");


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

    public void stopConference(View v) {//btn_stop
        //서버에게 사용자 나감을 알려야함
        Intent goClose = new Intent(ConferenceActivity.this, CloseActivity.class);

        goClose.putExtra("RoomNum", roomNum);

        goClose.putExtra("status", true);
        startActivity(goClose);
    }

    public void checkPeople_con(View v) { //btn_people_conference
        Intent goCheck = new Intent(ConferenceActivity.this, PeoplePopup.class);
        //값보내기
        goCheck.putExtra("RoomNum", roomNum);
        startActivity(goCheck);
    }

    public void Say(View v) //btn_say
    {
        /*stt*/
/*
        adapter.addDialogue("조영태","04/10", "대화내용");
        adapter.notifyDataSetChanged(); //갱신
*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //서버에게 사용자가 나감을 알려야함
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void onDestroy(){
        super.onDestroy();

        // API를 더이상 사용하지 않을 때 finalizeLibrary()를 호출한다.
        SpeechRecognizerManager.getInstance().finalizeLibrary();
    }
    //상황에 따라 버튼을 사용가능할지 불가능하게 할지 설정한다.
    private void setButtonsStatus(boolean enabled) {
        findViewById(R.id.btn_say).setEnabled(enabled);
        findViewById(R.id.btn_stop).setEnabled(!enabled);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        String serviceType = SpeechRecognizerClient.SERVICE_TYPE_WEB;
        Log.i("MainActivity", "ServiceType : " + serviceType);

        //음성인식 시작 버튼-마이크 버튼
        if (id == R.id.btn_say){
            SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().setServiceType(serviceType);
            client = builder.build();

            client.setSpeechRecognizeListener(this);
            client.startRecording(true);

            Toast.makeText(this, "음성인식을 시작합니다.", Toast.LENGTH_SHORT).show();

            setButtonsStatus(false);
        }
    }
    @Override
    public void onReady() {
        Log.d("MainActivity", "모든 준비가 완료 되었습니다.");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("MainActivity", "말하기 시작 했습니다.");
    }

    @Override
    public void onEndOfSpeech() {
        Log.d("MainActivity", "말하기가 끝났습니다.");
    }

    @Override
    public void onError(int errorCode, String errorMsg) {

    }

    @Override
    public void onPartialResult(String partialResult) {

    }

    @Override
    public void onResults(Bundle results) {
        final StringBuilder builder = new StringBuilder();

        final ArrayList<String> texts = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);
        ArrayList<Integer> confs = results.getIntegerArrayList(SpeechRecognizerClient.KEY_CONFIDENCE_VALUES);


        for (int i = 0; i < texts.size(); i++){
            builder.append(texts.get(i));
            builder.append(" (");
            builder.append(confs.get(i).intValue());
            builder.append(")\n");
        }

        //모든 콜백함수들은 백그라운드에서 돌고 있기 때문에 메인 UI를 변경할려면 runOnUiThread를 사용해야 한다.
        final Activity activity = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(activity.isFinishing()) return;

                //tv_result.setText(texts.get(0));

                setButtonsStatus(true);
            }
        });
    }

    @Override
    public void onAudioLevel(float audioLevel) {

    }

    @Override
    public void onFinished() {

    }
}
