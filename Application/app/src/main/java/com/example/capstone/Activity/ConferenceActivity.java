package com.example.capstone.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.InterruptedByTimeoutException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

//회의화면
public class ConferenceActivity extends AppCompatActivity implements View.OnClickListener, SpeechRecognizeListener {
    public static Activity activity;
    public int roomNum;
    public String userID, makeMember;

    public LinearLayout linearLayout;
    public Button btnStop, btnSay;

    ListView listview;
    DialogueViewAdapter adapter;

    Boolean authority;

    DialogueThread dt;
    MyHandler mh;


    private SpeechRecognizerClient client;
    private static final int REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE = 0;
    private static final int REFRESH_CHAT = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference);
        activity = ConferenceActivity.this;
        linearLayout = (LinearLayout) findViewById(R.id.control_layout);
        btnStop = (Button) findViewById(R.id.btn_stop);
        //주요 권한 사용자에게 다시 체크 받음
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE);
            } else {
                // 유저가 거부하면서 다시 묻지 않기를 클릭.. 권한이 없다고 유저에게 직접 알림.
            }
        } else {
            //startUsingSpeechSDK();
        }

        // SDK 초기화
        SpeechRecognizerManager.getInstance().initializeLibrary(this);

        // 클라이언트 생성 - 마이이크 아이콘에 동작하도록 하자.
        //SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().setServiceType(SpeechRecognizerClient.SERVICE_TYPE_WEB);

        btnSay = (Button) findViewById(R.id.btn_say);

        //이전 액티비티 종료

        Intent intent = getIntent();
        roomNum = intent.getExtras().getInt("RoomNum");


        if (intent.getExtras().getBoolean("where")) { //채널 생성 액티비티에서 왓으면 생성 액티비티 종료
            makeMember = intent.getStringExtra("makeMember"); //채널생성
            userID = intent.getStringExtra("makeMember"); //채널생성
            MakeChannelActivity makeChannelActivity = (MakeChannelActivity) MakeChannelActivity.createChannelActivity;
            makeChannelActivity.finish();
        } else//일반 접속시 패스워드 팝업 종료
        {
            userID = intent.getStringExtra("userID");
            makeMember = intent.getStringExtra("makeMember"); //채널생성
            PasswordPopup passwordPopup = (PasswordPopup) PasswordPopup.passwordPopup;
            passwordPopup.finish();
        }

        if (!userID.equals(makeMember)) {
            linearLayout.removeView(btnStop);
            authority = false;
        } else {
            authority = true;
        }

        // Adapter 생성
        adapter = new DialogueViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView_dialogue);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

            }
        });

        //다이얼로그 스레드 & 핸들러 객체 생성
        dt = new DialogueThread();
        mh = new MyHandler();

        dt.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.clear();
        adapter.notifyDataSetChanged();
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
                .context(ConferenceActivity.this)
                .success(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        JSONArray array = (JSONArray) objects[0];

                        for (int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject object = array.getJSONObject(i);
                                String now = object.getString("chat_date");
                                String date1 = now.substring(0, 10);
                                String date2 = now.substring(11, 19);
                                String date = date1 + " " + date2;
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

        listview.setSelection(adapter.getCount() - 1);
        setButtonsStatus(true);

    }

    public void StopCon(View v)
    {
        Intent goClose = new Intent(ConferenceActivity.this, CloseActivity.class);
        goClose.putExtra("RoomNum", roomNum);
        goClose.putExtra("status", true);
        startActivity(goClose);
    }

    public void checkPeople_con(View v) { //btn_people_conference
        Intent goCheck = new Intent(ConferenceActivity.this, PeoplePopup.class);
        goCheck.putExtra("RoomNum", roomNum);
        startActivity(goCheck);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //서버에게 사용자가 나감을 알려야함
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("room_id", roomNum);
            jsonObject.put("member_id", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/join/roomout") //0입 1나감
                .contentType("application/json; charset=utf-8")
                .type("POST")
                .data(jsonObject.toString())
                .dataType("json")
                .context(ConferenceActivity.this)
                .success(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        Log.d("test1", objects[0].toString());
                    }
                }));

        // API를 더이상 사용하지 않을 때 finalizeLibrary()를 호출한다.
        SpeechRecognizerManager.getInstance().finalizeLibrary();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        this.finish();
    }

    //상황에 따라 버튼을 사용가능할지 불가능하게 할지 설정한다.
    private void setButtonsStatus(boolean enabled) {
        findViewById(R.id.btn_say).setEnabled(enabled);
        Log.d("test1", authority.toString());
        if (authority)
            findViewById(R.id.btn_stop).setEnabled(enabled);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String serviceType = SpeechRecognizerClient.SERVICE_TYPE_WEB;
        Log.i("MainActivity", "ServiceType : " + serviceType);

        //음성인식 시작 버튼-마이크 버튼
        if (id == R.id.btn_say) {
            SpeechRecognizerClient.Builder builder = new SpeechRecognizerClient.Builder().setServiceType(serviceType);
            client = builder.build();

            client.setSpeechRecognizeListener(this);
            client.startRecording(true);

            Toast.makeText(this, "발언해주세요.", Toast.LENGTH_SHORT).show();
            findViewById(R.id.btn_say).setEnabled(false);
            if (authority)
                findViewById(R.id.btn_stop).setEnabled(false);
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

        //현재시간 설정
        final long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final String formatDate = sdfNow.format(date);

        for (int i = 0; i < texts.size(); i++) {
            builder.append(texts.get(i));
            builder.append(" (");
            builder.append(confs.get(i).intValue());
            builder.append(")\n");
        }

        final Activity activity = this;
        if (activity.isFinishing()) return;
        adapter.addDialogue(userID, formatDate, texts.get(0));
        adapter.notifyDataSetChanged();
        listview.setSelection(adapter.getCount() - 1);
        setButtonsStatus(true);
        String encodeStr = "";
        try {
            encodeStr = URLEncoder.encode(texts.get(0), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contents", encodeStr);
            jsonObject.put("roomid", roomNum);
            jsonObject.put("memberid", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/users/insert")
                .contentType("application/json; charset=utf-8")
                .type("POST")
                .data(jsonObject.toString())
                .dataType("text")
                .context(ConferenceActivity.this)
                .success(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        String result = objects[0].toString();
                        Log.d("test1", objects[0].toString());
                    }
                })
                .error(new Function() {
                    @Override
                    public void invoke($ $, Object... objects) {
                        Log.d("test1", objects[0].toString());
                    }
                }));

    }

    @Override
    public void onAudioLevel(float audioLevel) {

    }

    @Override
    public void onFinished() {

    }

    class DialogueThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {

                Message msg = mh.obtainMessage();

                msg.what = REFRESH_CHAT;
                mh.sendMessage(msg);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case REFRESH_CHAT:
                    final JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("room_id", roomNum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/room/count")
                            .contentType("application/json; charset=utf-8")
                            .type("POST")
                            .data(jsonObject.toString())
                            .dataType("json")
                            .context(ConferenceActivity.this)
                            .success(new Function() {
                                @Override
                                public void invoke($ $, Object... objects) {
                                    JSONArray array= (JSONArray) objects[0];
                                    JSONObject object= null;
                                    try {
                                        object = array.getJSONObject(0);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    int temp = 0;
                                    try {
                                        temp = Integer.parseInt(object.getString("cnt"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    final int totalChatCnt = temp;
                                    final int curChatCnt = adapter.getCount();
                                    if (totalChatCnt == curChatCnt) return;
                                    else {
                                        $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/room/chat")
                                                .contentType("application/json; charset=utf-8")
                                                .type("POST")
                                                .data(jsonObject.toString())
                                                .dataType("json")
                                                .context(ConferenceActivity.this)
                                                .success(new Function() {
                                                    @Override
                                                    public void invoke($ $, Object... objects) {
                                                        int dif = totalChatCnt - curChatCnt;
                                                        JSONArray array = (JSONArray) objects[0];

                                                        for (int i = totalChatCnt - dif; i < array.length(); i++) {
                                                            try {
                                                                JSONObject object = array.getJSONObject(i);
                                                                String now = object.getString("chat_date");
                                                                String date1 = now.substring(0, 10);
                                                                String date2 = now.substring(11, 19);
                                                                String date = date1 + " " + date2;
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
                                    }
                                }
                            })
                            .error(new Function() {
                                @Override
                                public void invoke($ $, Object... objects) {
                                    Log.d("test1", objects[0].toString());
                                }
                            }));
                    /////////////////////////////////////////////////////////////////////////////////////////////////////
                    //방상태 받기
                    try {
                        jsonObject.put("room_id", roomNum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    $.ajax(new AjaxOptions().url("http://emperorp.iptime.org/room/count")
                            .contentType("application/json; charset=utf-8")
                            .type("POST")
                            .data(jsonObject.toString())
                            .dataType("text")
                            .context(ConferenceActivity.this)
                            .success(new Function() {
                                @Override
                                public void invoke($ $, Object... objects) {
                                    //JSONArray array=(JSONArray)objects[0];
                                    //JSONArray는 JSONObject로 구성
                                    //JSONArray.get(배열 인덱스)으로 각 오브젝트 전체를 구할 수 있음
                                    //JSONObject.get(json key)로 원하는 값만 구할 수 있음
                                    String status;
                                    try {
                                        JSONArray array = new JSONArray(objects[0].toString());
                                            JSONObject jo = array.getJSONObject(0);
                                            status = jo.get("status").toString();

                                            if(status.equals("1"))
                                            {
                                                btnStop.performClick();
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
                    break;
            }
        }
    }
}
