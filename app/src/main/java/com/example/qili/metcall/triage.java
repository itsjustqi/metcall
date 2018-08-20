package com.example.qili.metcall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


public class triage extends result implements View.OnClickListener {

        private Button triagebutton = null;
        private Spinner s;
        private ArrayAdapter<CharSequence> adapter = null;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_triage);
            triagebutton = (Button) findViewById(R.id.triageb);
            triagebutton.setOnClickListener(this);
            s = (Spinner)findViewById(R.id.operations);
            adapter = ArrayAdapter.createFromResource(this, R.array.operations, android.R.layout.simple_spinner_item);
            s.setAdapter(adapter);
        }

        public void onClick(View v) {
            RadioGroup gender_group = (RadioGroup)findViewById(R.id.gender_group);
            int checkRadioButtonId_gender = gender_group.getCheckedRadioButtonId();
            int gender = 0;
            switch (checkRadioButtonId_gender) {
                case R.id.male:
                    gender = 1;
                    break;
                case R.id.female:
                    gender = 0;
                    break;
            }

            RadioGroup smoking_group = (RadioGroup)findViewById(R.id.smoking_group);
            int checkRadioButtonId_smoking = smoking_group.getCheckedRadioButtonId();
            int smoking = 0;
            switch (checkRadioButtonId_gender) {
                case R.id.smoking_n:
                    smoking = 0;
                    break;
                case R.id.smoking_y:
                    smoking = 1;
                    break;
                case R.id.smoking_issues:
                    smoking = 2;
                    break;
            }

            RadioGroup alcohol_group = (RadioGroup)findViewById(R.id.alcohol_group);
            int checkRadioButtonId_alcohol = alcohol_group.getCheckedRadioButtonId();
            int alcohol = 0;
            switch (checkRadioButtonId_gender) {
                case R.id.alcohol_n:
                    alcohol = 0;
                    break;
                case R.id.alcohol_y:
                    alcohol = 1;
                    break;
                case R.id.alcohol_issues:
                    alcohol= 2;
                    break;
            }

            RadioGroup admsourc_group = (RadioGroup)findViewById(R.id.admsourc_group);
            int checkRadioButtonId_admsourc = admsourc_group.getCheckedRadioButtonId();
            int admsourc = 0;
            switch (checkRadioButtonId_gender) {
                case R.id.admsourc_h:
                    admsourc = 0;
                    break;
                case R.id.admsourc_n:
                    admsourc = 1;
                    break;
                case R.id.admsourc_t:
                    admsourc = 2;
                    break;
                case R.id.admsourc_b:
                    admsourc = 3;
                    break;
            }

            RadioGroup emnone_group = (RadioGroup)findViewById(R.id.emnone_group);
            int checkRadioButtonId_emnone = alcohol_group.getCheckedRadioButtonId();
            int emnone = 0;
            switch (checkRadioButtonId_gender) {
                case R.id.emnone_n:
                    alcohol = 0;
                    break;
                case R.id.emnone_y:
                    alcohol = 1;
                    break;
            }

            RadioGroup anaesthetic_group = (RadioGroup)findViewById(R.id.anaesthetic_group);
            int checkRadioButtonId_anaesthetic = admsourc_group.getCheckedRadioButtonId();
            int anaesthetic = 0;
            switch (checkRadioButtonId_gender) {
                case R.id.anaesthetic_GA:
                    anaesthetic = 0;
                    break;
                case R.id.anaesthetic_RA:
                    anaesthetic= 1;
                    break;
                case R.id.anaesthetic_SED:
                    anaesthetic = 2;
                    break;
                case R.id.anaesthetic_LA:
                    anaesthetic = 3;
                    break;
                case R.id.anaesthetic_NIL:
                    anaesthetic = 4;
                    break;
            }

            String operations = s.getSelectedItem().toString();

            EditText age= (EditText)findViewById(R.id.age);
            String age_triage = age.getText().toString();
            int age_int = new Integer(age_triage).intValue();

            Intent intent = new Intent();
            intent.setClass(triage.this, result.class);

            intent.putExtra("gender", gender);
            intent.putExtra("smoking", smoking);
            intent.putExtra("alcohol", alcohol);
            intent.putExtra("admsourc", admsourc);
            intent.putExtra("emnone", emnone);
            intent.putExtra("operations",operations);
            intent.putExtra("anaesthetic",anaesthetic);
            intent.putExtra("age",age_int);

        startActivity(intent);
        }
}
