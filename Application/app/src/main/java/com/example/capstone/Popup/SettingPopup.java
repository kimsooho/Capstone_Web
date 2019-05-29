package com.example.capstone.Popup;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.capstone.R;

public class SettingPopup extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_setting);
    }
    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }


}
