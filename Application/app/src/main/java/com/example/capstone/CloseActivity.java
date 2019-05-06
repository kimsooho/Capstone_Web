package com.example.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//회의종료 화면
public class CloseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);
        Button btnIntent=(Button)findViewById(R.id.button4);
        btnIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회의록 or 채널리스트 화면으로 이동할 수 있어야 함
                //지금은 임시로 회의록 화면이동만 넣음
                Intent intent=new Intent(CloseActivity.this,NoteActivity.class);
                startActivity(intent);
            }
        });
    }
}
