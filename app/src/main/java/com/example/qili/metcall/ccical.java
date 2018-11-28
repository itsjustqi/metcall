package com.example.qili.metcall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ccical extends AppCompatActivity {

    private Button ccical_button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccical);

        Intent intent = getIntent();
        int age_triage = intent.getIntExtra("age", 0);

        ccical_button = (Button) findViewById(R.id.calcci);
        ccical_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ccical.this, triage.class);
                startActivity(intent);
            }
        });
    }
        public void onClick(View v) {
    }
}
