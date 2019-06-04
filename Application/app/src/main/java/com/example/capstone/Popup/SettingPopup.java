package com.example.capstone.Popup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.capstone.Activity.ChannelListActivity;
import com.example.capstone.PreferenceUtil;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import self.philbrown.droidQuery.$;
import self.philbrown.droidQuery.AjaxOptions;
import self.philbrown.droidQuery.Function;

public class SettingPopup extends Activity {

    EditText editSetVale;

    RadioButton radioPer;
    RadioButton radioLine;
    RadioGroup radioGroup;


    String division; //line or percentage
    int summaryValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_setting);

        editSetVale = (EditText) findViewById(R.id.edit_set);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioPer = (RadioButton) findViewById(R.id.radio_per);
        radioLine = (RadioButton) findViewById(R.id.radio_line);

        division = PreferenceUtil.getInstance(this).getStringExtra("Division");
        summaryValue = PreferenceUtil.getInstance(this).getIntExtra("SettingValue");
        if (division.equals("line")) {
            radioLine.setChecked(true);
            radioPer.setChecked(false);
        } else if (division.equals("percentage")) {
            radioLine.setChecked(false);
            radioPer.setChecked(true);
        }
        Log.d("debug", "-----------------------------22------" + summaryValue);
        editSetVale.setText(Integer.toString(summaryValue));

    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    public void OK(View v) {
        try {
            summaryValue = Integer.parseInt(editSetVale.getText().toString());
        } catch (Exception e) {
            Toast.makeText(SettingPopup.this, "1~100사이 정수의 값을 입력해 주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        if (summaryValue < 1 || summaryValue > 100) {
            Toast.makeText(SettingPopup.this, "1~100사이의 값을 입력해 주세요", Toast.LENGTH_SHORT).show();
            editSetVale.setText("");
            return;
        }
        if (radioLine.isChecked()) {
            division = "line";
        }
        if (radioPer.isChecked()) {
            division = "percentage";
        }
        PreferenceUtil.getInstance(this).putIntExtra("SettingValue", summaryValue);
        PreferenceUtil.getInstance(this).putStringExtra("Division", division);

        this.finish();

    }


}
