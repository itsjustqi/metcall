package com.example.qili.metcall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class result extends AppCompatActivity {

    private TextView View1;
    private TextView View2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        View1 = (TextView)findViewById(R.id.CART);
        View2 = (TextView)findViewById(R.id.RF);

        Intent intent = getIntent();
        int gender_triage = intent.getIntExtra("gender", 0);
        int smoking_triage = intent.getIntExtra("smoking", 0);
        int alcohol_triage = intent.getIntExtra("alcohol", 0);
        int admsourc_triage = intent.getIntExtra("admsourc", 0);
        int emnone_triage = intent.getIntExtra("emnone", 0);
        int cci_triage = intent.getIntExtra("cci_admit",0);
        int threatre_mins = intent.getIntExtra("threatre_mins",0);
        int anaesthetic_triage = intent.getIntExtra("anaesthetic",0);
        int age_triage = intent.getIntExtra("age",0);

        String operations = intent.getStringExtra("operations");

        double probability_CART = 0;
        double probability_RF = 0;

        /* probability calculator of individual CART model */
        if ((age_triage>=15&&age_triage<20)||(age_triage>=25&&age_triage<75)){
            if(operations=="Foot and ankle surgery"||operations=="Knee arthroscopy"||operations=="Ligamentous/Meniscal surgery"||operations=="ORIF - Tibia/fibula/patella"||operations=="Other UL surgery/shoulder other (incl. arthroscopy)/hand surgery"||operations=="THR")
            {
                probability_CART = 0.17;
            }else{
                if(smoking_triage == 0){
                    if(age_triage<89){
                        probability_CART = 0.4;
                    }else{
                        probability_CART = 0.8;
                    }
                }else{
                    probability_CART = 0.69;
                }
            }
        }else{
            if(operations=="Foot and ankle surgery"||operations=="Hip Arthroscopy"||operations=="Knee arthroscopy"||operations=="Ligamentous/Meniscal surgery"||operations=="ORIF - Tibia/fibula/patella"||operations=="Other UL surgery/shoulder other (incl. arthroscopy)/hand surgery"){
                probability_CART = 0.41;
            }else{
                probability_CART = 0.82;
            }
        }

        /* Probability calculator of individual Naive Bayes model */
        if(age_triage>=85&&age_triage<90){
            probability_RF = 0.847;
        }else{
            if(age_triage>=70&&age_triage<75){
                probability_RF = 0.247;
            }else{
                if(age_triage>=80&&age_triage<85){
                    probability_RF = 0.797;
                }else{
                    if(age_triage>=40&&age_triage<45){
                        switch(alcohol_triage){
                            case 0: probability_RF = 0.054;
                            break;
                            case 1: probability_RF = 0.8;
                            break;
                            case 2: probability_RF = 0;
                            break;
                        }
                        }else{
                        if(age_triage>=90&&age_triage<95){probability_RF = 0.849;}
                        else{
                            if(age_triage>=75&&age_triage<80){
                                probability_RF = 0.754;
                            }else{
                                if(age_triage>=60&&age_triage<65){
                                    switch(cci_triage){
                                        case 0: probability_RF = 0;
                                        break;
                                        case 1: probability_RF = 0;
                                        break;
                                        case 2: probability_RF = 0.125;
                                        break;
                                        case 3: probability_RF = 0.239;
                                        break;
                                        case 4: probability_RF = 0.684;
                                        break;
                                        case 5: probability_RF = 0.286;
                                        break;
                                        case 6: probability_RF = 0.25;
                                        break;
                                        case 7: probability_RF = 0.857;
                                        break;
                                        case 8: probability_RF = 0.833;
                                        break;
                                        case 9: probability_RF = 1;
                                        break;
                                        case 10: probability_RF = 0;
                                        break;
                                        case 11: probability_RF = 0;
                                        break;
                                        case 14: probability_RF = 0;
                                        break;
                                    }
                                }else{
                                    if(age_triage>=25&&age_triage<30){
                                        probability_RF = 0.924;
                                    }else {
                                        if(age_triage>=20&&age_triage<25){
                                            switch(admsourc_triage){
                                                case 0: probability_RF = 0.04;
                                                break;
                                                case 1: probability_RF = 1;
                                                break;
                                                case 2: probability_RF = 0.935;
                                                break;
                                                case 3: probability_RF = 1;
                                                break;
                                            }
                                        }else{
                                            if(age_triage>=65&&age_triage<70){
                                                probability_RF = 0.268;
                                            }else{
                                                if(age_triage>=55&&age_triage<60){
                                                    switch(operations){
                                                        case "ORIF - DHS/Nail": probability_RF = 0.750;
                                                        break;
                                                        case "ORIF - Femur": probability_RF = 0.008;
                                                        break;
                                                        case "Other":switch (anaesthetic_triage){
                                                            case 0: probability_RF = 0.071;
                                                            break;
                                                            case 1: probability_RF = 0.765;
                                                            break;
                                                            case 2: probability_RF = 0;
                                                            break;
                                                            case 3: probability_RF = 0;
                                                            break;
                                                            case 4: probability_RF = 0;
                                                            break;
                                                        }
                                                        break;
                                                        case "ORIF - Tibia/fibula/patella": switch(smoking_triage){
                                                            case 0: probability_RF = 0.145;
                                                            break;
                                                            case 1: switch(gender_triage){
                                                                case 0: probability_RF = 1;
                                                                break;
                                                                case 1: probability_RF = 0;
                                                                break;
                                                            }
                                                            case 2: probability_RF = 0;
                                                        }
                                                        break;
                                                        case "Hemi-arthroplasty": probability_RF = 0.014;
                                                        break;
                                                        case "TKR/UKR": switch (anaesthetic_triage){
                                                            case 0: probability_RF = 0.012;
                                                            break;
                                                            case 1: probability_RF = 0.767;
                                                            break;
                                                            case 2: probability_RF = 0;
                                                            break;
                                                            case 3: probability_RF = 0;
                                                            break;
                                                            case 4: probability_RF = 0;
                                                            break;
                                                        }
                                                        break;
                                                        case "Decompressiv laminctomy/spinal fusion": switch (smoking_triage){
                                                            case 0: probability_RF = 0.280;
                                                            break;
                                                            case 1: probability_RF = 0.944;
                                                            break;
                                                            case 2: probability_RF = 0;
                                                            break;
                                                        }
                                                        break;
                                                        case "THR": switch (anaesthetic_triage){
                                                            case 0: switch(gender_triage){
                                                                case 0: probability_RF = 0.061;
                                                                break;
                                                                case 1: probability_RF = 0.777;
                                                                break;
                                                            }
                                                            break;
                                                            case 1: probability_RF = 0.013;
                                                            break;
                                                            case 2: probability_RF = 0;
                                                            break;
                                                            case 3: probability_RF = 0;
                                                            break;
                                                            case 4: probability_RF = 0;
                                                            break;
                                                        }
                                                            break;
                                                        case "Shoulder/elbow arthroplasty": probability_RF = 0;
                                                        break;
                                                        case "Other UL surgery/shoulder other (incl. arthroscopy)/hand surgery": probability_RF = 0.013;
                                                        break;
                                                        case "Foot and ankle surgery": probability_RF = 0.013;
                                                        break;
                                                        case "Knee arthroscopy": probability_RF = 0.011;
                                                        break;
                                                        case "Hip Arthroscopy": probability_RF = 0.009;
                                                        break;
                                                        case "Ligamentous/Meniscal surgery": probability_RF = 0.009;
                                                        break;
                                                    }
                                                }else{
                                                    if(age_triage>=50&&age_triage<55){
                                                        probability_RF = 0.151;
                                                    }else{
                                                        if(age_triage>=30&&age_triage<35){
                                                            probability_RF = 0.179;
                                                        }else{
                                                            if(age_triage>=95&&age_triage<100){
                                                                probability_RF = 0.732;
                                                            }else{
                                                                if(age_triage>=45&&age_triage<50){
                                                                    probability_RF = 0.132;
                                                                }else{
                                                                    if(age_triage>=35&&age_triage<40){
                                                                        probability_RF = 0.138;
                                                                    }else{
                                                                        if(age_triage>=100){
                                                                            probability_RF = 1;
                                                                        }else{
                                                                            if(age_triage>=15&&age_triage<20){
                                                                                probability_RF = 0;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        View1.setText("The probability of CART model is " + probability_CART);
        View2.setText("The probability of Random Forest model is " + probability_RF);
    }
}
