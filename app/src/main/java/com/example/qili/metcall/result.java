package com.example.qili.metcall;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class result extends AppCompatActivity {

    private TextView View1;
    private TextView View2;
    private TextView View3;
    private Button button_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        View1 = (TextView) findViewById(R.id.CART);
        View2 = (TextView) findViewById(R.id.NB);
        View3 = (TextView) findViewById(R.id.RF);

        Intent intent = getIntent();
        int sex = intent.getIntExtra("gender", 0);
        int smoke = intent.getIntExtra("smoking", 0);
        int alcohol = intent.getIntExtra("alcohol", 0);
        int adm = intent.getIntExtra("admsourc", 0);
        int emnone = intent.getIntExtra("emnone", 0);
        int cci = intent.getIntExtra("cci_admit", 0);
        int threatre_mins = intent.getIntExtra("threatre_mins", 0);
        int anaesthetic = intent.getIntExtra("anaesthetic", 0);
        int age_triage = intent.getIntExtra("age", 0);

        String operations = intent.getStringExtra("operations");
        int OperID = 0;

        if (operations == "THR") {
            OperID = 1;
        } else {
            if (operations == "ORIF - DHS/NAIL") {
                OperID = 2;
            } else {
                if (operations == "ORIF - Femur") {
                    OperID = 3;
                } else {
                    if (operations == "Decompressiv laminctomy/spinal fusion") {
                        OperID = 4;
                    } else {
                        if (operations == "ORIF - Tibia/fibula/patella") {
                            OperID = 5;
                        } else {
                            if (operations == "TKR/UKR") {
                                OperID = 6;
                            } else {
                                if (operations == "Hemi-arthroplasty") {
                                    OperID = 7;
                                } else {
                                    if (operations == "Other") {
                                        OperID = 8;
                                    } else {
                                        if (operations == "Other UL surgery/shoulder other (incl. arthroscopy)/hand surgery") {
                                            OperID = 9;
                                        } else {
                                            if (operations == "Knee arthroscopy") {
                                                OperID = 10;
                                            } else {
                                                if (operations == "Shoulder/elbow arthroplasty") {
                                                    OperID = 11;
                                                } else {
                                                    if (operations == "Foot and ankle surgeruy") {
                                                        OperID = 12;
                                                    } else {
                                                        if (operations == "Hip Arthroscopy") {
                                                            OperID = 13;
                                                        } else {
                                                            if (operations == "Ligamentous/Meniscal surgery") {
                                                                OperID = 14;
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

        double probability_CART = 0;
        double probability_RF = 0;
        double probability_NB = 0;

        if (age_triage < 18 && age_triage >0) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Age should less than 18, please click BACK button and input values again")
                    .setPositiveButton("Confirm", null)
                    .show();
        } else {
            if (cci > 22) {
                new AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("Charlson Score should less than 22, please click BACK button and input values again")
                        .setPositiveButton("Confirm", null)
                        .show();
            } else {

                /* probability calculator of individual CART model */
                if ((age_triage >= 15 && age_triage < 20) || (age_triage >= 25 && age_triage < 75)) {
                    if (OperID == 12 || OperID == 10 || OperID == 14 || OperID == 5 || OperID == 9 || OperID == 1) {
                        probability_CART = 0.13;
                    } else {
                        if (cci <= 4.5) {
                            if (adm == 0) {
                                probability_CART = 0.35;
                            } else {
                                probability_CART = 0.85;
                            }
                        } else {
                            probability_CART = 0.73;
                        }
                    }
                } else {
                    if (OperID == 12 || OperID == 13 || OperID == 10 || OperID == 14 || OperID == 5 || OperID == 9
                            || OperID == 11) {
                        probability_CART = 0.45;
                    } else {
                        probability_CART = 0.82;
                    }
                }

                /* Probability calculator of individual Naive Bayes model */
                if (age_triage >= 85 && age_triage < 90) {
                    probability_NB = 0.847;
                } else {
                    if (age_triage >= 70 && age_triage < 75) {
                        probability_NB = 0.247;
                    } else {
                        if (age_triage >= 80 && age_triage < 85) {
                            probability_NB = 0.797;
                        } else {
                            if (age_triage >= 40 && age_triage < 45) {
                                switch (alcohol) {
                                    case 0:
                                        probability_RF = 0.054;
                                        break;
                                    case 1:
                                        probability_RF = 0.8;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                            } else {
                                if (age_triage >= 90 && age_triage < 95) {
                                    probability_NB = 0.849;
                                } else {
                                    if (age_triage >= 75 && age_triage < 80) {
                                        probability_NB = 0.754;
                                    } else {
                                        if (age_triage >= 60 && age_triage < 65) {
                                            switch (cci) {
                                                case 0:
                                                    probability_NB = 0;
                                                    break;
                                                case 1:
                                                    probability_NB = 0;
                                                    break;
                                                case 2:
                                                    probability_NB = 0.125;
                                                    break;
                                                case 3:
                                                    probability_NB = 0.239;
                                                    break;
                                                case 4:
                                                    probability_NB = 0.684;
                                                    break;
                                                case 5:
                                                    probability_NB = 0.286;
                                                    break;
                                                case 6:
                                                    probability_NB = 0.25;
                                                    break;
                                                case 7:
                                                    probability_NB = 0.857;
                                                    break;
                                                case 8:
                                                    probability_NB = 0.833;
                                                    break;
                                                case 9:
                                                    probability_NB = 1;
                                                    break;
                                                case 10:
                                                    probability_NB = 0;
                                                    break;
                                                case 11:
                                                    probability_NB = 0;
                                                    break;
                                                case 14:
                                                    probability_NB = 0;
                                                    break;
                                            }
                                        } else {
                                            if (age_triage >= 25 && age_triage < 30) {
                                                probability_NB = 0.924;
                                            } else {
                                                if (age_triage >= 20 && age_triage < 25)
                                                    switch (adm) {
                                                        case 0:
                                                            probability_NB = 0.04;
                                                            break;
                                                        case 1:
                                                            probability_NB = 1;
                                                            break;
                                                        case 2:
                                                            probability_NB = 0.935;
                                                            break;
                                                        case 3:
                                                            probability_NB = 1;
                                                            break;
                                                    }
                                                else {
                                                    if (age_triage >= 65 && age_triage < 70) {
                                                        probability_NB = 0.268;
                                                    } else {
                                                        if (age_triage >= 55 && age_triage < 60) {
                                                            switch (OperID) {
                                                                case 2:
                                                                    probability_NB = 0.750;
                                                                    break;
                                                                case 3:
                                                                    probability_NB = 0.008;
                                                                    break;
                                                                case 8:
                                                                    switch (anaesthetic) {
                                                                        case 0:
                                                                            probability_NB = 0.071;
                                                                            break;
                                                                        case 1:
                                                                            probability_NB = 0.765;
                                                                            break;
                                                                        case 2:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 3:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 4:
                                                                            probability_NB = 0;
                                                                            break;
                                                                    }
                                                                    break;
                                                                case 5:
                                                                    switch (smoke) {
                                                                        case 0:
                                                                            probability_NB = 0.145;
                                                                            break;
                                                                        case 1:
                                                                            switch (sex) {
                                                                                case 0:
                                                                                    probability_NB = 1;
                                                                                    break;
                                                                                case 1:
                                                                                    probability_NB = 0;
                                                                                    break;
                                                                            }
                                                                            break;
                                                                        case 2:
                                                                            probability_NB = 0;
                                                                            break;
                                                                    }
                                                                    break;
                                                                case 7:
                                                                    probability_NB = 0.014;
                                                                    break;
                                                                case 6:
                                                                    switch (anaesthetic) {
                                                                        case 0:
                                                                            probability_NB = 0.012;
                                                                            break;
                                                                        case 1:
                                                                            probability_NB = 0.767;
                                                                            break;
                                                                        case 2:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 3:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 4:
                                                                            probability_NB = 0;
                                                                            break;
                                                                    }
                                                                    break;
                                                                case 4:
                                                                    switch (smoke) {
                                                                        case 0:
                                                                            probability_NB = 0.280;
                                                                            break;
                                                                        case 1:
                                                                            probability_NB = 0.944;
                                                                            break;
                                                                        case 2:
                                                                            probability_NB = 0;
                                                                            break;
                                                                    }
                                                                    break;
                                                                case 1:
                                                                    switch (anaesthetic) {
                                                                        case 0:
                                                                            switch (sex) {
                                                                                case 0:
                                                                                    probability_NB = 0.061;
                                                                                    break;
                                                                                case 1:
                                                                                    probability_NB = 0.777;
                                                                                    break;
                                                                            }
                                                                            break;
                                                                        case 1:
                                                                            probability_NB = 0.013;
                                                                            break;
                                                                        case 2:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 3:
                                                                            probability_NB = 0;
                                                                            break;
                                                                        case 4:
                                                                            probability_NB = 0;
                                                                            break;
                                                                    }
                                                                    break;
                                                                case 11:
                                                                    probability_NB = 0;
                                                                    break;
                                                                case 9:
                                                                    probability_NB = 0.013;
                                                                    break;
                                                                case 12:
                                                                    probability_NB = 0.013;
                                                                    break;
                                                                case 10:
                                                                    probability_NB = 0.011;
                                                                    break;
                                                                case 13:
                                                                    probability_NB = 0.009;
                                                                    break;
                                                                case 14:
                                                                    probability_RF = 0.009;
                                                                    break;
                                                            }
                                                        } else {
                                                            if (age_triage >= 50 && age_triage < 55) {
                                                                probability_NB = 0.151;
                                                            } else {
                                                                if (age_triage >= 30 && age_triage < 35) {
                                                                    probability_NB = 0.179;
                                                                } else {
                                                                    if (age_triage >= 95 && age_triage < 100) {
                                                                        probability_NB = 0.732;
                                                                    } else {
                                                                        if (age_triage >= 45 && age_triage < 50) {
                                                                            probability_NB = 0.132;
                                                                        } else {
                                                                            if (age_triage >= 35 && age_triage < 40) {
                                                                                probability_NB = 0.138;
                                                                            } else {
                                                                                if (age_triage >= 100) {
                                                                                    probability_NB = 1;
                                                                                } else {
                                                                                    if (age_triage >= 15 && age_triage < 20) {
                                                                                        probability_NB = 0;
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

                /*Probability calculator of individual Random Forest model*/
                switch (age_triage) {
                    case 18:
                        probability_RF = 0;
                        break;

                    case 19:
                        probability_RF = 0;
                        break;

                    case 20:
                        if (OperID == 1 && adm == 0) {
                            probability_RF = 1;
                        } else {
                            if (OperID == 8 && smoke == 1) {
                                probability_RF = 1;
                            } else {
                                probability_RF = 0;
                            }
                        }
                        break;

                    case 21:
                        if (emnone == 1 && OperID == 5) {
                            probability_RF = 1;
                        } else {
                            probability_RF = 0;
                        }
                        break;

                    case 22:
                        probability_RF = 0;
                        break;

                    case 23:
                        probability_RF = 0;
                        break;

                    case 24:
                        if (OperID == 5 || OperID == 8) {
                            probability_RF = 1;
                        } else {
                            probability_RF = 0;
                        }
                        break;

                    case 25:
                        switch (cci) {
                            case 0:
                                switch (sex) {
                                    case 1:
                                        switch (adm) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                switch (adm) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                probability_RF = 0;
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 26:
                        switch (adm) {
                            case 0:
                                switch (adm) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        if (OperID == 4 || OperID == 5 || OperID == 8) {
                                            probability_RF = 1;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                probability_RF = 1;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 27:
                        switch (adm) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 1;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 28:
                        if (emnone == 0 && OperID == 12) {
                            if (sex == 1) {
                                probability_RF = 1;
                            } else {
                                probability_RF = 0;
                            }
                        } else {
                            probability_RF = 0;
                        }
                        break;

                    case 29:
                        if (smoke == 0 && emnone == 0) {
                            if (OperID == 4 && cci == 0) {
                                probability_RF = 1;
                            } else {
                                if (OperID == 5 || OperID == 8) {
                                    probability_RF = 1;
                                } else {
                                    probability_RF = 0;
                                }
                            }
                        } else {
                            if (smoke == 1) {
                                if (OperID == 2 || OperID == 4 || OperID == 9) {
                                    probability_RF = 1;
                                } else {
                                    probability_RF = 0;
                                }
                            } else {
                                probability_RF = 0;
                            }
                        }
                        break;

                    case 30:
                        if (sex == 0 && OperID == 8) {
                            probability_RF = 1;
                        } else {
                            if (sex == 1) {
                                probability_RF = 1;
                            } else {
                                probability_RF = 0;
                            }
                        }
                        break;

                    case 31:
                        switch (anaesthetic) {
                            case 0:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (smoke) {
                                            case 0:
                                                switch (adm) {
                                                    case 0:
                                                        if (OperID == 5) {
                                                            probability_RF = 1;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                probability_RF = 1;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 32:
                        switch (emnone) {
                            case 0:
                                switch (sex) {
                                    case 0:
                                        if (OperID == 8) {
                                            probability_RF = 1;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                    case 1:
                                        if (cci == 1) {
                                            if (smoke == 1) {
                                                probability_RF = 1;
                                            } else {
                                                probability_RF = 0;
                                            }
                                        }
                                        break;
                                }
                                break;
                        }
                        break;

                    case 33:
                        if (OperID == 13) {
                            probability_RF = 0.891;
                        } else {
                            probability_RF = 0;
                        }
                        break;

                    case 34:
                        probability_RF = 0;
                        break;

                    case 35:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                switch (smoke) {
                                    case 0:
                                        if (OperID == 8) {
                                            probability_RF = 1;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                probability_RF = 0;
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 36:
                        if (OperID == 4) {
                            probability_RF = 0.885;
                        } else {
                            probability_RF = 0;
                        }
                        break;

                    case 37:
                        switch (sex) {
                            case 1:
                                if (OperID == 4) {
                                    probability_RF = 0.885;
                                } else {
                                    if (OperID == 8) {
                                        probability_RF = 1;
                                    }
                                }
                                break;
                            case 0:
                                switch (emnone) {
                                    case 0:
                                        if (OperID == 4) {
                                            probability_RF = 0.885;
                                        } else {
                                            if (OperID == 8) {
                                                probability_RF = 1;
                                            }
                                        }
                                        break;
                                    case 1:
                                        if (OperID == 4) {
                                            probability_RF = 0.885;
                                        } else {
                                            if (OperID == 5) {
                                                if (cci == 3) {
                                                    probability_RF = 1;
                                                } else {
                                                    probability_RF = 0;
                                                }
                                            } else {
                                                probability_RF = 0;
                                            }
                                        }
                                        break;
                                }
                                break;
                        }
                        break;

                    case 38:
                        if (adm == 1) {
                            probability_RF = 1;
                        } else {
                            if (adm == 2 && smoke == 1) {
                                probability_RF = 1;
                            }
                        }
                        break;

                    case 39:
                        if (smoke == 0 && OperID == 4) {
                            probability_RF = 0.885;
                        } else {
                            if (smoke == 0 && OperID == 8) {
                                probability_RF = 0.816;
                            } else {
                                if (smoke == 1 && OperID == 1) {
                                    probability_RF = 1;
                                } else {
                                    if (smoke == 1 && OperID == 2) {
                                        probability_RF = 1;
                                    }
                                }
                            }
                        }
                        break;

                    case 40:
                        switch (adm) {
                            case 0:
                                if (OperID == 4) {
                                    probability_RF = 0.885;
                                } else {
                                    if (OperID == 8 && sex == 0) {
                                        probability_RF = 0.833;
                                    } else {
                                        probability_RF = 0;
                                    }
                                }
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 1;
                                break;
                        }
                        break;

                    case 41:
                        switch (emnone) {
                            case 0:
                                if (OperID == 4) {
                                    probability_RF = 0.873;
                                } else {
                                    if (OperID == 8 && smoke == 1) {
                                        probability_RF = 1;
                                    } else {
                                        probability_RF = 0;
                                    }
                                }
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 42:
                        switch (smoke) {
                            case 0:
                                if (adm == 2) {
                                    if (emnone == 1) {
                                        probability_RF = 1;
                                    } else {
                                        probability_RF = 0;
                                    }
                                } else {
                                    probability_RF = 0;
                                }
                                break;
                            case 1:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (sex) {
                                            case 1:
                                                if (OperID == 4) {
                                                    probability_RF = 0.75;
                                                } else {
                                                    probability_RF = 0;
                                                }
                                                break;
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 1;
                                break;
                        }
                        break;

                    case 43:
                        switch (OperID) {
                            case 1:
                                probability_RF = 0.883;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0.172;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                if (adm == 2) {
                                    probability_RF = 0.752;
                                } else {
                                    probability_RF = 0;
                                }
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                probability_RF = 0.117;
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0.117;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                switch (sex) {
                                    case 0:
                                        probability_RF = 0.188;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                        }
                        break;

                    case 44:
                        switch (emnone) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                switch (sex) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        if (OperID == 2 || OperID == 3) {
                                            probability_RF = 0.778;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                }
                                break;
                        }
                        break;

                    case 45:
                        if (OperID == 4) {
                            probability_RF = 0.75;
                        } else {
                            if (OperID == 5 && sex == 1) {
                                probability_RF = 0.885;
                            } else {
                                if (OperID == 11) {
                                    probability_RF = 0.885;
                                } else {
                                    probability_RF = 0;
                                }
                            }
                        }
                        break;

                    case 46:
                        probability_RF = 0;
                        break;

                    case 47:
                        if (OperID == 5) {
                            probability_RF = 0.931;
                        } else {
                            if (OperID == 12) {
                                if (cci == 2) {
                                    probability_RF = 1;
                                } else {
                                    probability_RF = 0;
                                }
                            } else {
                                probability_RF = 0;
                            }
                        }
                        break;

                    case 48:
                        switch (emnone) {
                            case 0:
                                switch (anaesthetic) {
                                    case 0:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                if (OperID == 4) {
                                                    probability_RF = 1;
                                                } else {
                                                    if (OperID == 10) {
                                                        probability_RF = 0.188;
                                                    } else {
                                                        if (OperID == 11) {
                                                            probability_RF = 0.885;
                                                        } else {
                                                            if (OperID == 14) {
                                                                probability_RF = 0.188;
                                                            } else {
                                                                probability_RF = 0;
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0.867;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 49:
                        switch (smoke) {
                            case 0:
                                switch (OperID) {
                                    case 1:
                                        if (cci == 1 && emnone == 0) {
                                            probability_RF = 0.333;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                switch (emnone) {
                                    case 0:
                                        if (cci == 2) {
                                            probability_RF = 1;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 50:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                switch (smoke) {
                                    case 0:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        if (OperID == 8) {
                                                            probability_RF = 0;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                switch (OperID) {
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        switch (sex) {
                                                            case 0:
                                                                probability_RF = 1;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 5:
                                                        probability_RF = 0;
                                                        break;
                                                    case 6:
                                                        probability_RF = 0;
                                                        break;
                                                    case 7:
                                                        probability_RF = 0;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 3:
                                        probability_RF = 1;
                                        break;
                                    case 4:
                                        probability_RF = 1;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                        }
                        break;

                    case 51:
                        switch (adm) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 2:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 52:
                        if (OperID == 1 && emnone == 0) {
                            probability_RF = 1;
                        } else {
                            if (OperID == 2) {
                                probability_RF = 0.891;
                            } else {
                                if (OperID == 4 && emnone == 0) {
                                    probability_RF = 0.333;
                                } else {
                                    if (OperID == 8 && emnone == 0) {
                                        probability_RF = 1;
                                    } else {
                                        probability_RF = 0;
                                    }
                                }
                            }
                        }
                        break;

                    case 53:
                        switch (OperID) {
                            case 1:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 1;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (sex) {
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 0:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                if (cci == 2 || cci == 3) {
                                                    probability_RF = 1;
                                                } else {
                                                    probability_RF = 0;
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                probability_RF = 0.910;
                                break;
                            case 9:
                                switch (adm) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0.910;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 54:
                        switch (OperID) {
                            case 1:
                                switch (sex) {
                                    case 0:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0.319;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (adm) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                switch (emnone) {
                                                    case 0:
                                                        switch (smoke) {
                                                            case 0:
                                                                switch (sex) {
                                                                    case 0:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 3:
                                                probability_RF = 1;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                switch (sex) {
                                    case 0:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0.265;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                probability_RF = 0;
                                break;
                            case 9:
                                switch (sex) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (alcohol) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 55:
                        switch (adm) {
                            case 0:
                                switch (OperID) {
                                    case 1:
                                        probability_RF = 0.963;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                switch (emnone) {
                                                    case 0:
                                                        switch (sex) {
                                                            case 0:
                                                                switch (smoke) {
                                                                    case 0:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 1;
                                        break;
                                    case 4:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                        }
                        break;

                    case 56:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (emnone) {
                                            case 0:
                                                if (OperID == 2) {
                                                    probability_RF = 1;
                                                } else {
                                                    probability_RF = 0;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                }
                                break;
                            case 3:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                            case 5:
                                probability_RF = 1;
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                probability_RF = 0;
                                break;
                            case 9:
                                probability_RF = 1;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 57:
                        switch (OperID) {
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                switch (sex) {
                                    case 0:
                                        if (anaesthetic == 0) {
                                            probability_RF = 0.924;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (emnone) {
                                    case 0:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0.924;
                                                break;
                                            case 1:
                                                probability_RF = 0.333;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                switch (alcohol) {
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                switch (smoke) {
                                                    case 0:
                                                        switch (sex) {
                                                            case 0:
                                                                probability_RF = 0.25;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        if (cci == 3) {
                                            probability_RF = 1;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                }
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;


                    case 58:
                        switch (smoke) {
                            case 0:
                                switch (OperID) {
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        switch (anaesthetic) {
                                            case 0:
                                                if (adm == 2) {
                                                    probability_RF = 1;
                                                } else {
                                                    probability_RF = 0;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 2;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 1;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                switch (anaesthetic) {
                                    case 0:
                                        switch (OperID) {
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 1;
                                                break;
                                            case 5:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0.103;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0.103;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 59:
                        switch (OperID) {
                            case 1:
                                switch (alcohol) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 2:
                                switch (sex) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0.966;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 0.939;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                            case 6:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 1;
                                        break;
                                    case 1:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 7:
                                switch (sex) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 8:
                                if (cci == 3) {
                                    probability_RF = 0.868;
                                } else {
                                    if (cci == 8) {
                                        probability_RF = 1;
                                    } else {
                                        probability_RF = 0;
                                    }
                                }
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 60:
                        switch (OperID) {
                            case 1:
                                switch (sex) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 1;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                    case 2:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (adm) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                probability_RF = 0;
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 61:
                        switch (anaesthetic) {
                            case 0:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 3:
                                        switch (sex) {
                                            case 1:
                                                switch (OperID) {
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0.076;
                                                        break;
                                                    case 5:
                                                        probability_RF = 0;
                                                        break;
                                                    case 6:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 0.148;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 7:
                                                        probability_RF = 0.924;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0.076;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0.924;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0.076;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0.076;
                                                        break;
                                                }
                                                break;
                                            case 0:
                                                switch (smoke) {
                                                    case 0:
                                                        if (OperID == 6 && emnone == 0) {
                                                            probability_RF = 1;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 4:
                                        probability_RF = 1;
                                        break;
                                    case 5:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 6:
                                        switch (adm) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 1;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                if (OperID == 1 && emnone == 0) {
                                    probability_RF = 1;
                                } else {
                                    if (OperID == 1 && emnone == 1) {
                                        probability_RF = 0.967;
                                    } else {
                                        if (OperID == 6) {
                                            probability_RF = 0.972;
                                        } else {
                                            probability_RF = 0;
                                        }
                                    }
                                }
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                if (cci == 3) {
                                    probability_RF = 0.35;
                                } else {
                                    if (cci == 5) {
                                        probability_RF = 0.741;
                                    } else {
                                        if (cci == 9) {
                                            probability_RF = 1;
                                        } else {
                                            probability_RF = 0;
                                        }
                                    }
                                }
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 62:
                        switch (adm) {
                            case 0:
                                switch (smoke) {
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                switch (cci) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        if (OperID == 6) {
                                                            probability_RF = 1;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                    case 5:
                                                        probability_RF = 0;
                                                        break;
                                                    case 6:
                                                        probability_RF = 0;
                                                        break;
                                                    case 7:
                                                        probability_RF = 0;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                probability_RF = 1;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                        }
                        break;


                    case 63:
                        switch (OperID) {
                            case 1:
                                switch (sex) {
                                    case 0:
                                        switch (smoke) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0.297;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 0.921;
                                break;
                            case 3:
                                probability_RF = 0.921;
                                break;
                            case 4:
                                probability_RF = 0.921;
                                break;
                            case 5:
                                if (cci == 4) {
                                    probability_RF = 0.333;
                                } else {
                                    probability_RF = 0;
                                }
                                break;
                            case 6:
                                switch (anaesthetic) {
                                    case 0:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 0.133;
                                                break;
                                            case 1:
                                                switch (emnone) {
                                                    case 0:
                                                        switch (smoke) {
                                                            case 0:
                                                                probability_RF = 0.917;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                            case 2:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0.056;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                if (cci == 4) {
                                    probability_RF = 0.675;
                                } else {
                                    probability_RF = 0;
                                }
                                break;
                            case 9:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        switch (sex) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 10:
                                probability_RF = 0.056;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0.056;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0.056;
                                break;
                        }
                        break;


                    case 64:
                        switch (smoke) {
                            case 0:
                                switch (adm) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                if (OperID == 1 && sex == 0) {
                                                    probability_RF = 0.784;
                                                } else {
                                                    if (OperID == 1 && sex == 1) {
                                                        probability_RF = 0.760;
                                                    } else {
                                                        probability_RF = 0;
                                                    }
                                                }
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                            case 1:
                                switch (sex) {
                                    case 1:
                                        if (OperID == 1) {
                                            probability_RF = 0.2;
                                        } else {
                                            if (OperID == 4) {
                                                probability_RF = 1;
                                            } else {
                                                if (OperID == 8) {
                                                    probability_RF = 1;
                                                } else {
                                                    probability_RF = 0;
                                                }
                                            }
                                        }
                                        break;
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                        }
                        break;


                    case 65:
                        switch (smoke) {
                            case 0:
                                switch (alcohol) {
                                    case 0:
                                        switch (OperID) {
                                            case 1:
                                                if (cci == 3 && emnone == 0) {
                                                    probability_RF = 0.25;
                                                } else {
                                                    if (cci == 3 && emnone == 1) {
                                                        probability_RF = 1;
                                                    } else {
                                                        if (cci == 6) {
                                                            probability_RF = 0.714;
                                                        } else {
                                                            if (cci == 9) {
                                                                probability_RF = 1;
                                                            } else {
                                                                probability_RF = 0;
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        switch (sex) {
                                                            case 0:
                                                                probability_RF = 0.873;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                switch (adm) {
                                                    case 0:
                                                        if (cci == 3 && emnone == 0) {
                                                            probability_RF = 0.333;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 9:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        if (cci == 3) {
                                                            probability_RF = 0.873;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        if (OperID == 5) {
                                            probability_RF = 0.286;
                                        } else {
                                            if (OperID == 8) {
                                                probability_RF = 1;
                                            } else {
                                                probability_RF = 0;
                                            }
                                        }
                                        break;
                                    case 2:
                                        if (OperID == 7 || OperID == 8) {
                                            probability_RF = 1;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                }
                                break;
                        }
                        break;

                    case 66:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                switch (sex) {
                                    case 1:
                                        switch (OperID) {
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                switch (emnone) {
                                                    case 0:
                                                        switch (smoke) {
                                                            case 0:
                                                                probability_RF = 0.333;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 0:
                                        if (OperID == 8 || OperID == 12) {
                                            probability_RF = 1;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                }
                                break;
                            case 4:
                                switch (smoke) {
                                    case 0:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 5:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 6:
                                probability_RF = 1;
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                probability_RF = 0;
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 67:
                        switch (OperID) {
                            case 1:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 2;
                                        break;
                                    case 3:
                                        switch (emnone) {
                                            case 0:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 0.333;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 4:
                                        switch (anaesthetic) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.323;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0.827;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0.333;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                switch (sex) {
                                    case 1:
                                        probability_RF = 0.164;
                                        break;
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                switch (smoke) {
                                                    case 0:
                                                        switch (adm) {
                                                            case 0:
                                                                probability_RF = 0.981;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 3:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 4:
                                        probability_RF = 0.710;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0.692;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 4:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 4:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 0.905;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0.333;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                switch (smoke) {
                                    case 0:
                                        if (cci == 4) {
                                            probability_RF = 0.155;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0.794;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 6:
                                switch (anaesthetic) {
                                    case 0:
                                        switch (sex) {
                                            case 1:
                                                probability_RF = 0.771;
                                                break;
                                            case 0:
                                                switch (smoke) {
                                                    case 0:
                                                        switch (adm) {
                                                            case 0:
                                                                switch (emnone) {
                                                                    case 0:
                                                                        probability_RF = 0.333;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0.75;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 1:
                                        if (cci == 4) {
                                            probability_RF = 0.818;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0.063;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 7:
                                switch (alcohol) {
                                    case 0:
                                        probability_RF = 0.196;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 8:
                                switch (adm) {
                                    case 0:
                                        switch (sex) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.755;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0.717;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 9:
                                if (cci == 4) {
                                    probability_RF = 0.861;
                                } else {
                                    probability_RF = 0;
                                }
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0.712;
                                        break;
                                    case 5:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 6:
                                        probability_RF = 0.333;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 12:
                                probability_RF = 0.109;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;


                    case 68:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                switch (sex) {
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                if (anaesthetic == 0) {
                                                    probability_RF = 1;
                                                } else {
                                                    probability_RF = 0;
                                                }
                                                break;
                                            case 1:
                                                if (OperID == 1) {
                                                    probability_RF = 1;
                                                } else {
                                                    if (OperID == 3) {
                                                        probability_RF = 0.272;
                                                    } else {
                                                        if (OperID == 8) {
                                                            probability_RF = 0.272;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                    }
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 4:
                                switch (adm) {
                                    case 0:
                                        switch (sex) {
                                            case 1:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        switch (smoke) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 5:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 6:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 7:
                                switch (emnone) {
                                    case 0:
                                        switch (anaesthetic) {
                                            case 0:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 0.3;
                                                        break;
                                                    case 1:
                                                        switch (smoke) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                            case 2:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 8:
                                probability_RF = 0;
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 69:
                        switch (OperID) {
                            case 1:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        switch (adm) {
                                            case 0:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 1;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 1;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 0.024;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        switch (anaesthetic) {
                                            case 0:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 6:
                                        probability_RF = 1;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                if (cci == 6) {
                                    probability_RF = 0.091;
                                } else {
                                    probability_RF = 0;
                                }
                                break;
                            case 6:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        switch (emnone) {
                                            case 0:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        switch (adm) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 6:
                                        probability_RF = 1;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                probability_RF = 0;
                                break;
                            case 9:
                                probability_RF = 0.871;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                switch (anaesthetic) {
                                    case 0:
                                        switch (smoke) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        switch (sex) {
                                                            case 0:
                                                                probability_RF = 1;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        switch (sex) {
                                                            case 0:
                                                                probability_RF = 0.924;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 12:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0.035;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0.313;
                                        break;
                                }
                                break;
                        }
                        break;

                    case 70:
                        switch (smoke) {
                            case 0:
                                switch (OperID) {
                                    case 1:
                                        switch (sex) {
                                            case 1:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        switch (adm) {
                                                            case 0:
                                                                probability_RF = 0.216;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        switch (adm) {
                                            case 0:
                                                if (cci == 4 && sex == 1) {
                                                    probability_RF = 0.103;
                                                } else {
                                                    probability_RF = 0;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 3:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0.906;
                                                break;
                                        }
                                        break;
                                    case 4:
                                        probability_RF = 0.004;
                                        break;
                                    case 5:
                                        probability_RF = 0.004;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 1;
                                        break;
                                    case 9:
                                        switch (sex) {
                                            case 1:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        if (cci == 4) {
                                                            probability_RF = 1;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        if (cci == 4) {
                                                            probability_RF = 1;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 10:
                                        probability_RF = 0.044;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0.044;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        switch (smoke) {
                                            case 1:
                                                switch (cci) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        switch (sex) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                    case 4:
                                                        switch (sex) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                    case 5:
                                                        probability_RF = 1;
                                                        break;
                                                    case 6:
                                                        probability_RF = 0;
                                                        break;
                                                    case 7:
                                                        probability_RF = 0;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 1;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                }
                                break;
                        }
                        break;

                    case 71:
                        switch (OperID) {
                            case 1:
                                switch (smoke) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                switch (anaesthetic) {
                                                    case 0:
                                                        switch (sex) {
                                                            case 1:
                                                                switch (emnone) {
                                                                    case 0:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.931;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                }
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 0.339;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (alcohol) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 2:
                                switch (alcohol) {
                                    case 0:
                                        if (cci == 4 && smoke == 2) {
                                            probability_RF = 1;
                                        } else {
                                            if (cci == 5) {
                                                probability_RF = 0.829;
                                            } else {
                                                if (cci == 8) {
                                                    probability_RF = 0.255;
                                                } else {
                                                    probability_RF = 0;
                                                }
                                            }
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 3:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 0.112;
                                        break;
                                    case 1:
                                        switch (smoke) {
                                            case 0:
                                                switch (alcohol) {
                                                    case 0:
                                                        if (cci == 8) {
                                                            probability_RF = 1;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 4:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0.032;
                                        break;
                                    case 1:
                                        probability_RF = 0.890;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                if (cci == 4 && sex == 1) {
                                    probability_RF = 0.333;
                                } else {
                                    if (cci == 8) {
                                        probability_RF = 0.255;
                                    } else {
                                        probability_RF = 0;
                                    }
                                }
                                break;
                            case 6:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        switch (smoke) {
                                            case 0:
                                                switch (sex) {
                                                    case 0:
                                                        switch (emnone) {
                                                            case 0:
                                                                if (anaesthetic == 0) {
                                                                    probability_RF = 0.25;
                                                                } else {
                                                                    probability_RF = 0;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 2;
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0.791;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0.895;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 7:
                                switch (sex) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0.114;
                                                break;
                                            case 1:
                                                if (cci == 4) {
                                                    probability_RF = 0.963;
                                                } else {
                                                    if (cci == 8) {
                                                        probability_RF = 1;
                                                    } else {
                                                        probability_RF = 0;
                                                    }
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 8:
                                probability_RF = 0.035;
                                break;
                            case 9:
                                switch (emnone) {
                                    case 0:
                                        switch (sex) {
                                            case 1:
                                                probability_RF = 0.114;
                                                break;
                                            case 0:
                                                probability_RF = 0.25;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0.148;
                                        break;
                                }
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 72:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (OperID) {
                                    case 1:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                switch (adm) {
                                                    case 0:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 1;
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0.995;
                                                break;
                                        }
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                if (OperID == 2) {
                                    probability_RF = 1;
                                } else {
                                    if (OperID == 5) {
                                        probability_RF = 0.333;
                                    } else {
                                        probability_RF = 0;
                                    }
                                }
                                break;
                            case 6:
                                switch (alcohol) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 7:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 8:
                                switch (OperID) {
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        switch (smoke) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 9:
                                probability_RF = 1;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 73:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (OperID) {
                                    case 1:
                                        switch (sex) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        switch (smoke) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0.333;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0.175;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        switch (adm) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.089;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                        }
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0.742;
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0.065;
                                        break;
                                    case 6:
                                        switch (adm) {
                                            case 0:
                                                switch (sex) {
                                                    case 0:
                                                        switch (anaesthetic) {
                                                            case 0:
                                                                probability_RF = 0.322;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 0.931;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.109;
                                                        break;
                                                }
                                                break;
                                            case 9:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.745;
                                                        break;
                                                }
                                                break;
                                            case 10:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 1;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0.065;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 6:
                                switch (anaesthetic) {
                                    case 0:
                                        probability_RF = 1;
                                        break;
                                    case 1:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 5:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 1;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 7:
                                probability_RF = 0;
                                break;
                            case 8:
                                probability_RF = 0;
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 74:
                        switch (anaesthetic) {
                            case 0:
                                switch (smoke) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                switch (adm) {
                                                    case 0:
                                                        probability_RF = 0.698;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (emnone) {
                                            case 0:
                                                if (OperID == 1) {
                                                    probability_RF = 1;
                                                } else {
                                                    if (OperID == 4 && sex == 0) {
                                                        probability_RF = 1;
                                                    } else {
                                                        probability_RF = 0;
                                                    }
                                                }
                                                break;
                                            case 1:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        if (OperID == 5) {
                                                            probability_RF = 0;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 1:
                                switch (OperID) {
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        switch (emnone) {
                                            case 0:
                                                if (cci == 5) {
                                                    probability_RF = 1;
                                                } else {
                                                    probability_RF = 0;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 75:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (OperID) {
                                    case 1:
                                        switch (anaesthetic) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        switch (adm) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        switch (sex) {
                                            case 0:
                                                switch (adm) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 8:
                                        probability_RF = 1;
                                        break;
                                    case 9:
                                        probability_RF = 1;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                if (OperID == 2) {
                                    probability_RF = 0.75;
                                } else {
                                    probability_RF = 0;
                                }
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                switch (smoke) {
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 8:
                                if (OperID == 2) {
                                    probability_RF = 1;
                                } else {
                                    probability_RF = 0;
                                }
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 76:
                        switch (OperID) {
                            case 1:
                                if (cci == 6) {
                                    probability_RF = 1;
                                } else {
                                    probability_RF = 0;
                                }
                                break;
                            case 2:
                                if (cci == 6 || cci == 7) {
                                    probability_RF = 1;
                                } else {
                                    probability_RF = 0;
                                }
                                break;
                            case 3:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 0.333;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 1;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 4:
                                switch (adm) {
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        if (cci == 4 || cci == 9) {
                                                            probability_RF = 1;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0.327;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                            case 6:
                                switch (emnone) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                switch (sex) {
                                                    case 0:
                                                        switch (smoke) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 1;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 7:
                                switch (anaesthetic) {
                                    case 0:
                                        switch (adm) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0.313;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0.333;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 8:
                                probability_RF = 0.899;
                                break;
                            case 9:
                                switch (adm) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0.688;
                                        break;
                                }
                                break;
                            case 10:
                                switch (adm) {
                                    case 0:
                                        if (cci == 4 && sex == 0) {
                                            probability_RF = 0.976;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0.313;
                                        break;
                                }
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 77:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (adm) {
                                    case 0:
                                        switch (OperID) {
                                            case 1:
                                                switch (emnone) {
                                                    case 0:
                                                        if (anaesthetic == 0) {
                                                            probability_RF = 1;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 0.75;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                switch (sex) {
                                                    case 0:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                switch (anaesthetic) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                }
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                switch (anaesthetic) {
                                    case 0:
                                        switch (sex) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0.833;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 6:
                                switch (sex) {
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                }
                                break;
                            case 7:
                                probability_RF = 1;
                                break;
                            case 8:
                                probability_RF = 0;
                                break;
                            case 9:
                                probability_RF = 1;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 1;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 1;
                                break;
                        }
                        break;

                    case 78:
                        switch (OperID) {
                            case 1:
                                switch (adm) {
                                    case 0:
                                        switch (sex) {
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        if (cci == 4 && smoke == 1) {
                                                            probability_RF = 1;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                switch (adm) {
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        if (cci == 6) {
                                            probability_RF = 1;
                                        } else {
                                            probability_RF = 0;
                                        }
                                        break;
                                }
                                break;
                            case 3:
                                probability_RF = 0.913;
                                break;
                            case 4:
                                switch (sex) {
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        if (cci == 5) {
                                            probability_RF = 1;
                                        } else {
                                            if (cci == 6) {
                                                probability_RF = 0.891;
                                            } else {
                                                probability_RF = 0;
                                            }
                                        }
                                        break;
                                }
                                break;
                            case 5:
                                probability_RF = 0.041;
                                break;
                            case 6:
                                switch (smoke) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                switch (adm) {
                                                    case 0:
                                                        switch (sex) {
                                                            case 0:
                                                                switch (anaesthetic) {
                                                                    case 0:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 3:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 4:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 5:
                                                switch (anaesthetic) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 6:
                                                probability_RF = 0.337;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 1;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (anaesthetic) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                switch (adm) {
                                                    case 0:
                                                        if (cci == 5) {
                                                            probability_RF = 1;
                                                        } else {
                                                            probability_RF = 0;
                                                        }
                                                        break;
                                                    case 2:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 7:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0.038;
                                        break;
                                    case 1:
                                        probability_RF = 0.045;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 8:
                                switch (adm) {
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0.041;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        probability_RF = 0.045;
                                        break;
                                    case 6:
                                        probability_RF = 0.313;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                        }
                        break;


                    case 79:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                switch (OperID) {
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        switch (adm) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        switch (sex) {
                                            case 0:
                                                switch (anaesthetic) {
                                                    case 0:
                                                        switch (smoke) {
                                                            case 0:
                                                                probability_RF = 1;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 7:
                                        switch (smoke) {
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 1;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                switch (smoke) {
                                    case 0:
                                        switch (OperID) {
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 1;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 6:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 7:
                                switch (OperID) {
                                    case 1:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 2:
                                        switch (adm) {
                                            case 0:
                                                probability_RF = 0.262;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.835;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0.835;
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        switch (smoke) {
                                            case 0:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.835;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 0.894;
                                                break;
                                        }
                                        break;
                                    case 8:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0.890;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 8:
                                probability_RF = 1;
                                break;
                            case 9:
                                probability_RF = 1;
                                break;
                            case 10:
                                probability_RF = 1;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 80:
                        switch (anaesthetic) {
                            case 0:
                                switch (OperID) {
                                    case 1:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0.342;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0.842;
                                                break;
                                            case 5:
                                                probability_RF = 0.296;
                                                break;
                                            case 6:
                                                switch (adm) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 3:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                switch (adm) {
                                                    case 0:
                                                        switch (cci) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 0;
                                                                break;
                                                            case 5:
                                                                probability_RF = 0;
                                                                break;
                                                            case 6:
                                                                probability_RF = 1;
                                                                break;
                                                            case 7:
                                                                probability_RF = 0;
                                                                break;
                                                            case 8:
                                                                probability_RF = 0;
                                                                break;
                                                            case 9:
                                                                probability_RF = 0;
                                                                break;
                                                            case 10:
                                                                probability_RF = 0;
                                                                break;
                                                            case 11:
                                                                probability_RF = 0;
                                                                break;
                                                            case 12:
                                                                probability_RF = 0;
                                                                break;
                                                            case 13:
                                                                probability_RF = 0;
                                                                break;
                                                            case 14:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.68;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 4:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0.175;
                                                break;
                                        }
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.714;
                                                        break;
                                                }
                                                break;
                                            case 5:
                                                probability_RF = 0.338;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 7:
                                        switch (adm) {
                                            case 0:
                                                switch (cci) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        switch (sex) {
                                                            case 0:
                                                                probability_RF = 0.900;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 5:
                                                        probability_RF = 0;
                                                        break;
                                                    case 6:
                                                        probability_RF = 1;
                                                        break;
                                                    case 7:
                                                        probability_RF = 1;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 0.759;
                                                break;
                                        }
                                        break;
                                    case 8:
                                        probability_RF = 0.062;
                                        break;
                                    case 9:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0.333;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                switch (OperID) {
                                    case 1:
                                        probability_RF = 0.041;
                                        break;
                                    case 2:
                                        probability_RF = 0.907;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0.041;
                                        break;
                                    case 7:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                        }
                        break;


                    case 81:
                        switch (OperID) {
                            case 1:
                                probability_RF = 0.983;
                                break;
                            case 2:
                                switch (sex) {
                                    case 0:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0.320;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (smoke) {
                                            case 0:
                                                switch (cci) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                    case 5:
                                                        probability_RF = 0;
                                                        break;
                                                    case 6:
                                                        probability_RF = 0;
                                                        break;
                                                    case 7:
                                                        probability_RF = 1;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 3:
                                probability_RF = 0.983;
                                break;
                            case 4:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 1;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                            case 6:
                                switch (smoke) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                switch (sex) {
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 0:
                                                        switch (emnone) {
                                                            case 0:
                                                                switch (anaesthetic) {
                                                                    case 0:
                                                                        probability_RF = 0.09;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 3:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 4:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (anaesthetic) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0.281;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 7:
                                switch (adm) {
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.333;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 8:
                                probability_RF = 1;
                                break;
                            case 9:
                                switch (smoke) {
                                    case 0:
                                        switch (sex) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.073;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 1;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 82:
                        switch (OperID) {
                            case 1:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (adm) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                switch (adm) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0.364;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                }
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                switch (sex) {
                                    case 0:
                                        switch (adm) {
                                            case 0:
                                                switch (smoke) {
                                                    case 0:
                                                        switch (anaesthetic) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 1;
                                                break;
                                            case 5:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.75;
                                                        break;
                                                }
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 3:
                                switch (adm) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 1;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 1;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0.25;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 4:
                                switch (sex) {
                                    case 0:
                                        switch (adm) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        switch (cci) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 1;
                                                                break;
                                                            case 5:
                                                                probability_RF = 1;
                                                                break;
                                                            case 6:
                                                                switch (smoke) {
                                                                    case 0:
                                                                        probability_RF = 0.903;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 7:
                                                                probability_RF = 0;
                                                                break;
                                                            case 8:
                                                                probability_RF = 0;
                                                                break;
                                                            case 9:
                                                                probability_RF = 0;
                                                                break;
                                                            case 10:
                                                                probability_RF = 0;
                                                                break;
                                                            case 11:
                                                                probability_RF = 0;
                                                                break;
                                                            case 12:
                                                                probability_RF = 0;
                                                                break;
                                                            case 13:
                                                                probability_RF = 0;
                                                                break;
                                                            case 14:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0.338;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (adm) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                            case 6:
                                switch (emnone) {
                                    case 0:
                                        switch (anaesthetic) {
                                            case 0:
                                                switch (cci) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                    case 5:
                                                        switch (smoke) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 6:
                                                        probability_RF = 0;
                                                        break;
                                                    case 7:
                                                        probability_RF = 0;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                switch (smoke) {
                                                    case 0:
                                                        switch (cci) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 0;
                                                                break;
                                                            case 5:
                                                                probability_RF = 1;
                                                                break;
                                                            case 6:
                                                                probability_RF = 0;
                                                                break;
                                                            case 7:
                                                                probability_RF = 0;
                                                                break;
                                                            case 8:
                                                                probability_RF = 0;
                                                                break;
                                                            case 9:
                                                                probability_RF = 0;
                                                                break;
                                                            case 10:
                                                                probability_RF = 0;
                                                                break;
                                                            case 11:
                                                                probability_RF = 0;
                                                                break;
                                                            case 12:
                                                                probability_RF = 0;
                                                                break;
                                                            case 13:
                                                                probability_RF = 0;
                                                                break;
                                                            case 14:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.064;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0.885;
                                        break;
                                }
                                break;
                            case 7:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0.313;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 8:
                                switch (smoke) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0.957;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 9:
                                switch (adm) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 1;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0.241;
                                        break;
                                }
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 83:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                            case 5:
                                switch (smoke) {
                                    case 0:
                                        switch (anaesthetic) {
                                            case 0:
                                                switch (OperID) {
                                                    case 1:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 0.143;
                                                                break;
                                                            case 1:
                                                                switch (adm) {
                                                                    case 0:
                                                                        probability_RF = 0.232;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                        }
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        switch (adm) {
                                                            case 0:
                                                                switch (emnone) {
                                                                    case 0:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0.907;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 5:
                                                        probability_RF = 0.041;
                                                        break;
                                                    case 6:
                                                        probability_RF = 0.041;
                                                        break;
                                                    case 7:
                                                        switch (adm) {
                                                            case 0:
                                                                switch (emnone) {
                                                                    case 0:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0.935;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 1;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                switch (OperID) {
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        switch (sex) {
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                            case 0:
                                                                switch (emnone) {
                                                                    case 0:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0.982;
                                                                        break;
                                                                }
                                                                break;
                                                        }
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                    case 5:
                                                        probability_RF = 0;
                                                        break;
                                                    case 6:
                                                        probability_RF = 0;
                                                        break;
                                                    case 7:
                                                        probability_RF = 0;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                switch (adm) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                switch (anaesthetic) {
                                                    case 0:
                                                        switch (OperID) {
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                switch (sex) {
                                                                    case 0:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 1;
                                                                        break;
                                                                }
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 1;
                                                                break;
                                                            case 5:
                                                                probability_RF = 0;
                                                                break;
                                                            case 6:
                                                                probability_RF = 0;
                                                                break;
                                                            case 7:
                                                                probability_RF = 0;
                                                                break;
                                                            case 8:
                                                                probability_RF = 0;
                                                                break;
                                                            case 9:
                                                                probability_RF = 0;
                                                                break;
                                                            case 10:
                                                                probability_RF = 0;
                                                                break;
                                                            case 11:
                                                                probability_RF = 0;
                                                                break;
                                                            case 12:
                                                                probability_RF = 0;
                                                                break;
                                                            case 13:
                                                                probability_RF = 0;
                                                                break;
                                                            case 14:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 3:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 6:
                                        switch (sex) {
                                            case 1:
                                                switch (alcohol) {
                                                    case 0:
                                                        switch (emnone) {
                                                            case 0:
                                                                switch (OperID) {
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 3:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 4:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 5:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 6:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 7:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 8:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 9:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 10:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 11:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 12:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 13:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 14:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                switch (OperID) {
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        switch (adm) {
                                                                            case 0:
                                                                                probability_RF = 1;
                                                                                break;
                                                                            case 1:
                                                                                probability_RF = 0;
                                                                                break;
                                                                            case 2:
                                                                                probability_RF = 0;
                                                                                break;
                                                                        }
                                                                        break;
                                                                    case 3:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 4:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 5:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 6:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 7:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 8:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 9:
                                                                        probability_RF = 0.910;
                                                                        break;
                                                                    case 10:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 11:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 12:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 13:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 14:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 1;
                                                }
                                                break;
                                            case 0:
                                                probability_RF = 0;
                                        }
                                        break;
                                    case 7:
                                        probability_RF = 1;
                                        break;
                                    case 8:
                                        probability_RF = 1;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 1;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                        }
                        break;


                    case 84:
                        switch (OperID) {
                            case 1:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                switch (sex) {
                                                    case 1:
                                                        probability_RF = 0.972;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 6:
                                        probability_RF = 1;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0.891;
                                                break;
                                            case 1:
                                                switch (sex) {
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 0:
                                                        probability_RF = 0.75;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 6:
                                        probability_RF = 1;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 1;
                                        break;
                                    case 9:
                                        probability_RF = 1;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 3:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0.842;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 6:
                                        probability_RF = 1;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 4:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 1;
                                        break;
                                    case 1:
                                        switch (sex) {
                                            case 1:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 0:
                                                switch (cci) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                    case 5:
                                                        probability_RF = 0;
                                                        break;
                                                    case 6:
                                                        probability_RF = 1;
                                                        break;
                                                    case 7:
                                                        probability_RF = 0;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 5:
                                switch (sex) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (emnone) {
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                    case 5:
                                                        probability_RF = 0.985;
                                                        break;
                                                    case 6:
                                                        probability_RF = 1;
                                                        break;
                                                    case 7:
                                                        probability_RF = 0;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 6:
                                switch (emnone) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 1;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 0.75;
                                                break;
                                            case 1:
                                                probability_RF = 0.972;
                                                break;
                                        }
                                        break;
                                    case 7:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 0.931;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.963;
                                                        break;
                                                }
                                                break;
                                            case 6:
                                                probability_RF = 1;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 1;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                        }

                    case 85:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                            case 5:
                                switch (smoke) {
                                    case 0:
                                        switch (sex) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.8;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                switch (OperID) {
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0.889;
                                                        break;
                                                    case 3:
                                                        switch (adm) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                    case 5:
                                                        probability_RF = 0;
                                                        break;
                                                    case 6:
                                                        probability_RF = 0.891;
                                                        break;
                                                    case 7:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 1;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 6:
                                switch (adm) {
                                    case 0:
                                        switch (anaesthetic) {
                                            case 0:
                                                switch (smoke) {
                                                    case 0:
                                                        switch (sex) {
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        switch (sex) {
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                            case 0:
                                                                probability_RF = 0.905;
                                                                break;
                                                        }
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 7:
                                switch (OperID) {
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        switch (sex) {
                                            case 1:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0.8;
                                                        break;
                                                }
                                                break;
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 3:
                                        probability_RF = 1;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        switch (sex) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 8:
                                probability_RF = 1;
                                break;
                            case 9:
                                probability_RF = 1;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;


                    case 86:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                            case 5:
                                switch (anaesthetic) {
                                    case 0:
                                        switch (OperID) {
                                            case 1:
                                                switch (alcohol) {
                                                    case 0:
                                                        switch (sex) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 1;
                                                break;
                                            case 3:
                                                probability_RF = 1;
                                                break;
                                            case 4:
                                                probability_RF = 1;
                                                break;
                                            case 5:
                                                probability_RF = 1;
                                                break;
                                            case 6:
                                                probability_RF = 1;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 1;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                }
                                break;
                            case 6:
                                switch (sex) {
                                    case 1:
                                        switch (adm) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 0:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 7:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 8:
                                probability_RF = 1;
                                break;
                            case 9:
                                probability_RF = 1;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 87:
                        switch (smoke) {
                            case 0:
                                switch (OperID) {
                                    case 1:
                                        switch (sex) {
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        switch (cci) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 0;
                                                                break;
                                                            case 5:
                                                                probability_RF = 0.965;
                                                                break;
                                                            case 6:
                                                                probability_RF = 0;
                                                                break;
                                                            case 7:
                                                                probability_RF = 0;
                                                                break;
                                                            case 8:
                                                                probability_RF = 0;
                                                                break;
                                                            case 9:
                                                                probability_RF = 0;
                                                                break;
                                                            case 10:
                                                                probability_RF = 0;
                                                                break;
                                                            case 11:
                                                                probability_RF = 0;
                                                                break;
                                                            case 12:
                                                                probability_RF = 0;
                                                                break;
                                                            case 13:
                                                                probability_RF = 0;
                                                                break;
                                                            case 14:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 2:
                                                        switch (cci) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 0;
                                                                break;
                                                            case 5:
                                                                switch (sex) {
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        switch (anaesthetic) {
                                                                            case 0:
                                                                                switch (emnone) {
                                                                                    case 0:
                                                                                        probability_RF = 1;
                                                                                        break;
                                                                                    case 1:
                                                                                        probability_RF = 0.762;
                                                                                        break;
                                                                                }
                                                                                break;
                                                                            case 1:
                                                                                probability_RF = 0.945;
                                                                                break;
                                                                            case 2:
                                                                                probability_RF = 0;
                                                                                break;
                                                                            case 3:
                                                                                probability_RF = 0;
                                                                                break;
                                                                            case 4:
                                                                                probability_RF = 0;
                                                                                break;
                                                                        }
                                                                        break;
                                                                }
                                                                break;
                                                            case 6:
                                                                probability_RF = 1;
                                                                break;
                                                            case 7:
                                                                probability_RF = 0;
                                                                break;
                                                            case 8:
                                                                probability_RF = 0;
                                                                break;
                                                            case 9:
                                                                probability_RF = 1;
                                                                break;
                                                            case 10:
                                                                probability_RF = 0;
                                                                break;
                                                            case 11:
                                                                probability_RF = 0;
                                                                break;
                                                            case 12:
                                                                probability_RF = 0;
                                                                break;
                                                            case 13:
                                                                probability_RF = 0;
                                                                break;
                                                            case 14:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 3:
                                                        switch (cci) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 0;
                                                                break;
                                                            case 5:
                                                                probability_RF = 0.776;
                                                                break;
                                                            case 6:
                                                                probability_RF = 1;
                                                                break;
                                                            case 7:
                                                                probability_RF = 1;
                                                                break;
                                                            case 8:
                                                                probability_RF = 0;
                                                                break;
                                                            case 9:
                                                                probability_RF = 1;
                                                                break;
                                                            case 10:
                                                                probability_RF = 0;
                                                                break;
                                                            case 11:
                                                                probability_RF = 0;
                                                                break;
                                                            case 12:
                                                                probability_RF = 0;
                                                                break;
                                                            case 13:
                                                                probability_RF = 0;
                                                                break;
                                                            case 14:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 4:
                                                        probability_RF = 1;
                                                        break;
                                                    case 5:
                                                        probability_RF = 0;
                                                        break;
                                                    case 6:
                                                        probability_RF = 0;
                                                        break;
                                                    case 7:
                                                        switch (adm) {
                                                            case 0:
                                                                switch (cci) {
                                                                    case 0:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 3:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 4:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 5:
                                                                        switch (sex) {
                                                                            case 0:
                                                                                probability_RF = 0.950;
                                                                                break;
                                                                            case 1:
                                                                                probability_RF = 0;
                                                                                break;
                                                                        }
                                                                        break;
                                                                    case 6:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 7:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 8:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 9:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 10:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 11:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 12:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 13:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 14:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0.087;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 0.087;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 1:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 6:
                                        probability_RF = 1;
                                        break;
                                    case 7:
                                        probability_RF = 1;
                                        break;
                                    case 8:
                                        probability_RF = 1;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                probability_RF = 1;
                                break;
                        }
                        break;

                    case 88:
                        switch (OperID) {
                            case 1:
                                switch (anaesthetic) {
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                switch (adm) {
                                                    case 0:
                                                        probability_RF = 0.946;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 2:
                                switch (sex) {
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 3:
                                probability_RF = 0.917;
                                break;
                            case 4:
                                switch (emnone) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 6:
                                                probability_RF = 1;
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        switch (sex) {
                                            case 1:
                                                switch (adm) {
                                                    case 0:
                                                        switch (anaesthetic) {
                                                            case 0:
                                                                switch (cci) {
                                                                    case 0:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 3:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 4:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 5:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 6:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 7:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 8:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 9:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 10:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 11:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 12:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 13:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 14:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 1;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 8:
                                switch (cci) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        probability_RF = 0;
                                        break;
                                    case 8:
                                        probability_RF = 1;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;


                    case 89:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                            case 5:
                                switch (sex) {
                                    case 1:
                                        switch (adm) {
                                            case 0:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 0.75;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                    case 0:
                                        switch (emnone) {
                                            case 0:
                                                switch (OperID) {
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                    case 5:
                                                        probability_RF = 0;
                                                        break;
                                                    case 6:
                                                        probability_RF = 1;
                                                        break;
                                                    case 7:
                                                        probability_RF = 0;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                switch (OperID) {
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 1;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                    case 5:
                                                        probability_RF = 0;
                                                        break;
                                                    case 6:
                                                        probability_RF = 1;
                                                        break;
                                                    case 7:
                                                        probability_RF = 0;
                                                        break;
                                                    case 8:
                                                        probability_RF = 0;
                                                        break;
                                                    case 9:
                                                        probability_RF = 0;
                                                        break;
                                                    case 10:
                                                        probability_RF = 0;
                                                        break;
                                                    case 11:
                                                        probability_RF = 0;
                                                        break;
                                                    case 12:
                                                        probability_RF = 0;
                                                        break;
                                                    case 13:
                                                        probability_RF = 0;
                                                        break;
                                                    case 14:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 6:
                                switch (anaesthetic) {
                                    case 0:
                                        switch (OperID) {
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                switch (adm) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 3:
                                                probability_RF = 0.936;
                                                break;
                                            case 4:
                                                probability_RF = 0.935;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                switch (adm) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 8:
                                                probability_RF = 0.936;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 1;
                                                break;
                                            case 1:
                                                switch (adm) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0.917;
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 7:
                                probability_RF = 1;
                                break;
                            case 8:
                                probability_RF = 1;
                                break;
                            case 9:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 1;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 90:
                        switch (cci) {
                            case 0:
                                probability_RF = 0;
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                            case 5:
                                switch (OperID) {
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        switch (emnone) {
                                            case 0:
                                                probability_RF = 0.674;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0.813;
                                        break;
                                    case 5:
                                        probability_RF = 0;
                                        break;
                                    case 6:
                                        probability_RF = 0;
                                        break;
                                    case 7:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0.727;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 8:
                                        probability_RF = 0;
                                        break;
                                    case 9:
                                        probability_RF = 0;
                                        break;
                                    case 10:
                                        probability_RF = 0;
                                        break;
                                    case 11:
                                        probability_RF = 0;
                                        break;
                                    case 12:
                                        probability_RF = 0;
                                        break;
                                    case 13:
                                        probability_RF = 0;
                                        break;
                                    case 14:
                                        probability_RF = 0;
                                        break;
                                }
                            case 6:
                                switch (sex) {
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 0:
                                        switch (smoke) {
                                            case 0:
                                                switch (emnone) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        switch (adm) {
                                                            case 0:
                                                                switch (OperID) {
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 3:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 4:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 5:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 6:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 7:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 8:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 9:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 10:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 11:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 12:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 13:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 14:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 1;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 1;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 7:
                                switch (sex) {
                                    case 1:
                                        switch (OperID) {
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 1;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 0:
                                        switch (smoke) {
                                            case 0:
                                                probability_RF = 0.75;
                                                break;
                                            case 1:
                                                probability_RF = 1;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                }
                                break;
                            case 8:
                                probability_RF = 1;
                                break;
                            case 9:
                                probability_RF = 1;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 91:
                        switch (smoke) {
                            case 0:
                                switch (adm) {
                                    case 0:
                                        switch (OperID) {
                                            case 1:
                                                switch (sex) {
                                                    case 1:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 1;
                                                                break;
                                                            case 1:
                                                                switch (cci) {
                                                                    case 0:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 3:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 4:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 5:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 6:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 7:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 8:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 9:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 10:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 11:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 12:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 13:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 14:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                        }
                                                        break;
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                switch (sex) {
                                                    case 1:
                                                        probability_CART = 1;
                                                        break;
                                                    case 0:
                                                        switch (adm) {
                                                            case 0:
                                                                switch (cci) {
                                                                    case 0:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 3:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 4:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 5:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 6:
                                                                        probability_RF = 0.773;
                                                                        break;
                                                                    case 7:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 8:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 9:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 10:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 11:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 12:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 13:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 14:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 1:
                                                                probability_RF = 0.972;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 3:
                                                switch (sex) {
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        switch (cci) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 0;
                                                                break;
                                                            case 5:
                                                                probability_RF = 0;
                                                                break;
                                                            case 6:
                                                                probability_RF = 0;
                                                                break;
                                                            case 7:
                                                                probability_RF = 1;
                                                                break;
                                                            case 8:
                                                                probability_RF = 0;
                                                                break;
                                                            case 9:
                                                                probability_RF = 0;
                                                                break;
                                                            case 10:
                                                                probability_RF = 0;
                                                                break;
                                                            case 11:
                                                                probability_RF = 0;
                                                                break;
                                                            case 12:
                                                                probability_RF = 0;
                                                                break;
                                                            case 13:
                                                                probability_RF = 0;
                                                                break;
                                                            case 14:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                    case 4:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 1;
                                                                break;
                                                            case 1:
                                                                switch (cci) {
                                                                    case 0:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 3:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 4:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 5:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 6:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 7:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 8:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 9:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 10:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 11:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 12:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 13:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 14:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                        }
                                                        break;
                                                    case 5:
                                                        probability_RF = 0.972;
                                                        break;
                                                    case 6:
                                                        probability_RF = 0;
                                                        break;
                                                    case 7:
                                                        switch (emnone) {
                                                            case 0:
                                                                probability_RF = 1;
                                                                break;
                                                            case 1:
                                                                switch (cci) {
                                                                    case 0:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 1:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 2:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 3:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 4:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 5:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 6:
                                                                        switch (sex) {
                                                                            case 1:
                                                                                probability_RF = 1;
                                                                                break;
                                                                            case 0:
                                                                                probability_RF = 0.873;
                                                                                break;
                                                                        }
                                                                        break;
                                                                    case 7:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 8:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 9:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 10:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 11:
                                                                        probability_RF = 1;
                                                                        break;
                                                                    case 12:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 13:
                                                                        probability_RF = 0;
                                                                        break;
                                                                    case 14:
                                                                        probability_RF = 0;
                                                                        break;
                                                                }
                                                                break;
                                                            case 8:
                                                                probability_RF = 0;
                                                                break;
                                                            case 9:
                                                                probability_RF = 0;
                                                                break;
                                                            case 10:
                                                                probability_RF = 0;
                                                                break;
                                                            case 11:
                                                                probability_RF = 0;
                                                                break;
                                                            case 12:
                                                                probability_RF = 0;
                                                                break;
                                                            case 13:
                                                                probability_RF = 0;
                                                                break;
                                                            case 14:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                probability_RF = 1;
                                break;
                            case 2:
                                switch (sex) {
                                    case 0:
                                        probability_RF = 1;
                                        break;
                                    case 1:
                                        probability_RF = 0.75;
                                        break;
                                }
                                break;
                        }
                        break;

                    case 92:
                        switch (OperID) {
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                switch (smoke) {
                                    case 0:
                                        switch (anaesthetic) {
                                            case 0:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        switch (cci) {
                                                            case 0:
                                                                probability_RF = 0;
                                                                break;
                                                            case 1:
                                                                probability_RF = 0;
                                                                break;
                                                            case 2:
                                                                probability_RF = 0;
                                                                break;
                                                            case 3:
                                                                probability_RF = 0;
                                                                break;
                                                            case 4:
                                                                probability_RF = 0;
                                                                break;
                                                            case 5:
                                                                probability_RF = 0;
                                                                break;
                                                            case 6:
                                                                probability_RF = 0;
                                                                break;
                                                            case 7:
                                                                probability_RF = 0;
                                                                break;
                                                            case 8:
                                                                probability_RF = 0;
                                                                break;
                                                            case 9:
                                                                probability_RF = 0;
                                                                break;
                                                            case 10:
                                                                probability_RF = 0;
                                                                break;
                                                            case 11:
                                                                probability_RF = 1;
                                                                break;
                                                            case 12:
                                                                probability_RF = 0;
                                                                break;
                                                            case 13:
                                                                probability_RF = 0;
                                                                break;
                                                            case 14:
                                                                probability_RF = 0;
                                                                break;
                                                        }
                                                        break;
                                                }
                                                break;
                                            case 1:
                                                switch (sex) {
                                                    case 0:
                                                        probability_RF = 1;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                probability_RF = 0;
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 2:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                switch (emnone) {
                                    case 0:
                                        probability_RF = 0;
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                }
                                break;
                            case 8:
                                probability_RF = 0;
                                break;
                            case 9:
                                probability_RF = 0;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 93:
                        switch (OperID) {
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                switch (smoke) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                switch (anaesthetic) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                    case 3:
                                                        probability_RF = 0.75;
                                                        break;
                                                    case 4:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 7:
                                                probability_RF = 0;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 1;
                                                break;
                                            case 11:
                                                probability_RF = 1;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 1;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 3:
                                switch (anaesthetic) {
                                    case 0:
                                        probability_RF = 1;
                                        break;
                                    case 1:
                                        probability_RF = 0.083;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 4:
                                probability_RF = 1;
                                break;
                            case 5:
                                probability_RF = 0;
                                break;
                            case 6:
                                probability_RF = 0;
                                break;
                            case 7:
                                probability_RF = 1;
                                break;
                            case 8:
                                probability_RF = 0.083;
                                break;
                            case 9:
                                probability_RF = 0.083;
                                break;
                            case 10:
                                probability_RF = 0;
                                break;
                            case 11:
                                probability_RF = 0;
                                break;
                            case 12:
                                probability_RF = 0;
                                break;
                            case 13:
                                probability_RF = 0;
                                break;
                            case 14:
                                probability_RF = 0;
                                break;
                        }
                        break;

                    case 94:
                        switch (sex) {
                            case 1:
                                probability_RF = 1;
                                break;
                            case 2:
                                switch (anaesthetic) {
                                    case 0:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                switch (adm) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 0;
                                                        break;
                                                    case 2:
                                                        probability_RF = 1;
                                                        break;
                                                }
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 1;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 1;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 1:
                                        probability_RF = 0.917;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        switch (cci) {
                                            case 0:
                                                probability_RF = 0;
                                                break;
                                            case 1:
                                                probability_RF = 0;
                                                break;
                                            case 2:
                                                probability_RF = 0;
                                                break;
                                            case 3:
                                                probability_RF = 0;
                                                break;
                                            case 4:
                                                probability_RF = 0;
                                                break;
                                            case 5:
                                                probability_RF = 0;
                                                break;
                                            case 6:
                                                switch (smoke) {
                                                    case 0:
                                                        probability_RF = 0;
                                                        break;
                                                    case 1:
                                                        probability_RF = 1;
                                                        break;
                                                    case 2:
                                                        probability_RF = 0;
                                                        break;
                                                }
                                                break;
                                            case 7:
                                                probability_RF = 1;
                                                break;
                                            case 8:
                                                probability_RF = 0;
                                                break;
                                            case 9:
                                                probability_RF = 0;
                                                break;
                                            case 10:
                                                probability_RF = 0;
                                                break;
                                            case 11:
                                                probability_RF = 0;
                                                break;
                                            case 12:
                                                probability_RF = 0;
                                                break;
                                            case 13:
                                                probability_RF = 0;
                                                break;
                                            case 14:
                                                probability_RF = 0;
                                                break;
                                        }
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                        }
                        break;

                    case 95:
                        switch (adm) {
                            case 0:
                                switch (anaesthetic) {
                                    case 0:
                                        probability_RF = 1;
                                        break;
                                    case 1:
                                        probability_RF = 0;
                                        break;
                                    case 2:
                                        probability_RF = 0;
                                        break;
                                    case 3:
                                        probability_RF = 0;
                                        break;
                                    case 4:
                                        probability_RF = 0;
                                        break;
                                }
                                break;
                            case 1:
                                probability_RF = 0;
                                break;
                            case 2:
                                probability_RF = 1;
                                break;
                        }
                        break;

                    case 96:
                        probability_RF = 1;
                        break;

                    case 97:
                        if (OperID == 7) {
                            probability_RF = 1;
                        } else {
                            probability_RF = 0;
                        }
                        break;

                    case 98:
                        probability_RF = 0;
                        break;

                    case 99:
                        switch (anaesthetic) {
                            case 0:
                                probability_RF = 1;
                                break;
                            case 1:
                                probability_RF = 0.75;
                                break;
                            case 2:
                                probability_RF = 0;
                                break;
                            case 3:
                                probability_RF = 0;
                                break;
                            case 4:
                                probability_RF = 0;
                                break;
                        }
                        break;
                }

                View1.setText("CART Model: " + probability_CART);
                View2.setText("Naive Bayes Model: " + probability_NB);
                View3.setText("Random Forest Tree Model: " + probability_RF);

                button_result = (Button) findViewById(R.id.b_result_home);
                button_result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(result.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                button_result = (Button) findViewById(R.id.b_result_back);
                button_result.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(result.this, triage.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }
}