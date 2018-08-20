package com.example.qili.metcall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class data extends AppCompatActivity {

    private Button button_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        button_data = (Button) findViewById(R.id.b_data_back);
        button_data.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(data.this, triage.class);
                startActivity(intent);
            }
        });

        button_data = (Button) findViewById(R.id.b_data_home);
        button_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(data.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
